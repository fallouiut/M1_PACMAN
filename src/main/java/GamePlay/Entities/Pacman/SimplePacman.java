package GamePlay.Entities.Pacman;

import GamePlay.GamePlay;
import GamePlay.Entities.Entity;
import GamePlay.Map.Position;

public class SimplePacman extends AbstractPacman 
{
    public SimplePacman(Position position)
    {
        super(position);
    }
    
    @Override
    public Entity chooseWhoToKill(Entity e)
    {
    	return this;
    }

    @Override
    public AbstractPacman getPacman() {
        return this;
    }
}