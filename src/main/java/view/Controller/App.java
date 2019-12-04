package view.Controller;

import GamePlay.GamePlay;
import Motors.GameMotor;
import Motors.PhysicalMotor;
import javafx.scene.layout.BorderPane;
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
    private PhysicalMotor physicalMotor;
    private Sounds sounds;
    
    private Stage stage;

    public void start(Stage stage) throws Exception {
    	this.stage = stage;
        this.pane = initInterface();
        physicalMotor = new PhysicalMotor(map, m_stateBar);
        sounds = new Sounds();
        gameMotor = new GameMotor(physicalMotor, sounds);
        gamePlay = new GamePlay();
        gamePlay.setGameMotor(gameMotor);
        stage.setTitle("Pacman");
        try {
            // lance le jeu
            gamePlay.nextLevel();
        } catch (Exception e) {
            System.out.println("ApplicationController.start()");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
        displayMenu();
        stage.show();
    }

    public BorderPane initInterface() {
        new Sprites();
        map = new MapController();
        createStateBar(0);
        BorderPane pane = new BorderPane();
        pane.setCenter(map.getMap());
        pane.setBottom(m_stateBar.getBar());
        m_pacmanAnim = new PacmanAnimation(map);
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
                			launchGame();
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
        m_pacmanAnim.start();
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