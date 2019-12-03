package view.Interface;

import java.util.ArrayList;

import GamePlay.Map.PacMap.ENTITIES;
import view.Controller.MapController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Map extends Pane {
    public ImageView addTile(Image image, double x, double y, ENTITIES entity) {
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitHeight(MapController.CONFIG_X);
        imageView.setFitWidth(MapController.CONFIG_Y);
        imageView.setPreserveRatio(true);
        getChildren().add(imageView);
        return imageView;
    }

    public void replaceImage(double x, double y, ENTITIES entity) {
        System.out.println("Map.replaceImage()");
        ImageView imageView = new ImageView();
        switch (entity) 
        {
            case FRUTE:
                imageView.setImage(Sprites.empty_fruit);
                break;
            case EMPTY:
                imageView.setImage(Sprites.empty);
                break;
            case KILLING_POWER:
                imageView.setImage(Sprites.super_fruit);
                break;
            case SLOW_GHOST_POWER:
                imageView.setImage(Sprites.super_fruit);
                break;
            case SPEED_POWER:
                imageView.setImage(Sprites.super_fruit);
                break;
            default:
                imageView.setImage(Sprites.empty);
                break;
        }
        imageView.setX(y * MapController.CONFIG_Y);
        imageView.setY(x * MapController.CONFIG_X);
        imageView.setFitHeight(MapController.CONFIG_X);
        imageView.setFitWidth(MapController.CONFIG_Y);
        imageView.setPreserveRatio(true);
        getChildren().add(imageView);
    }

    public void preserveFrontground(ArrayList<ImageView> ghosts, ImageView pacman) {
        for (ImageView image : ghosts) 
        {
            getChildren().remove(image);
            getChildren().add(image);
        }
        getChildren().remove(pacman);
        getChildren().add(pacman);

    }

    public void refresh() {
        this.getChildren().clear();
    }
}
