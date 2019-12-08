package Motors;

import GamePlay.Entities.Entity;
import GamePlay.Entities.Ghost;
import GamePlay.Map.PacMap;
import GamePlay.Map.Position;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import view.Controller.MapController;
import view.Interface.Sprites;

public class PhysicalCalculsMoteur {

    public static Pair getDiffXY(Position p1, Position p2) {
        int xStart, xEnd, yStart, yEnd, difX, difY;
        xStart = p1.getX();
        xEnd = p2.getX();
        yStart = p1.getY();
        yEnd = p2.getY();
        difX = xEnd - xStart;
        difY = yEnd - yStart;

        if (difX != 0 && difY != 0)
            System.err.println("Error in MapController.movePacman()");

        return new Pair(difX, difY);
    }

    public static TranslateTransition getTranslateTransition(int x, int y, Entity e, MapController map) {
        ImageView imageToMove = null;
        TranslateTransition translateTransition = new TranslateTransition();
        if (e.getType() == PacMap.ENTITIES.PACMAN) {
            imageToMove = map.getPacman();
            translateTransition.setNode(imageToMove);
            translateTransition.setDuration(Duration.millis(e.getSpeed()));

            if (y == 1) {
                translateTransition.setByX(MapController.CONFIG_X);
                imageToMove.setImage(Sprites.orientatePacman(imageToMove.getImage(), "RIGHT"));
            } else if (y == -1) {
                translateTransition.setByX(-MapController.CONFIG_X);
                imageToMove.setImage(Sprites.orientatePacman(imageToMove.getImage(), "LEFT"));
            } else if (x == 1) {
                translateTransition.setByY(MapController.CONFIG_Y);
                imageToMove.setImage(Sprites.orientatePacman(imageToMove.getImage(), "DOWN"));
            } else if (x == -1) {
                translateTransition.setByY(-MapController.CONFIG_Y);
                imageToMove.setImage(Sprites.orientatePacman(imageToMove.getImage(), "UP"));
            }
        } else if (e.getType() == PacMap.ENTITIES.GHOST) {
            Ghost g = (Ghost) e;

            imageToMove = map.getM_ghosts().get(g.getNumGhost());

            translateTransition.setNode(imageToMove);
            translateTransition.setDuration(Duration.millis(e.getSpeed()));

            if (y == 1) {
                translateTransition.setByX(MapController.CONFIG_X);
            } else if (y == -1) {
                translateTransition.setByX(-MapController.CONFIG_X);
            } else if (x == 1) {
                translateTransition.setByY(MapController.CONFIG_X);
            } else if (x == -1) {
                translateTransition.setByY(-MapController.CONFIG_X);
            }
        } else
            System.err.println("PhysicalCalculMotors.getTranslateTransition()");

        return translateTransition;
    }

    public static Pair computePixelPosition(Position p) {
        int pixelX = p.getX() * MapController.CONFIG_X;
        int pixelY = p.getY() * MapController.CONFIG_Y;
        return new Pair(pixelX, pixelY);
    }

    public static class Pair {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
