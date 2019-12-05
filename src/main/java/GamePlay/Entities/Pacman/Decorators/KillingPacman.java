package GamePlay.Entities.Pacman.Decorators;

import GamePlay.Entities.Pacman.AbstractPacman;
import GamePlay.GamePlay;
import GamePlay.Entities.Entity;
import GamePlay.Map.Position;

public class KillingPacman extends AbstractPacman {

    private AbstractPacman decoratedPacman;

    public KillingPacman(AbstractPacman decoratedPacman) {
        super(decoratedPacman.getPosition());
        this.decoratedPacman = decoratedPacman;
        // TODO Auto-generated constructor stub
    }

    @Override
    public Entity chooseWhoToKill(Entity e) {
        // on ignore decoratedPacman.chooseWhoToKill()
        return e;
    }

	@Override
	public Position getPosition() {
		return this.decoratedPacman.getPosition();
	}

	@Override
	public void setPosition(Position p) {
		this.decoratedPacman.setPosition(p);
	}

	@Override
	public int getSpeed() {
		return decoratedPacman.getSpeed();
	}

	public AbstractPacman getPacman() {
        return this.decoratedPacman;
    }
}