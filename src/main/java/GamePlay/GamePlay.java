package GamePlay;

import GamePlay.Entities.Entity;
import GamePlay.Entities.Ghost;
import GamePlay.Entities.Pacman.AbstractPacman;
import GamePlay.Entities.Pacman.Decorators.KillingPacman;
import GamePlay.Entities.Pacman.Decorators.SpeedLessGhostPacman;
import GamePlay.Entities.Pacman.Decorators.SpeedPacman;
import GamePlay.Entities.Pacman.SimplePacman;
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
	private static final int GHOST_SCORE  = 300;
	private static final int SUPERFRUIT_SCORE = 200;
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
        return doMove(main, newP);
    }

    public boolean doMove(Entity entity, Position end) {
        if (map.isPosition(end)) {
            Position position = entity.getPosition();
            boolean found = map.find(entity, position);
            if (found) {
                if (!isBloc(end)) {
                    // moteur de jeu
                    PacMap.ENTITIES mainAtNewPos = map.getLabyrinth()[end.getX()][end.getY()].getMainElem();
                    System.out.println("maintAtNewPos: " + mainAtNewPos);
                    gameMotor.makeMove(entity, end);

                    // donne à l'entité sa nouvelle position
                    entity.setPosition(end);

                    // on récupere l'entité principale à la position end
                    // verifie s'il y a collision entre les deux
                    handleCollision(entity, new Entity(mainAtNewPos, end) {
                        @Override
                        public PacMap.ENTITIES getType() {
                            return mainAtNewPos;
                        }
                    });

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

    public void handleCollision(Entity p1, Entity p2) {
        for (Collision collision : collisions) {
            if (collision.match(p1, p2)) { // chaque type de collision verifie s'il est impliqué
                System.out.println("----------------------------b collision - ---------------------");
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

    public void ghostCollision(Entity ghost) {
        System.out.println(main.toString());
        Entity killed = main.chooseWhoToKill(ghost);
        if (killed.getType() == PacMap.ENTITIES.GHOST) {
            	killGhost((Ghost) ghost);
            // TODO: gameMotor.removeGhost(ghost)
        } else if (killed.getType() == PacMap.ENTITIES.PACMAN) 
        {
            hurtPacman();
        }
    }

    public void powerCollision(Entity e) {
        System.out.println("e.getType()" + e.getType());
        switch (e.getType()) {
            case KILLING_POWER:
                main = new KillingPacman(main, this);
                break;
            case SPEED_POWER:
                main = new SpeedPacman(main, this, 7);
                break;
            case SLOW_GHOST_POWER:
                freezeGhosts();
                break;
            default:
            	break;
        }
        // TODO: si tu peux mettre un son de pouvoir
         //TODO: new PowerTimeThread(this, POWER_TIME_SEC, e.getType()).start();
        // TODO: qui, dans un thread lance un miniteur et a la fin appelle gamePlay.stopPower()
    }

    public void stopPower(PacMap.ENTITIES power) {
        // TODO: appelé du thread, elle enleve les pouvoirs
        switch (power) {
            case KILLING_POWER:
                main = main.getPacman();
                System.out.println("New pouvoir KILLER FINI");
                break;
            case SPEED_POWER:
                main = main.getPacman();
                System.out.println("New pouvoir VITESSE FINI");
                break;
            case SLOW_GHOST_POWER:
                freezeBackGhosts();
                System.out.println("New pouvoir FREEZE GHOSTS FINI");
                break;
        }
    }

    public synchronized void freezeGhosts() {
        Ghost buffer;
        for (Ghost g : ghosts) {
            buffer = g;
            ghosts.remove(g);
            buffer = new SpeedLessGhostPacman(buffer, this, 7);
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
    
    public void hurtPacman()
    {
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
                if (currentLevel == null)
                {
                    currentLevel = new Level(3, 1);
                    lifeLeft = currentLevel.getLevelLife();
                }
                else
                {
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
                this.start();
                System.out.println(map.getMainElem(2, 17));
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
    
    public int getPoints(Entity e)
    {
    	switch (e.getType())
    	{
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

    public Level getLevel()
    {
    	return currentLevel;
    }

    public PacMap getMap() {
        return map;
    }
}
