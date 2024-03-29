package GamePlay;

import GamePlay.Entities.Entity;
import GamePlay.Entities.Ghost;
import GamePlay.Map.PacMap;

public interface Collision {
    boolean match(Entity e1, Entity e2);

    void takeDecision(GamePlay gamePlay, Entity e1, Entity e2);
}

class PacManFruteCollision implements Collision {
    @Override
    public boolean match(Entity e1, Entity e2) {
        boolean affirmation1, affirmation2;
        affirmation1 = e1.getType() == PacMap.ENTITIES.PACMAN && e2.getType() == PacMap.ENTITIES.FRUTE;
        affirmation2 = e2.getType() == PacMap.ENTITIES.PACMAN && e1.getType() == PacMap.ENTITIES.FRUTE;
        return affirmation1 || affirmation2;
    }

    @Override
    public void takeDecision(GamePlay gamePlay, Entity e1, Entity e2) {
        if (e2.getType() == PacMap.ENTITIES.FRUTE) {
            gamePlay.deleteFrute(e2); // gamePlay.deleteFrute(e1);
        } else {
            gamePlay.deleteFrute(e1); // gamePlay.deleteFrute(e2);*/
        }
    }

}

class PacManGhostCollision implements Collision {
    @Override
    public boolean match(Entity e1, Entity e2) {
        boolean affirmation1, affirmation2;
        affirmation1 = e1.getType() == PacMap.ENTITIES.PACMAN && e2.getType() == PacMap.ENTITIES.GHOST;
        affirmation2 = e2.getType() == PacMap.ENTITIES.PACMAN && e1.getType() == PacMap.ENTITIES.GHOST;
        return affirmation1 || affirmation2;
    }

	@Override
	public void takeDecision(GamePlay gamePlay, Entity e1, Entity e2) {
		if (e1.getType() == PacMap.ENTITIES.GHOST) {
			gamePlay.ghostCollision((Ghost) e1);
		} else {
			gamePlay.ghostCollision((Ghost) e2);
		}
	}
}

class PacManPowerCollision implements Collision {
    @Override
    public boolean match(Entity e1, Entity e2) {
        boolean affirmation1, affirmation2;
        affirmation1 = e1.getType() == PacMap.ENTITIES.PACMAN && (e2.getType() == PacMap.ENTITIES.SLOW_GHOST_POWER ||
                e2.getType() == PacMap.ENTITIES.KILLING_POWER || e2.getType() == PacMap.ENTITIES.SPEED_POWER);
        affirmation2 = e2.getType() == PacMap.ENTITIES.PACMAN && (e1.getType() == PacMap.ENTITIES.SLOW_GHOST_POWER ||
                e1.getType() == PacMap.ENTITIES.KILLING_POWER || e1.getType() == PacMap.ENTITIES.SPEED_POWER);
        return affirmation1 || affirmation2;
    }

    @Override
    public void takeDecision(GamePlay gamePlay, Entity e1, Entity e2) {
        if (e1.getType() == PacMap.ENTITIES.PACMAN) // power est e2
            gamePlay.powerCollision(e2);
        else
            gamePlay.powerCollision(e1);
    }
}