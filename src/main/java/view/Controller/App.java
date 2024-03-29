package view.Controller;

import GamePlay.GamePlay;
import Motors.GameMotor;
import Motors.GraphicalMotor;
import javafx.scene.layout.BorderPane;
import javafx.stage.WindowEvent;
import view.Interface.Highscore;
import view.Interface.Menu;
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
    private BorderPane pane;

    /* OBJETS DU JEU */
    private GamePlay gamePlay;
    private GameMotor gameMotor;
    private GraphicalMotor graphicalMotor;
    private Sounds sounds;
    
    private Stage stage;


    public void start(Stage stage) throws Exception {
        // chercher les imges
        new Sprites();

    	this.stage = stage;
        this.pane = initInterface();
        graphicalMotor = new GraphicalMotor(map, m_stateBar, m_pacmanAnim, pane, stage);
        sounds = new Sounds();
        gameMotor = new GameMotor(graphicalMotor, sounds);
        gameMotor = new GameMotor(graphicalMotor, sounds);
        gamePlay = new GamePlay();
        gamePlay.setGameMotor(gameMotor);
        stage.setTitle("Pacman");

        displayMenu();
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }

    public BorderPane initInterface() {
        new Sprites();
        map = new MapController();
        createStateBar(0);
        BorderPane pane = new BorderPane();
        pane.setCenter(map.getMap());
        pane.setBottom(m_stateBar.getBar());
        return pane;
    }

    private void createStateBar(int numberLife) {
        m_stateBar = new StateBar(numberLife);
    }
    
    private void displayMenu()
    {
    	Menu menu = new Menu();
    	root = new Scene(menu.getRoot());
    	root.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() 
        {
        	String currentPos = menu.getCurrentPos();
            public void handle(KeyEvent ke) {
                String key = "";
                if (ke.getCode() == KeyCode.ENTER)
                {
                	switch (currentPos)
                	{
                		case "QUIT" :
                			System.exit(0);
                		case "PLAY" :
                            gamePlay.nextLevel();
                			launchGame();
                            gamePlay.stopMoves();
                			break;
                		case "HIGHSCORE":
                			displayHighscore();
                			break;
                	}
                }
                else if (ke.getCode() == KeyCode.DOWN)
                    key = "DOWN";
                else if (ke.getCode() == KeyCode.UP)
                    key = "UP";
                ke.consume();
                currentPos = menu.selectMenuChoice(key, currentPos);
                menu.setCurrentPos(currentPos);
                System.out.println(currentPos);
            }
        });
        stage.setScene(root);
    }
    
    private void launchGame()
    {
        root = new Scene(pane);
        root.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                String key = "";
                if (ke.getCode() == KeyCode.LEFT)
                    key = "LEFT";
                if (ke.getCode() == KeyCode.ENTER)
                    gamePlay.alterMoves();
                else if (ke.getCode() == KeyCode.RIGHT)
                    key = "RIGHT";
                else if (ke.getCode() == KeyCode.DOWN)
                    key = "DOWN";
                else if (ke.getCode() == KeyCode.UP)
                    key = "UP";
                else if (ke.getCode() == KeyCode.ESCAPE)
                    displayMenu();
                    ke.consume();
                gamePlay.pacmanMove(key);
            }
        });
        stage.setScene(root);
    }

    private void displayHighscore()
    {
    	Highscore highscore = new Highscore();
    	root = new Scene(highscore.getRoot());
    	root.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() 
        {
            public void handle(KeyEvent ke) {
                String key = "";
                if (ke.getCode() == KeyCode.ESCAPE)
                {
                	displayMenu();
                }
            }
        });
        stage.setScene(root);
    }
    public static void main(String[] args) {
        launch(args);
    }


}