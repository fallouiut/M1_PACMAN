package GamePlay.Entities.Pacman.Decorators;

import GamePlay.Entities.Pacman.AbstractPacman;
import GamePlay.GamePlay;
import GamePlay.Entities.Entity;
import GamePlay.Entities.Pacman.SimplePacman;

public class KillingPacman extends SimplePacman
{

	private AbstractPacman decoratedPacman;

	public KillingPacman(AbstractPacman decoratedPacman, GamePlay gamePlay) {
		super(decoratedPacman.getPosition(), gamePlay);
		this.decoratedPacman = decoratedPacman;
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public Entity chooseWhoToKill(Entity e)
    {
    	// on ignore decoratedPacman.chooseWhoToKill()
		System.out.println("Killing");
    	return e;
    }
	
}
