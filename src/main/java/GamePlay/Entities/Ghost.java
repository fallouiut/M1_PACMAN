package GamePlay.Entities;

import GamePlay.GamePlay;
import GamePlay.Map.PacMap;
import GamePlay.Map.Position;

import java.util.concurrent.TimeUnit;

public class Ghost extends Entity implements Runnable {

    private Position lastPosition = null;
    private Position nextPosition;

    private boolean killed = false;

    // [moveTop, moveBottom, left, right] pour pouvoir itérer dessus
    private boolean[] moves = new boolean[4];
    private int[] distances = new int[4];
    private Position[] positions = new Position[4];
    private String[] directions = {"Haut", "Bas", "Gauche", "Droite"};
    private GamePlay gamePlay;
    
    private int nGhost;

    public Ghost(int numGhost, Position position, GamePlay gamePlay) {
        super(PacMap.ENTITIES.GHOST, position);
        this.nGhost = numGhost;
        this.position = position;
        this.gamePlay = gamePlay;
        System.out.println("Thread: " + Thread.currentThread().getName());
    }

    public void finish() {
    }

    public void initMovesData() {
        // on mets tous les moves a false
        // on mets toutes les distances a 0
        // on initialise les positions en fonction de la position du fantome (this)
        this.moves = new boolean[4];
        this.distances = new int[4];
        this.positions = new Position[4];
        for (int i = 0; i < moves.length; ++i) {
            this.moves[i] = false;
            this.distances[i] = 0;
        }
        this.positions[0] = new Position(this.position.getX() - 1, this.position.getY());
        this.positions[1] = new Position(this.position.getX() + 1, this.position.getY());
        this.positions[2] = new Position(this.position.getX(), this.position.getY() - 1);
        this.positions[3] = new Position(this.position.getX(), this.position.getY() + 1);
    }

    public void choose() {
        this.initMovesData();
        int possibleMoves = 0;

        for (int i = 0; i < this.moves.length; i++) {
            this.checkMove(i);
            if (this.moves[i]) {
                possibleMoves += 1;
            }
        }

        if (possibleMoves == 0) {
            nextPosition = lastPosition;
            System.out.println("move back");
        } else {
            findNextPosition();
        }
    }

    public void findNextPosition() {
        int distance = -1;
        int indice = -1;

        for (int i = 0; i < moves.length; ++i) {
            //System.out.println(directions[i] + ": " + moves[i] + " " + distances[i]);
            // si rien n'est défini
            // le premier trouvé est indicé
            if(indice == -1 && distance == -1 && this.moves[i]) {
                distance = distances[i];
                indice = i;
            } else if (moves[i] && (distances[i] < distances[indice])){
                indice = i;
                //System.out.println(directions[indice] + " -nouveau choix");
            }
            //System.out.println("CHOIX: " + directions[indice]);
        }
        nextPosition = positions[indice];
        //System.out.println("Nouveau move: " + directions[indice] + ": " + moves[indice] + " " + distances[indice]);
    }

    /*
        calcule la distance vers pacman en fonction du carré des coordonnées de pacman - ceux du fantome
        en principe, le + faible est le plus proche
        d(A, B) = |XB - XA| + |YB - YA|
     */
    public int getDistance(Position p) {// distance de manhattan
        int xDiff = Math.abs(p.getX() - this.gamePlay.getMainPosition().getX());
        int yDiff = Math.abs(p.getY() - this.gamePlay.getMainPosition().getY());
        return (yDiff + xDiff);
    }

    /*
        Vérifie que la position indiquée n'est pas un block ou la dernière position
     */
    public boolean isValidPosition(Position position) {
        boolean isLastPosition;
        if (lastPosition != null) {
            isLastPosition = (position.getX() == this.lastPosition.getX()) && (this.lastPosition.getY() == position.getY());
        }
        else{
            isLastPosition = false;
        }
        return !this.gamePlay.isBloc(position) && !isLastPosition;
    }

    public void checkMove(int move) {
        this.moves[move] = this.isValidPosition(this.positions[move]); //&& true mais pas la peine
        if (this.moves[move]) {
            this.distances[move] = getDistance(this.positions[move]);
        }
    }

    public Position getNextPosition() {
        return nextPosition;
    }

    public Position getLastPosition() {
        return lastPosition;
    }

    public void stop() {
        // attendre un temps limité selon le niveau
        try {
            TimeUnit.MILLISECONDS.sleep(GamePlay.TIME_TO_WAIT);
            choose();
        } catch (Exception e) {
            System.out.println("Ghost.stop(): maybe TimeUnit.sleep()");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /*
        on boucle et on choisit un mouvement
     */
    public void startMoving() {
        while (!killed) {
            choose();
        }
    }

    public void move() {
        try {
            // gamePlay.doMove() comme d'hab
            // le truc est déjà fait faudra juste checker la compatibilité avec moveEntity s'il y a des erreurs
            if (this.gamePlay.doMove(this, lastPosition, nextPosition)) {
                Position lastOne = this.position;
                this.position = nextPosition;
                this.lastPosition = lastOne;
            } else {
                System.out.println("Holding");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public int getNumGhost()
    {
    	return nGhost;
    }

    @Override
    public void run() {
        try {
            startMoving();
        } catch (Exception e) {
            System.out.println("Ghost.run() startMoving()");
        }
    }
}
