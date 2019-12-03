package GamePlay.Entities.Pacman.Decorators;

import GamePlay.GamePlay;
import GamePlay.Entities.Entity;
import GamePlay.Entities.Pacman.SimplePacman;
import GamePlay.Map.Position;
import GamePlay.Map.PacMap.ENTITIES;

public class KillingPacman extends SimplePacman
{

	public KillingPacman(Position position, GamePlay gamePlay) {
		super(position, gamePlay);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public Entity chooseWhoToKill(Entity e)
    {
    	return e;
    }
	
}
