package Motors;

import GamePlay.Entities.Entity;
import GamePlay.GamePlay;
import GamePlay.Map.PacMap;
import GamePlay.Map.Position;
import view.Interface.Sounds;

public class GameMotor {

    private PhysicalMotor physicalMotor;

    public void launchParty(PacMap stateMap) {
        physicalMotor.initMap(stateMap);
    }

    public void gameIsReached() {
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
    	Sounds.lifeLost();
    }
    
    public void takeBonus()
    {
    	Sounds.bonus();
    }  

    public void makeMove(Entity entity, Position newOne) {
        System.out.println("gameMotor.makeMove()");
        physicalMotor.translation(entity, newOne);
    }
	
    // TODO : Il faut appeller cette fonction quand le score est modifié
    public void setScore(int score) {
        physicalMotor.setScore(score);
    }
    // TODO : Il faut appeller cette fonction quand le nombre de vie est modifié
    public void setLife(int life) {
    	physicalMotor.setLife(life);
    }
   
}
