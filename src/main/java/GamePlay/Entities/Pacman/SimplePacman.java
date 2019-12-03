package GamePlay.Entities.Pacman;

import GamePlay.GamePlay;
import GamePlay.Entities.Entity;
import GamePlay.Map.Position;

public class SimplePacman extends AbstractPacman 
{
    public SimplePacman(Position position, GamePlay gamePlay) 
    {
        super(position, gamePlay);
    }
    
    @Override
    public Entity chooseWhoToKill(Entity e)
    {
    	return this;
    }
}
