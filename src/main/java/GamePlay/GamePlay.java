package GamePlay;

import GamePlay.Entities.Entity;
import GamePlay.Entities.Ghost;
import GamePlay.Entities.Pacman.AbstractPacman;
import GamePlay.Entities.Pacman.Decorators.KillingPacman;
import GamePlay.Entities.Pacman.Decorators.SpeedPacman;
import GamePlay.Entities.Pacman.SimplePacman;
import GamePlay.Entities.PowerTimeThread;
import GamePlay.Map.PacMap;
import GamePlay.Map.Position;
import Motors.GameMotor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamePlay {

    private static final int FRUIT_SCORE = 100;
    private static final int GHOST_SCORE = 300;
    private static final int SUPERFRUIT_SCORE = 200;
    private int totalScore = 0;

    private AbstractPacman main;
    private PacMap map;

    // pour lancer le jeu dès que la map est prête
    private volatile boolean canMove = false;

    private int currentLevelNumber = -1;
    private Level currentLevel;
    private int lifeLeft;

    private List<Collision> collisions = new ArrayList<Collision>();

    private List<Ghost> ghosts;
    private int nGhost = 0;

    private Object fruteLock = new Object();
    private volatile int frutesNumber = 0;

    GameMotor gameMotor;

    public GamePlay() throws Exception {
        this.map = new PacMap();

        this.map.setGamePlay(this);
        this.map.setPath("map-" + currentLevelNumber + ".txt");

        // les collisions possible
        this.collisions.add(new PacManFruteCollision());
        this.collisions.add(new PacManGhostCollision());
        this.collisions.add(new PacManPowerCollision());

        this.ghosts = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized void stopMoves() {
        canMove = false;
        System.out.println("stop moves");
    }

    public synchronized void allowMoves() {
        canMove = true;
        System.out.println("c'est bon ça bouge");
    }

    public void alterMoves() {
        if (canMove) {
            stopMoves();
        } else {
            allowMoves();
        }
    }

    public void reinitData() {
        frutesNumber = 0;
        ghosts = Collections.synchronizedList(new ArrayList<>());
        nGhost = 0;
        canMove = false;
        totalScore = 0;
        lifeLeft = 0;

        if (main != null) {
            stopPower(PacMap.ENTITIES.KILLING_POWER);
            stopPower(PacMap.ENTITIES.SPEED_POWER);
        }
        if (ghosts.size() > 0) {
            stopPower(PacMap.ENTITIES.SLOW_GHOST_POWER);
        }
    }

    public void startEntities() {
        for (Ghost g : ghosts) {
            Thread t = new Thread(g);
            t.start();
        }
    }

    public void setGameMotor(GameMotor gameMotor) {
        this.gameMotor = gameMotor;
    }

    public void addEntity(PacMap.ENTITIES entity, Position position) {
        try {
            switch (entity) {
                case PACMAN:
                    this.main = new SimplePacman(position);
                    break;
                case GHOST:
                    this.ghosts.add(new Ghost(nGhost, position, currentLevel.getGhostSpeed(), this));
                    nGhost++;
                    break;
                case FRUTE:
                    frutesNumber++;
                    break;
            }
        } catch (Exception e) {
            System.out.println("Entity type problem: " + entity.toString());
            System.out.println("GamePlau.addEntity()");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Position getMainPosition() {
        return this.main.getPosition();
    }

    public boolean isBloc(Position position) {
        boolean isBloc = false;
        if (position.getX() < map.getLabyrinth().length && position.getY() < map.getLabyrinth()[0].length) {
            isBloc = this.map.getLabyrinth()[position.getX()][position.getY()].find(PacMap.ENTITIES.BLOC);
            return isBloc;
        } else {
            throw new ArrayIndexOutOfBoundsException("Engine.isBloc()");
        }
    }

    public boolean pacmanMove(String orientation) {
        Position newP = PacMap.getNewPositionByOrientation(this.main.getPosition(), orientation);
        return doMove(main, newP);
    }

    public boolean doMove(Entity entity, Position end) {
        if (map.isPosition(end)) {
            Position position = entity.getPosition();
            boolean found = map.find(entity, position);
            if (found) {
                if (!isBloc(end)) {
                    // moteur de jeu
                    if (this.canMove) {
                        PacMap.ENTITIES mainAtNewPos = map.getLabyrinth()[end.getX()][end.getY()].getMainElem();
                        gameMotor.makeMove(entity, end);

                        // donne à l'entité sa nouvelle position
                        entity.setPosition(end);

                        // on récupere l'entité principale à la position end
                        // verifie s'il y a collision entre les deux
                        Entity entityAtEndPos = this.getEntityByPos(end, mainAtNewPos);
                        handleCollision(entity, entityAtEndPos);
                    } else {
                        System.out.println("\u001B[33m Mouvement bloqué de " + entity.getType() + "\u001B[0m");
                    }
                }
                return true;
            } else {
                System.out.println("not found");
                return false;
            }
        } else {
            return false;
        }
    }

    private Entity getEntityByPos(Position end, PacMap.ENTITIES mainAtNewPos) {
        // dans le cas ou c'est un pourvoir ou un simple fruit
        Entity entity = new Entity(mainAtNewPos, end);

        switch (mainAtNewPos) {
            case PACMAN:
                return main;
            case GHOST:
                return this.findGhostByPos(end);
            default:
                return entity;
        }
    }

    private Entity findGhostByPos(Position end) {
        for (Ghost g : ghosts) {
            if (g.getPosition().getX() == end.getX() && g.getPosition().getY() == end.getY())
                return g;
        }
        System.err.println("GamePlay.findGHostByPos: inexistant");
        return null;
    }

    public void handleCollision(Entity p1, Entity p2) {
        for (Collision collision : collisions) {
            if (collision.match(p1, p2)) { // chaque type de collision verifie s'il est impliqué
                // si oui, il appelle gamePlay pour l'action à faire
                collision.takeDecision(this, p1, p2);
                break;
            }
        }
    }

    // conclusion d'une colliion avec fruit
    public void deleteFrute(Entity e) {
        if (this.map.find(PacMap.ENTITIES.FRUTE, e.getPosition())) {
            synchronized (fruteLock) {
                frutesNumber--;
                totalScore = totalScore + getPoints(e);
                gameMotor.setScore(totalScore);
                map.removeEntity(e);
                gameMotor.frute();
                System.out.println("Frutes: " + frutesNumber);
                if (frutesNumber == 0) {
                    this.gameWon();
                }
            }
        }
    }

    public void ghostCollision(Ghost ghost) {
        System.out.println("Gameplay.ghostCollision()");
        Entity killed = main.chooseWhoToKill(ghost);
        if (killed.getType() == PacMap.ENTITIES.GHOST) {
            killGhost(ghost);
        } else if (killed.getType() == PacMap.ENTITIES.PACMAN) {
            System.out.println("KILL PACMAN");
            hurtPacman();
        }
    }

    public void powerCollision(Entity e) {
        System.out.println("\u001B[33m" + "Power: " + e.getType() + " \u001B[35m ENABLED for " + currentLevel.getTimeForPower() + "ms \u001B[0m");
        switch (e.getType()) {
            case KILLING_POWER:
                main = new KillingPacman(main);
                break;
            case SPEED_POWER:
                main = new SpeedPacman(main, currentLevel.getPacmanPowerSpeed());
                break;
            case SLOW_GHOST_POWER:
                freezeGhosts(currentLevel.getGhostReductionPower());
                break;
            default:
                break;
        }
        gameMotor.power();
        System.out.println(main.toString());
        map.removeEntity(e);
        (new PowerTimeThread(this, e.getType(), currentLevel.getTimeForPower())).start();
    }

    public void stopPower(PacMap.ENTITIES power) {
        System.out.println("\u001B[33m" + "Power: " + power + " \u001B[35m DISABLED \u001B[0m");
        switch (power) {
            case KILLING_POWER:
                main = main.getPacman();
                break;
            case SPEED_POWER:
                main = main.getPacman();
                break;
            case SLOW_GHOST_POWER:
                freezeBackGhosts();
                break;
        }
        gameMotor.powerEnd();
        System.out.println("power stopped: " + main.toString());
    }

    public void freezeGhosts(int minus) {
        for (Ghost g : ghosts) {
            g.decreaseSpeed(minus);
        }
    }

    public void freezeBackGhosts() {
        for (Ghost g : ghosts) {
            g.resetSpeed();
        }
    }

    // conclusion d'une collision avec ghost
    public void killPacman() {
        map.removeEntity(main);
        gameMotor.removePacman(main, map.getMainElem(main.getPosition().getX(), main.getPosition().getY()));
        this.gameLost();
        gameMotor.gameOver();
    }

    public void hurtPacman() {
        lifeLeft--;
        if (lifeLeft > 0)
            gameMotor.loseLife();
        else
            killPacman();
        gameMotor.setLife(lifeLeft);
    }

    public void killGhost(Ghost g) {
        // TODO: ghosts.getByPosition(p).stop
        // TODO: game.deleteGhost(p)
        g.dead();
        gameMotor.removeGhost(g);
        totalScore = totalScore + getPoints(g);
        gameMotor.setScore(totalScore);
    }

    public void go() {
        if (gameMotor.launchParty(this.map)) {
            try {
                stopMoves();
                System.out.println("Nombre de vies: " + lifeLeft);
                startEntities();
                gameMotor.setLife(lifeLeft);
                gameMotor.setScore(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void gameWon() {
        this.stopMoves();
        gameMotor.victory();
        this.nextLevel();
    }

    public void gameLost() {
        this.stopMoves();
        gameMotor.gameOver();
        this.restart();
    }

    public void restart() {
        currentLevelNumber -= 1;
        this.nextLevel();
    }

    public void nextLevel() {
        reinitData();

        currentLevelNumber += 1;
        System.out.println("--------- PASSAGE AU NIVEAU " + currentLevelNumber + "---------------");
        currentLevel = new Level(currentLevelNumber);
        if (currentLevel.get()) {
            try {
                String mapName = "map-" + currentLevelNumber + ".txt";
                // reinitialiser les données score ...
                this.map.destroy();
                this.map.setPath(mapName);
                this.map.load();
                this.lifeLeft = currentLevel.getPacmanLives();
                stopMoves();
                this.go();
            } catch (Exception e) {
                System.out.println("GamePlay.nextLvl()");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Le jeu est fini... Vous êtes si fort :-)");
        }
    }

    public int getPoints(Entity e) {
        switch (e.getType()) {
            case FRUTE:
                return FRUIT_SCORE;
            case GHOST:
                return GHOST_SCORE;
            case KILLING_POWER:
            case SLOW_GHOST_POWER:
            case SPEED_POWER:
                return SUPERFRUIT_SCORE;
            default:
                return 0;
        }
    }

    public PacMap getMap() {
        return map;
    }
}