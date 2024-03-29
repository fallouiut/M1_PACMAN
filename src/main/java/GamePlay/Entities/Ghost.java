package GamePlay.Entities;

import GamePlay.GamePlay;
import GamePlay.Map.PacMap;
import GamePlay.Map.Position;
import javafx.application.Platform;

/*
    Class ghost, executee en thread
    IA: Distance Shanon
 */
public class Ghost extends Entity implements Runnable {

    private Position lastPosition = null;
    private Position nextPosition;

    private int time_to_wait_MILLIS;
    private int speed_MINUS;

    private boolean killed = false;

    // [moveTop, moveBottom, left, right] pour pouvoir itérer dessus
    private boolean[] moves = new boolean[4];
    private int[] distances = new int[4];
    private Position[] positions = new Position[4];
    private String[] directions = {"Haut", "Bas", "Gauche", "Droite"}; // a des fins de tests
    private GamePlay gamePlay;
    
    private int nGhost;

    public Ghost(int nGhost, Position position, int time_to_wait_MILLIS, GamePlay gamePlay) {
        super(PacMap.ENTITIES.GHOST, position);
        this.nGhost = nGhost;
        this.position = position;
        this.gamePlay = gamePlay;
        this.time_to_wait_MILLIS = time_to_wait_MILLIS;
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

    /*
        on boucle et on choisit un mouvement
     */
    public void startMoving() {
        while (true) {
            choose();
            move();
            try {
                Thread.sleep(time_to_wait_MILLIS);
                if(killed) break;
            } catch (Exception e) {
                System.out.println("Ghost.startMoving()");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("stop moving");
    }

    public void move() {
        try {
            // gamePlay.doMove() comme d'hab
            // le truc est déjà fait faudra juste checker la compatibilité avec moveEntity s'il y a des erreurs
            Position maybeLast = new Position(this.position.getX(), this.position.getY());

            Ghost thisG = this;
            // eviter les IllegalStateException
            if(gamePlay != null) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (gamePlay.doMove(thisG, nextPosition)) {
                            lastPosition = maybeLast;
                        } else {
                            System.out.println("Holding");
                        }
                    }
                });
            } else {
                System.out.println("gamePlay == null, ghost out of game");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " starting");
            startMoving();
        } catch (Exception e) {
            System.out.println("Ghost.run() startMoving()");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Position p) {
        this.position = p;
    }

    @Override
    public int getSpeed() {
        return 10;
    }

    public void dead() {
        this.killed = true;
        System.out.println("killed");
    }

    public void resetSpeed() {
        this.time_to_wait_MILLIS -= this.speed_MINUS;
    }

    public void decreaseSpeed(int minus) {
        this.speed_MINUS = minus;
        this.time_to_wait_MILLIS += this.speed_MINUS;
    }


    public int getNumGhost() {
        return nGhost;
    }
}