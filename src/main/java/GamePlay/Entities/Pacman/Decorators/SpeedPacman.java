package GamePlay.Entities.Pacman.Decorators;

import GamePlay.GamePlay;
import GamePlay.Entities.Pacman.SimplePacman;
import GamePlay.Map.Position;

public class SpeedPacman extends SimplePacman
{

	private final int SPEED_BONUS = 20;
	public SpeedPacman(Position position, GamePlay gamePlay) {
		super(position, gamePlay);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getSpeed()
	{
		return speed + SPEED_BONUS;
		
	}
	
}
