package view.Interface;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import view.Controller.MapController;


public class PacmanAnimation extends AnimationTimer {

    private boolean m_initiated = false;
    private long m_lastUpdate;
    private MapController map;

    public PacmanAnimation(MapController map) {
        this.map = map;
    }

    @Override
    public void handle(long arg0) {
        long elapsedNanoSeconds = System.nanoTime() - m_lastUpdate;
        double elapsedSeconds = elapsedNanoSeconds / 1_000_000_000.0;
        if (elapsedSeconds < 1)
            return;
        m_lastUpdate = System.nanoTime();
        ImageView currentPacman = this.map.getPacman();
        currentPacman.setImage(Sprites.animatePacman(currentPacman.getImage()));
    }
}
