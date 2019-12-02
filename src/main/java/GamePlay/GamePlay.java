package GamePlay;

import GamePlay.Entities.Entity;
import GamePlay.Entities.Ghost;
import GamePlay.Entities.Pacman.AbstractPacman;
import GamePlay.Entities.Pacman.SimplePacman;
import GamePlay.Map.PacMap;
import GamePlay.Map.Position;
import Motors.GameMotor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GamePlay {

    private final String MAP_PATH = "files/maps/map-test.txt";
    public final static int TIME_TO_WAIT = 1000;

    private AbstractPacman main;
    private PacMap map;

    private int currentLevel = 1;

    private List<Collision> collisions = new ArrayList<Collision>();

    private List<Ghost> ghosts;
    private int nGhost = 0;

    private Object fruteLock = new Object();
    private volatile int frutesNumber = 0;
    public static final int LIFE_NUMBER = 5;

    GameMotor gameMotor;

    public GamePlay() throws Exception {
        this.ghosts = new ArrayList<>();
        this.map = new PacMap();

        this.map.setGamePlay(this);
        this.map.setPath(MAP_PATH);

        // les collisions possible
        this.collisions.add(new PacManFruteCollision());
        this.collisions.add(new PacManGhostCollision());
        this.collisions.add(new PacManPowerCollision());
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
                    this.main = new SimplePacman(position, this);
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
        return doMove(main, main.getPosition(), newP);
    }

    public boolean doMove(Entity entity, Position start, Position end) {
        System.out.println("gamePlay.movePacman()");
        if (map.isPosition(end)) {
            Position position = entity.getPosition();
            boolean found = map.find(entity, position);
            System.out.println("doMove()");
            if (found) {
                // moteur de jeu
                PacMap.ENTITIES mainAtNewPos = map.getLabyrinth()[position.getX()][position.getY()].getMainElem();


                gameMotor.makeMove(entity, end);

                // on récupere l'entité principale à la position end
                // verifie s'il y a collision entre les deux
                handleCollision(entity, new Entity(mainAtNewPos, end) {
                    @Override
                    public PacMap.ENTITIES getType() {
                        return mainAtNewPos;
                    }
                });

                // met a jour la map
                map.removeEntity(entity);
                map.moveEntity(entity, end);

                // met a jour avec sa nouvelle position
                entity.setPosition(end);
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println("POSITION DEMANDEE EST UN BLOC");
            return false;
        }
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
                // TODO: map.removeEntity(ENTITIES.FRUTE, p)
                // TODO: gameMotor.deleteFrute(p);

                if (frutesNumber == 0) {
                    // TODO: gameMotor.gameWon()
                    // TODO: this.nextLvl()
                }
            }
        }
    }

    // conclusion d'une collision avec ghost
    public void killPacman() {
        map.removeEntity(main);
        // TODO: verifier pouvoirs avant de tuer
        // TODO: main.stop()
        // TODO: gameMotor.deletePacman(p)
        // TODO: gameMotor.gameLost()
        // TODO: gameMotor.restart(this.lvl)

    }

    public void killGhost(Position p) {
        // TODO: ghosts.getByPosition(p).stop
        // TODO: game.deleteGhost(p)
    }

    public void ghostCollision(Entity e) {
    }

    public void start() {
        System.out.println("start");
        gameMotor.launchParty(this.map);
        startEntities();
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
                this.ghosts = new ArrayList<>();
                //this.currentLevel += 1;

                this.map.destroy();
                this.map.setPath(path);
                this.map.load();
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

    public PacMap getMap() {
        return map;
    }
}
