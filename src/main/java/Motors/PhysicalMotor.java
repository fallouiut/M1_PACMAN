package Motors;

import GamePlay.Entities.Entity;
import GamePlay.Entities.Ghost;
import GamePlay.Map.PacMap;
import GamePlay.Map.PacMap.ENTITIES;
import GamePlay.Map.Position;
import javafx.scene.layout.BorderPane;
import view.Controller.MapController;
import view.Interface.PacmanAnimation;
import view.Interface.StateBar;

public class PhysicalMotor {

    private MapController window;
    private StateBar stateBar;
    private PacmanAnimation pacmanAnimation;
    private BorderPane pane;

    public PhysicalMotor(MapController mapController, StateBar stateBar, PacmanAnimation pacmanAnimation, BorderPane borderPane) throws Exception
    {
        this.window = mapController;
        this.stateBar = stateBar;
        this.pacmanAnimation = pacmanAnimation;
        this.pane = borderPane;
    }
    
    public void translation(Entity entity, Position end) {
		int xStart, xEnd, yStart, yEnd, difX, difY;
		xStart = entity.getPosition().getX();
		xEnd = end.getX();
		yStart = entity.getPosition().getY();
		yEnd = end.getY();
		difX = xEnd - xStart;
		difY = yEnd - yStart;
		if (difX != 0 && difY != 0)
			System.err.println("Error in MapController.movePacman()");
        window.moveEntity(entity, end, difX, difY, entity.getSpeed());
    }

    public boolean loadMap(PacMap stateMap)
    {
        window = new MapController();
        boolean isDone = window.initializeMap(stateMap);

        pane.setCenter(window.getMap());
        pane.setBottom(stateBar.getBar());

        pacmanAnimation = new PacmanAnimation(window);
        pacmanAnimation.start();


    	return isDone;
    	//this.pacmanAnimation.start();
    }

    public void setScore(int score)
    {
    	stateBar.setScore(score);
    }

    public void setLife(int life)
    {
    	stateBar.setLife(life);
    }

    public void remove(Entity e) {
        //System.out.println("physicalMotor.remove()");
        window.deleteEntity(e);
        //window.getMap().replaceImage(e.getPosition().getX(), e.getPosition().getY(), ENTITIES.EMPTY);
    }

    public void removeGhost(Entity g) {
        System.out.println("physicalMotor.removeGhost()");
        window.removeGhost(g);
        //window.getMap().replaceImage(e.getPosition().getX(), e.getPosition().getY(), ENTITIES.EMPTY);
    }
}
