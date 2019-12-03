package GamePlay.Entities.Pacman;

import GamePlay.Entities.Entity;
import GamePlay.GamePlay;
import GamePlay.Map.PacMap;
import GamePlay.Map.Position;

public abstract class AbstractPacman extends Entity {
    private GamePlay gamePlay;

    public AbstractPacman(Position position, GamePlay gamePlay) {
        super(PacMap.ENTITIES.PACMAN, position);
        this.position = position;
        this.gamePlay = gamePlay;
    }

    public abstract Entity chooseWhoToKill(Entity e);

    public AbstractPacman getPacman() {
        return this;
    }
}
