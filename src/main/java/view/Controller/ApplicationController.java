package view.Controller;

import GamePlay.GamePlay;
import GamePlay.Map.PacMap;
import GamePlay.Map.Position;
import Motors.GameMotor;
import Motors.PhysicalMotor;
import view.Interface.Sounds;
import view.Interface.Sprites;
import view.Interface.StateBar;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ApplicationController extends Application {

    private PacmanAnimation pacmanAnimation;
    private MapController m_mapController;
    private StateBar m_stateBar;
    private Scene m_root;

    public final static int SPEED = 10;

    private boolean m_initiated = false;
    private long m_lastUpdate;

    private final int m_numberLife = 3; // To remove

    public ApplicationController() {
        initializeInterface();
    }

    private class PacmanAnimation extends AnimationTimer {
        @Override
        public void handle(long arg0) {
            long elapsedNanoSeconds = System.nanoTime() - m_lastUpdate;
            double elapsedSeconds = elapsedNanoSeconds / 1_000_000_000.0;
            if (elapsedSeconds < 1)
                return;
            m_lastUpdate = System.nanoTime();
            ImageView currentPacman = m_mapController.getPacman();
            currentPacman.setImage(Sprites.animatePacman(currentPacman.getImage()));
        }

    }

    public void start(Stage stage) throws Exception {
        // qui fait les translations et detections de collision
        // c'est ce qui controle l'interface
        PhysicalMotor physicalMotor = new PhysicalMotor(m_mapController);
        // gère les combinaisons interface + son + et autres
        GameMotor gameMotor = new GameMotor(physicalMotor, new Sounds());
        // gere les mouvements tout en appliquant les regles du jeu
        GamePlay gamePlay = new GamePlay();

        // CHAQUE MOUVEMENT SUIT CET ORDRE
		// Gampley (qui vérifie le mouvement suit les regles)
		// qui appelle Moteur de jeu (qui effectue les actions graphiques et sonores)
		// Moteur physique (qui controle l'interface et detecte les collisions)

        gamePlay.setGameMotor(gameMotor);
        gameMotor.setGamePlay(gamePlay);
        physicalMotor.setGameMotor(gameMotor);

        // ecouteurs mouvement
        m_root.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                String key = "";
                if (ke.getCode() == KeyCode.LEFT)
                    key = "LEFT";
                else if (ke.getCode() == KeyCode.RIGHT)
                    key = "RIGHT";
                else if (ke.getCode() == KeyCode.DOWN)
                    key = "DOWN";
                else if (ke.getCode() == KeyCode.UP)
                    key = "UP";
                else if (ke.getCode() == KeyCode.ESCAPE)
                    refresh();
                ke.consume();
                gamePlay.pacmanMove(key);
            }
        });

        try {
            // lance le jeu
            gamePlay.nextLevel();
        } catch (Exception e) {
            System.out.println("ApplicationController.start()");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        stage.setScene(m_root);
        stage.setTitle("Pacman");
        stage.show();
    }

    private void createStateBar() {
        m_stateBar = new StateBar(m_numberLife);
    }

    private void initializeInterface() {
        new Sprites();
        // TODO: deplacer dans moteur de jeu
        //  new Sounds();
        m_mapController = new MapController();
        createStateBar();
        BorderPane pane = new BorderPane();
        pane.setCenter(m_mapController.getMap());
        pane.setBottom(m_stateBar.getBar());
        m_root = new Scene(pane);

        pacmanAnimation = new PacmanAnimation();
        pacmanAnimation.start();
    }

    public void setScore(int score) {
        m_stateBar.setScore(score);
    }

    public void setLife(int life) {
        m_stateBar.setLife(life);
    }

    public void refresh() {
        // TODO: sounds dans Motor de jeu, enlever le couplage avec la Map
        //  Sounds.restartTheme();
        pacmanAnimation.stop();
        m_mapController.refresh();
        m_stateBar.refresh(m_numberLife);
        pacmanAnimation.start();
    }

    public Scene getM_root() {
        return m_root;
    }

    public static void main(String[] args) {
        launch(args);
    }


}
