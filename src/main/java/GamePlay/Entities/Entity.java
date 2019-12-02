package GamePlay.Entities;

import GamePlay.Map.PacMap;
import GamePlay.Map.Position;

public abstract class Entity {

    protected PacMap.ENTITIES type;
    protected Position position;
    protected int speed;
    public Entity(PacMap.ENTITIES type, Position position)
    {
        this.type = type;
        this.position = position;
        this.speed = 10;
    }

    public PacMap.ENTITIES getType() {return type;}
    public Position getPosition() {return position;}
    public int getSpeed() {return speed; }
    public  void setPosition(Position newOne) {
        this.position = newOne;
    }
}
