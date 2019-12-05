package GamePlay.Entities;

import GamePlay.Map.PacMap;
import GamePlay.Map.Position;

public class Entity {

    protected PacMap.ENTITIES type;
    protected Position position;
    protected int speed;

    public Entity(PacMap.ENTITIES type, Position position) {
        this.type = type;
        this.position = position;
        this.speed = 10;
    }

    public PacMap.ENTITIES getType() {
        return type;
    }

    // fonctione @Overridées par les décorateurs
    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position p) {
        this.position = p;
    }

    public int getSpeed() {
        return this.speed;
    }
}
