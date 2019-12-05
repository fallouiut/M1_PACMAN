package GamePlay;

import GamePlay.Entities.Entity;
import GamePlay.Entities.Ghost;
import GamePlay.Entities.Pacman.AbstractPacman;
import GamePlay.Entities.Pacman.Decorators.KillingPacman;
import GamePlay.Entities.Pacman.Decorators.SlowGhost;
import GamePlay.Entities.Pacman.Decorators.SpeedPacman;
import GamePlay.Entities.Pacman.SimplePacman;
import GamePlay.Entities.PowerTimeThread;
import GamePlay.Map.PacMap;
import GamePlay.Map.Position;
import Motors.GameMotor;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamePlay {

    private static final int FRUIT_SCORE = 100;
    private static final int GHOST_SCORE = 300;
    private static final int SUPERFRUIT_SCORE = 200;
    private static final int POWER_TIME_SEC = 10;
    private int totalScore = 0;

    private final String MAP_PATH = "files/maps/map-test.txt";
    public final static int TIME_TO_WAIT = 1000;

    private AbstractPacman main;
    private PacMap map;

    private Level currentLevel;
    private int lifeLeft;

    private List<Collision> collisions = new ArrayList<Collision>();

    private List<Ghost> ghosts;
    private int nGhost = 0;

    private Object moveLock = new Object();
    private Object fruteLock = new Object();
    private volatile int frutesNumber = 0;
    public static final int LIFE_NUMBER = 5;

    GameMotor gameMotor;

    public GamePlay() throws Exception {
        this.map = new PacMap();

        this.map.setGamePlay(this);
        this.map.setPath(MAP_PATH);

        // les collisions possible
        this.collisions.add(new PacManFruteCollision());
        this.collisions.add(new PacManGhostCollision());
        this.collisions.add(new PacManPowerCollision());

        this.ghosts = Collections.synchronizedList(new ArrayList<>());
    }

    public void startEntities() {
        // TODO: lancer l'ia des fantomes
        // TODO: lancer les mouvements pacman
        for (Ghost g : ghosts) {
            Thread t = new Thread(g);
            t.start();
        }
    }

    public void setGameMotor(GameMotor gameMotor) {
        this.gameMotor = gameMotor;
    }

    public List<Ghost> getGhosts() {
        return ghosts;
    }

    public int getFrutesNumber() {
        return frutesNumber;
    }

    public void addEntity(PacMap.ENTITIES entity, Position position) {
        try {
            switch (entity) {
                case PACMAN:
                    this.main = new SimplePacman(position);
                    break;
                case GHOST:
                    this.ghosts.add(new Ghost(nGhost, position, this));
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
        System.out.println("----------------- doMove() -----------------------");
        if (map.isPosition(end)) {
            Position position = entity.getPosition();
            boolean found = map.find(entity, position);
            if (found) {
                if (!isBloc(end)) {
                    // moteur de jeu
                    PacMap.ENTITIES mainAtNewPos = map.getLabyrinth()[end.getX()][end.getY()].getMainElem();
                    gameMotor.makeMove(entity, end);

                    // donne à l'entité sa nouvelle position
                    entity.setPosition(end);
                    System.out.println(entity.getType() + " new pos: " + entity.getPosition().toString());

                    // on récupere l'entité principale à la position end
                    // verifie s'il y a collision entre les deux
                    Entity entityAtEndPos = this.getEntityByPos(end, mainAtNewPos);
                    System.out.println("Main entity at pos: " + end.toString());
                    System.out.println(mainAtNewPos);
                    handleCollision(entity, entityAtEndPos);
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
        System.out.println("getEntityByPos");

        switch (mainAtNewPos) {
            case PACMAN:
                return main;
            case GHOST:
                System.out.println("getEntityByPos(): found ghost");
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
        System.out.println("handleCollision()");
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

                if (frutesNumber == 0) {
                    // TODO: gameMotor.gameWon()
                    // TODO: this.nextLvl()
                    System.out.println("GAGNARE");
                    JOptionPane.showConfirmDialog(null, "Vous avez gagné");
                }
            }
        }
    }

    public void ghostCollision(Ghost ghost) {
        System.out.println("Gameplay.ghostCollision()");
        Entity killed = main.chooseWhoToKill(ghost);
        if (killed.getType() == PacMap.ENTITIES.GHOST) {
            killGhost(ghost);
            // TODO: gameMotor.removeGhost(ghost)
        } else if (killed.getType() == PacMap.ENTITIES.PACMAN) {
            System.out.println("KILL PACMAN");
            hurtPacman();
        }
    }

    public void powerCollision(Entity e) {
        switch (e.getType()) {
            case KILLING_POWER:
                main = new KillingPacman(main);
                break;
            case SPEED_POWER:
                main = new SpeedPacman(main, 7);
                break;
            case SLOW_GHOST_POWER:
                freezeGhosts();
                break;
            default:
                break;
        }
        System.out.println(main.toString());
        map.removeEntity(e);
        // TODO: si tu peux mettre un son de pouvoir
        //new PowerTimeThread(this, e.getType(), POWER_TIME_SEC).start();
        // TODO: qui, dans un thread lance un miniteur et a la fin appelle gamePlay.stopPower()
    }

    public void stopPower(PacMap.ENTITIES power) {
        System.out.println("Pouvoir " + power + " terminé");
        // TODO: appelé du thread, elle enleve les pouvoirs
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
        System.out.println(main.toString());
    }

    public synchronized void freezeGhosts() {
        Ghost buffer;
        for (Ghost g : ghosts) {
            buffer = g;
            ghosts.remove(g);
            buffer = new SlowGhost(buffer, this, 7);
            ghosts.add(buffer);
        }

/*
        for (Ghost g : ghosts)
            g.toString();
*/
    }

    public void freezeBackGhosts() {
        Ghost buffer;
        for (Ghost g : ghosts) {
            buffer = g.getGhost();
            ghosts.remove(g);
            ghosts.add(buffer);
        }
    }

    // conclusion d'une collision avec ghost
    public void killPacman() {
        map.removeEntity(main);
        gameMotor.removePacman(main, map.getMainElem(main.getPosition().getX(), main.getPosition().getY()));
        JOptionPane.showConfirmDialog(null, "Perdu");

        // fais ces deux la stp dans un premier temps
        // TODO: gameMotor.deletePacman(p)
        // TODO: gameMotor.gameLost()

        // TODO: main.stop()
        // TODO: gameMotor.restart(this.lvl)

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
        System.out.println("Gameplay.killGhost at(): " + g.getPosition().toString());
        System.out.println("GHOST position " + g.getPosition().toString() + " to remove");
        gameMotor.removeGhost(g);
        totalScore = totalScore + getPoints(g);
        gameMotor.setScore(totalScore);
    }

    public void start() {
        System.out.println("start");
        gameMotor.launchParty(this.map);
        //startEntities();
    }

    public void nextLevel() throws Exception {
        System.out.println("nextLvl()");
        try {
            //String path = "files/maps/map-" + currentLevel + ".txt";
            String path = "files/maps/map-test.txt";
            File file = new File(path);
            if (file.exists()) {
                this.frutesNumber = 0;
                this.main = null;
                this.ghosts.removeAll(this.ghosts);
                this.totalScore = 0;
                if (currentLevel == null) {
                    currentLevel = new Level(3, 1);
                    lifeLeft = currentLevel.getLevelLife();
                } else {
                    int currentLevelNum = currentLevel.getlevelNumber();
                    int nextLifeAmount = currentLevel.getLevelLife();
                    if (nextLifeAmount > 1)
                        nextLifeAmount--;
                    currentLevel = new Level(currentLevelNum + 1, nextLifeAmount);
                    lifeLeft = currentLevel.getLevelLife();
                }
                gameMotor.setLife(lifeLeft);
                this.map.destroy();
                this.map.setPath(path);
                this.map.load();
                this.main = new KillingPacman(main);
                this.start();
            } else {
                // TODO: afficher que le jeu est fini ou erreur
                System.out.println("game is done");
                gameMotor.gameIsReached();
            }
        } catch (Exception e) {
            System.out.println("GamePlay.nextLvl()");
            System.out.println(e.getMessage());
            e.printStackTrace();
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

    public Level getLevel() {
        return currentLevel;
    }

    public PacMap getMap() {
        return map;
    }
}
