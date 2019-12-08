package Motors;

import GamePlay.Entities.Entity;
import GamePlay.Map.PacMap;
import GamePlay.Map.Position;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.BorderPane;
import view.Controller.MapController;
import view.Interface.PacmanAnimation;
import view.Interface.StateBar;

public class GraphicalMotor {

    private MapController window;
    private StateBar stateBar;
    private PacmanAnimation pacmanAnimation;
    private BorderPane pane;

    public GraphicalMotor(MapController mapController, StateBar stateBar, PacmanAnimation pacmanAnimation, BorderPane borderPane) throws Exception {
        this.window = mapController;
        this.stateBar = stateBar;
        this.pacmanAnimation = pacmanAnimation;
        this.pane = borderPane;
    }

    public void translation(Entity entity, Position end) {
        PhysicalCalculsMoteur.Pair p = PhysicalCalculsMoteur.getDiffXY(entity.getPosition(), end);
        TranslateTransition translateTransition = PhysicalCalculsMoteur.getTranslateTransition(p.getX(), p.getY(), entity, window);
        PhysicalCalculsMoteur.Pair currentPixelPosition = PhysicalCalculsMoteur.computePixelPosition(entity.getPosition());

        window.moveEntity(entity, end, translateTransition, currentPixelPosition);

    }

    public boolean loadMap(PacMap stateMap) {
        window = new MapController();
        boolean isDone = window.initializeMap(stateMap);

        pane.setCenter(window.getMap());
        pane.setBottom(stateBar.getBar());

        pacmanAnimation = new PacmanAnimation(window);
        pacmanAnimation.start();

        return isDone;
    }

    public void setScore(int score) {
        stateBar.setScore(score);
    }

    public void setLife(int life) {
        stateBar.setLife(life);
    }

    public void remove(Entity entity) {
        //System.out.println("physicalMotor.remove()");
        PhysicalCalculsMoteur.Pair currentPixelPosition = PhysicalCalculsMoteur.computePixelPosition(entity.getPosition());
        window.deleteEntity(entity, currentPixelPosition);
        //window.getMap().replaceImage(e.getPosition().getX(), e.getPosition().getY(), ENTITIES.EMPTY);
    }

    public void removeGhost(Entity g) {
        System.out.println("physicalMotor.removeGhost()");
        window.removeGhost(g);
        //window.getMap().replaceImage(e.getPosition().getX(), e.getPosition().getY(), ENTITIES.EMPTY);
    }
}
