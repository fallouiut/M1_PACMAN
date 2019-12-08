package Motors;

import GamePlay.Entities.Entity;
import GamePlay.Entities.Ghost;
import GamePlay.Map.PacMap;
import GamePlay.Map.Position;
import view.Interface.Sounds;

public class GameMotor {

    private final Sounds sounds;
    private GraphicalMotor graphicalMotor;

    public GameMotor(GraphicalMotor physicalMotor, Sounds sounds) {
        this.graphicalMotor = physicalMotor;
        this.sounds = sounds;
    }

    public boolean launchParty(PacMap stateMap) {
        //System.out.println("launchParty");
        return graphicalMotor.loadMap(stateMap);
    }

    public void victory() {
        // TODO: mapController popup indiquant que le jeu est fini
        Sounds.victory();
    }
    
    public void eatFruit()
    {
    	Sounds.fruit();
    }
    
    public void death()
    {
    	Sounds.death();
    }
    
    public void gameOver()
    {
    	Sounds.gameOver();
    }
    
    public void loseLife()
    {
    	Sounds.ghost();
    }
    
    public void takeBonus()
    {
    	Sounds.bonus();
    }  

    public void makeMove(Entity entity, Position end) {
        graphicalMotor.translation(entity, end);
    }
	
    // TODO : Il faut appeller cette fonction quand le score est modifié
    public void setScore(int score) {
        graphicalMotor.setScore(score);
    }
    // TODO : Il faut appeller cette fonction quand le nombre de vie est modifié
    public void setLife(int life) {
        graphicalMotor.setLife(life);
    }

    public void deleteFrute(Entity e, PacMap.ENTITIES sub) {
        //physicalMotor.remove(e, sub);
        Sounds.fruit();
    }

    public void removePacman(Entity e, PacMap.ENTITIES sub) {
        //physicalMotor.remove(e, sub);
        Sounds.death();
    }

    public void removeGhost(Ghost g) {
        System.out.println("GameMotor.removeGhost()");
        graphicalMotor.removeGhost(g);
        Sounds.ghost();
    }

    public void frute() {
        Sounds.fruit();
    }

    public void power() {
        Sounds.power();
    }

    public void powerEnd() {
        Sounds.ghost();
        Sounds.bonus();
    }
}
