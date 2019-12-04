package GamePlay.Entities;

import java.util.Random;

import GamePlay.GamePlay;
import GamePlay.Map.Position;

public class PacmanPower {

    private Position currentPosition;
    private GamePlay gamePlay;
    private TYPE type;
    public enum TYPE { NONE, INVINCIBLE_PACMAN, KILLING_GHOST, MORE_PACMAN_SPEED, LESS_GHOST_SPEED }

    public PacmanPower(Position position) 
    {
        this.currentPosition = position;
        this.gamePlay = gamePlay;
        this.type = pickPower();
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
