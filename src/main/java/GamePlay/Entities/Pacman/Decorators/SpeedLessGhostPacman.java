package GamePlay.Entities.Pacman.Decorators;

import GamePlay.Entities.Ghost;
import GamePlay.GamePlay;

public class SpeedLessGhostPacman extends Ghost
{
	private Ghost decoratedGhost;
	private int speedMinus;
	// TODO: Il faut acceder aux entités qui correspondent aux ghosts ?
	public SpeedLessGhostPacman(Ghost decorated, GamePlay gamePlay, int speedMinus) {
		super(decorated.getNumGhost(), decorated.getPosition(), gamePlay);
		this.decoratedGhost = decorated;
		this.speedMinus = speedMinus;
		// TODO Auto-generated constructor stub
	}

	public int getSpeed() {
		return decoratedGhost.getSpeed() - speedMinus;
	}

	public Ghost getGhost() {
		return decoratedGhost;
	}
}
