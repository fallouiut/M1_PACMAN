package GamePlay.Entities;

import GamePlay.GamePlay;
import GamePlay.Map.Position;

public class PacmanPower {
    private boolean actionned = false;
    private float time;

    private Position currentPosition;
    private GamePlay gamePlay;
    private TYPE type;

    // TODO: choisir des motifs pour chacun d'entre eux
    // TODO: pouvoir les génerer aléatoirement avec timers de début et de fin
    public enum TYPE { NONE, INVINCIBLE_PACMAN, KILLING_GHOST, MORE_PACMAN_SPEED, LESS_GHOST_SPEED }

    public PacmanPower(Position position, TYPE type) {
        this.currentPosition = position;
        this.gamePlay = gamePlay;
        this.type = type;
    }

}
