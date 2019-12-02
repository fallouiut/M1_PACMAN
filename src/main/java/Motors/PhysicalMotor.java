package Motors;

import GamePlay.GamePlay;
import GamePlay.Entities.Entity;
import GamePlay.Map.PacMap;
import GamePlay.Map.PacMap.ENTITIES;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import GamePlay.Map.Position;
import view.Controller.MapController;
import view.Interface.Sounds;
import view.Interface.Sprites;
import view.Interface.StateBar;

import java.util.ArrayList;
import java.util.List;

public class PhysicalMotor extends Application {

    private MapController window;
	// Composantes de l'interface graphique

    private final int SPEED = 10;
    private PacmanAnimation m_pacmanAnim;
    private StateBar m_stateBar;
    private Scene m_root;
    
    private GamePlay gamePlay;

    // TODO: IMPORTANT 
    // J'AURAI BESOIN QUE TU RAJOUTE UNE LISTE DE TOUS LES ELEMENTS FRUIT PACMAN
    // QUI PEUT ETRE AU LOADING DE (MAP -> WINDOW) GENRE JUSTE UNE MATRICE D'ENTITIES
    // SI TU PEUX MODIFIER CA  
    // DE FAIRE UNE FONCTION DE DETECTION DE COLLISION EN PARCOURANT LA MATRICE D'ENTITIES
    // QUI VERIFIE QUI EST A CETTE POSITION
    // MODIFIER UN PEU MAPCONTROLLER POUR PRENDRE LA POSITION EN PARAMETRE DE MOVE() ET NON UN STRING
    // DE MODIFIER LE CHARGEMENT DE LA MAP POUR QU'ELLE PRENNE UN PaCMAP EN PARAMETRE
    // si tu lance l'appli et que tu cliques sur une fleche tu vas voir tout le cheminement de gameplay a ici

    List<Entity> entities = new ArrayList<> ();

    public PhysicalMotor() throws Exception 
    {
        new Sounds();
        new Sprites();
    	this.gamePlay = new GamePlay();
    	GameMotor gameMotor = new GameMotor();
        gamePlay.setGameMotor(gameMotor);
       	initInterface();
        entities = this.window.getEntities();
    }

    // Permet d'animer Pacman
    private class PacmanAnimation extends AnimationTimer 
    {
        private long m_lastUpdate;
        private ImageView m_pacman;
        
        public PacmanAnimation(ImageView pacman)
        {
        	m_pacman = pacman;     	
        }
        
        @Override
        public void handle(long arg0) {
            long elapsedNanoSeconds = System.nanoTime() - m_lastUpdate;
            double elapsedSeconds = elapsedNanoSeconds / 1_000_000_000.0;
            if (elapsedSeconds < 1)
                return;
            m_lastUpdate = System.nanoTime();
            ImageView currentPacman = m_pacman;
            currentPacman.setImage(Sprites.animatePacman(currentPacman.getImage()));
        }
    }
    
    public void translation(Entity entity, Position newOne) {
        System.out.println("PhysicalMotor.translation()");
        if (entity.getType() == ENTITIES.PACMAN)
        {
        	window.movePacman(newOne); 
        	// ATTENTION POUR L'INSTANT MOVEPACMAN NE DEPLACE FORCEMENT QUE D'UNE CASE
        	// A utiliser dans une boucle du coup
        }
        // TODO: calculer le nv moubement en fonction de currentPosition et newPosition
    }

    public void checkCollision() {
        // chercher une collision
    }

	public void start(Stage stage)
	{
        m_root.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.LEFT)
                    gamePlay.pacmanMove("LEFT");
                else if (ke.getCode() == KeyCode.RIGHT)
                	gamePlay.pacmanMove("RIGHT");
                else if (ke.getCode() == KeyCode.DOWN)
                    gamePlay.pacmanMove("DOWN");
                else if (ke.getCode() == KeyCode.UP)
                	gamePlay.pacmanMove("UP");
                ke.consume();
            }
        });
        try {
            // lance le jeu
           gamePlay.nextLevel();
        } catch (Exception e) {
            System.out.println("GameMotor.start()");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        m_pacmanAnim = new PacmanAnimation(window.getPacman());
        m_pacmanAnim.start();
        stage.setScene(m_root);
        stage.setTitle("Pacman");
        stage.show();
        
	}
	
    public void refresh(PacMap pacmap) 
    {
    	m_pacmanAnim.stop();
        window.refresh(pacmap, SPEED);
        m_stateBar.refresh(gamePlay.getLifeNumber());
        m_pacmanAnim.start();
        // TODO: A tester
    }
	
	public void initInterface()
	{	
        window = new MapController(SPEED);
        createStateBar(gamePlay.getLifeNumber());
        BorderPane pane = new BorderPane();
        pane.setCenter(window.getMap());
        pane.setBottom(m_stateBar.getBar());
        m_root = new Scene(pane);
	}
	
    private void createStateBar(int numberLife) 
    {
        m_stateBar = new StateBar(numberLife);
    }

    public void setScore(int score) {
        m_stateBar.setScore(score);
    }

    
    public void setLife(int life) {
    	m_stateBar.setLife(life);
    }
	

    public void initMap(PacMap stateMap) 
    {
    	window.initializeMap(stateMap);
    }
    

    public void setGamePlay(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }
    
    public static void main(String[] args) {
        launch(args);
    }


}
