package GamePlay.Entities.Pacman.Decorators;

import GamePlay.Entities.Ghost;
import GamePlay.GamePlay;
import GamePlay.Map.Position;

/*
	Decorateur qui diminue la vitesse d'un ghost
 */
public class SlowGhost extends Ghost
{
	private Ghost decoratedGhost;
	private int speedMinus;
	// TODO: Il faut acceder aux entités qui correspondent aux ghosts ?
	public SlowGhost(Ghost decorated, GamePlay gamePlay, int speedMinus) {
		super(decorated.getNumGhost(), decorated.getPosition(), gamePlay);
		this.decoratedGhost = decorated;
		this.speedMinus = speedMinus;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Position getPosition() {
		return decoratedGhost.getPosition();
	}

	@Override
	public void setPosition(Position p) {
		decoratedGhost.setPosition(p);
	}

	@Override
	public int getSpeed() {
		return decoratedGhost.getSpeed() - speedMinus;
	}

	public Ghost getGhost() {
		return decoratedGhost;
	}


}
