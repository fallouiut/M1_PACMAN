package GamePlay.Entities;

import GamePlay.Map.PacMap.ENTITIES;
import GamePlay.Map.Position;

public class PacmanEntity extends Entity {

	public PacmanEntity(Position position) 
	{
		super(ENTITIES.PACMAN, position);
	}
}
