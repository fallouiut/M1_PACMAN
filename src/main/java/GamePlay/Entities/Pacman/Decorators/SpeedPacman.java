package GamePlay.Entities.Pacman.Decorators;

import GamePlay.Entities.Entity;
import GamePlay.Entities.Pacman.AbstractPacman;
import GamePlay.GamePlay;
import GamePlay.Entities.Pacman.SimplePacman;
import GamePlay.Map.Position;

public class SpeedPacman extends AbstractPacman
{
	private AbstractPacman decorated;
	private int speedAdded;

	public SpeedPacman(AbstractPacman pacman, GamePlay gamePlay, int speedAdded) {
		super(pacman.getPosition(), gamePlay);
		this.decorated = pacman;
		this.speedAdded = speedAdded;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Entity chooseWhoToKill(Entity e) {
		return null;
	}

	@Override
	public int getSpeed()
	{
		return decorated.getSpeed() + speedAdded;
		
	}
	
}
