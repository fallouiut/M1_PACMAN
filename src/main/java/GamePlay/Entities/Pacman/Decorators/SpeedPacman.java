package GamePlay.Entities.Pacman.Decorators;

import GamePlay.Entities.Entity;
import GamePlay.Entities.Pacman.AbstractPacman;
import GamePlay.Map.Position;

public class SpeedPacman extends AbstractPacman {
    private AbstractPacman decorated;
    private int speedAdded;

    public SpeedPacman(AbstractPacman pacman, int speedAdded) {
        super(pacman.getPosition());
        this.decorated = pacman;
        this.speedAdded = speedAdded;
        // TODO Auto-generated constructor stub
    }

    @Override
    public Entity chooseWhoToKill(Entity e) {
        return null;
    }

    @Override
    public AbstractPacman getPacman() {
        return decorated;
    }

	@Override
	public Position getPosition() {
		return decorated.getPosition();
	}

	public void setPosition(Position p) {
    	this.decorated.setPosition(p);
	}

    @Override
    public int getSpeed() {
        return this.decorated.getSpeed() + this.speedAdded;
    }
}