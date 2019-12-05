package GamePlay.Entities.Pacman;

import GamePlay.Entities.Entity;
import GamePlay.GamePlay;
import GamePlay.Map.PacMap;
import GamePlay.Map.Position;

public abstract class AbstractPacman extends Entity {
    public AbstractPacman(Position position) {
        super(PacMap.ENTITIES.PACMAN, position);
        this.position = position;
    }

    public abstract Entity chooseWhoToKill(Entity e);

    public abstract AbstractPacman getPacman();
}
