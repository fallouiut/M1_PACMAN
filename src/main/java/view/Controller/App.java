package view.Controller;

import GamePlay.GamePlay;
import Motors.GameMotor;
import Motors.PhysicalMotor;
import javafx.scene.layout.BorderPane;
import view.Interface.PacmanAnimation;
import view.Interface.Sounds;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import view.Interface.Sprites;
import view.Interface.StateBar;

public class App extends Application {

    /* COMPOSANT GRAPHIQUES */
    private PacmanAnimation m_pacmanAnim;
    private StateBar m_stateBar;
    private Scene root;
    private MapController map;

    /* OBJETS DU JEU */
    private GamePlay gamePlay;
    private GameMotor gameMotor;
    private PhysicalMotor physicalMotor;
    private Sounds sounds;

    public void start(Stage stage) throws Exception {
        // qui fait les translations et detections de collision
        // c'est ce qui controle l'interface
        initInterface();

        physicalMotor = new PhysicalMotor(map);
        sounds = new Sounds();

        // gère les combinaisons interface + son + et autres
        gameMotor = new GameMotor(physicalMotor, sounds);

        // gere les mouvements tout en appliquant les regles du jeu
        gamePlay = new GamePlay();
        gamePlay.setGameMotor(gameMotor);

        // CHAQUE MOUVEMENT SUIT CET ORDRE
        // Gampley (qui vérifie le mouvement suit les regles)
        // qui appelle Moteur de jeu (qui effectue les actions graphiques et sonores)
        // Moteur physique (qui controle l'interface et detecte les collisions)

        // ecouteurs mouvement
        root.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
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
                    //refresh();
                    ke.consume();
                gamePlay.pacmanMove(key);
            }
        });

        try {
            // lance le jeu
            gamePlay.nextLevel();
            m_pacmanAnim.start();
        } catch (Exception e) {
            System.out.println("ApplicationController.start()");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        stage.setScene(root);
        stage.setTitle("Pacman");
        stage.show();
    }

    public void initInterface() {
        new Sprites();
        map = new MapController();
        createStateBar(GamePlay.LIFE_NUMBER);
        BorderPane pane = new BorderPane();
        pane.setCenter(map.getMap());
        pane.setBottom(m_stateBar.getBar());
        root = new Scene(pane);
        m_pacmanAnim = new PacmanAnimation(map);
    }

    private void createStateBar(int numberLife) {
        m_stateBar = new StateBar(numberLife);
    }

    public static void main(String[] args) {
        launch(args);
    }


}