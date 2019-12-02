package view.Interface;

import GamePlay.Map.PacMap.ENTITIES;
import view.Controller.MapController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Map extends Pane 
{
	ImageView m_pacman;
	
	public ImageView addTile(Image image, double x, double y, ENTITIES entity)
	{
		  ImageView imageView = new ImageView();
		  imageView.setImage(image);
		  imageView.setX(x); 
		  imageView.setY(y);
		  imageView.setFitHeight(MapController.CONFIG_X); 
		  imageView.setFitWidth(MapController.CONFIG_Y);         
		  imageView.setPreserveRatio(true);
		  if (entity == ENTITIES.PACMAN)
			  m_pacman = imageView;
		  getChildren().add(imageView);
		  return imageView;
	}
	
	public void replaceImage(double x, double y)
	{
		  ImageView imageView = new ImageView();
	      imageView.setImage(Sprites.empty);
		  imageView.setX(y * MapController.CONFIG_Y); 
		  imageView.setY(x * MapController.CONFIG_X);
		  imageView.setFitHeight(MapController.CONFIG_X); 
		  imageView.setFitWidth(MapController.CONFIG_Y);         
		  imageView.setPreserveRatio(true);
		  getChildren().add(imageView);
		  getChildren().remove(m_pacman);
		  getChildren().add(m_pacman);
	}
	
	public void refresh()
	{
		this.getChildren().clear();
		m_pacman = null;
	}
}
