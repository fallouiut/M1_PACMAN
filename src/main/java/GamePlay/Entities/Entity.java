package GamePlay.Entities;

import GamePlay.Map.PacMap;
import GamePlay.Map.Position;

public abstract class Entity {

    protected PacMap.ENTITIES type;
    protected Position position;

    public Entity(PacMap.ENTITIES type, Position position){
        this.type = type;
        this.position = position;
    }

    public PacMap.ENTITIES getType() {return type;}
    public Position getPosition() {return position;}

    public  void setPosition(Position newOne) {
        this.position = newOne;
    }
}
