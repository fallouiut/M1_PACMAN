package Motors;

import GamePlay.Entities.Entity;
import GamePlay.Map.PacMap;
import GamePlay.Map.PacMap.ENTITIES;
import GamePlay.Map.Position;
import view.Controller.MapController;
import view.Interface.Sprites;

public class PhysicalMotor {

    private MapController window;

    public PhysicalMotor(MapController mapController) throws Exception
    {
        this.window = mapController;
    }
    
    public void translation(Entity entity, Position end) {
        //System.out.println("trnaslate() Current : " + entity.getPosition().toString());
        //System.out.println("PhysicalMotor.translation() new: " + end.toString());
		int xStart, xEnd, yStart, yEnd, difX, difY;
		xStart = entity.getPosition().getX();
		xEnd = end.getX();
		yStart = entity.getPosition().getY();
		yEnd = end.getY();
		difX = xEnd - xStart;
		difY = yEnd - yStart;
		if (difX != 0 && difY != 0)
			System.err.println("Error in MapController.movePacman()");
        window.moveEntity(entity, difX, difY, entity.getSpeed());
    }

    public void loadMap(PacMap stateMap)
    {
        //System.out.println("loadMap");
    	window.initializeMap(stateMap);
    }

    public void setScore(int score) 
    {
    }

    public void setLife(int life) {
    }

    public void remove(Entity e, ENTITIES sub) {
        System.out.println("PhysicalMotor.removeFrute()");
        window.getMap().replaceImage(e.getPosition().getX(), e.getPosition().getY(), sub);
    }
}
