package GamePlay.Entities;

import java.util.Random;

import GamePlay.GamePlay;
import GamePlay.Map.Position;

public class PacmanPower {
	private static final int LASTING = 5;
    private float time;

    private Position currentPosition;
    private GamePlay gamePlay;
    private TYPE type;
    // TODO: choisir des motifs pour chacun d'entre eux
    // TODO: pouvoir les génerer aléatoirement avec timers de début et de fin
    public enum TYPE { NONE, INVINCIBLE_PACMAN, KILLING_GHOST, MORE_PACMAN_SPEED, LESS_GHOST_SPEED }

    public PacmanPower(Position position) 
    {
        this.currentPosition = position;
        this.gamePlay = gamePlay;
        this.type = pickPower();
        this.time = System.nanoTime();
        applyPower();
    }
    
    private void applyPower()
    {
        
        float elapsedNanoSeconds;
        double elapsedSeconds;
        while (true)
        {
            elapsedNanoSeconds = System.nanoTime() - time;
            elapsedSeconds = elapsedNanoSeconds / 1_000_000_000.0;
            if (elapsedSeconds > LASTING)
            	break;
        }
        removePower();
    }

	private void removePower() 
	{
		// TODO Auto-generated method stub
		
	}
	
	private TYPE pickPower()
	{
		Random rand = new Random();
		int differentPowers = 4;
		int powerChosen = rand.nextInt(differentPowers);
		switch (powerChosen)
		{
			case 0 :
				return TYPE.NONE;
			case 1 :
				return TYPE.INVINCIBLE_PACMAN;
			case 2 :
				return TYPE.KILLING_GHOST;
			case 3 :
				return TYPE.LESS_GHOST_SPEED;
			case 4 :
				return TYPE.MORE_PACMAN_SPEED;
			default :
				return TYPE.KILLING_GHOST;
		}
	}
	
	
	
	
}
