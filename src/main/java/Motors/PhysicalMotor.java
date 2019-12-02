package Motors;

import GamePlay.Entities.Entity;
import GamePlay.Map.PacMap;
import GamePlay.Map.PacMap.ENTITIES;
import GamePlay.Map.Position;
import view.Controller.MapController;

public class PhysicalMotor {

    private MapController window;
    private PacMap stateMap;

    public PhysicalMotor(MapController mapController) throws Exception
    {
        this.window = mapController;
    }
    
    public void translation(Entity entity, Position newOne) {
        System.out.println("PhysicalMotor.translation()");
        if (entity.getType() == ENTITIES.PACMAN)
        {
            System.out.println("On fait un move");
        	window.movePacman(newOne, entity.getSpeed());
        	// ATTENTION POUR L'INSTANT MOVEPACMAN NE DEPLACE FORCEMENT QUE D'UNE CASE
            // OUI MAIS TKT LES MOUVEMENTS DES GHOSTS ET DES FLECHES C CASE PAR CASE
        	// A utiliser dans une boucle du coup
        }
        // TODO: calculer le nv moubement en fonction de currentPosition et newPosition
    }

    public void checkCollision() {
        // chercher une collision
    }

    public void loadMap(PacMap stateMap)
    {
        System.out.println("loadMap");
    	window.initializeMap(stateMap);
    	window.getMap().build();
    }

    public void setScore(int score) {
    }

    public void setLife(int life) {
    }
}
