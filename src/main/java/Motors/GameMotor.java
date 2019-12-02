package Motors;

import GamePlay.Entities.Entity;
import GamePlay.GamePlay;
import GamePlay.Map.PacMap;
import GamePlay.Map.Position;
import view.Interface.Sounds;

public class GameMotor {

    private final Sounds sounds;
    private PhysicalMotor physicalMotor;

    public GameMotor(PhysicalMotor physicalMotor, Sounds sounds) {
        this.physicalMotor = physicalMotor;
        this.sounds = sounds;
    }

    public void launchParty(PacMap stateMap) {
        System.out.println("launchParty");
        physicalMotor.loadMap(stateMap);
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

    public void makeMove(Entity entity, Position end) {
        System.out.println("gameMotor.makeMove()");
        physicalMotor.translation(entity, end);
    }
	
    // TODO : Il faut appeller cette fonction quand le score est modifi�
    public void setScore(int score) {
        physicalMotor.setScore(score);
    }
    // TODO : Il faut appeller cette fonction quand le nombre de vie est modifi�
    public void setLife(int life) {
    	physicalMotor.setLife(life);
    }
}
