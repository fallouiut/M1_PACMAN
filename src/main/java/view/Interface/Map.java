package view.Interface;
import java.util.ArrayList;

import view.Controller.MapController;
import view.Interface.Cell.ENTITIES;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Map extends Pane 
{
	ArrayList <ImageView> m_fruits = new ArrayList <ImageView> ();
	ArrayList <ImageView> m_ghosts = new ArrayList <ImageView> ();
	ImageView m_pacman;
	
	public ImageView addTile(Image image, int x, int y, Cell.ENTITIES entity)
	{
	  ImageView imageView = new ImageView();
      imageView.setImage(image);
	  imageView.setX(x); 
	  imageView.setY(y);
	  imageView.setFitHeight(MapController.CONFIG_X); 
	  imageView.setFitWidth(MapController.CONFIG_Y);         
	  imageView.setPreserveRatio(true);
	  if (entity == ENTITIES.FRUTE)
		  m_fruits.add(imageView);
	  else if (entity == ENTITIES.GHOST)
		  m_ghosts.add(imageView);
	  else if (entity == ENTITIES.PACMAN)
		  m_pacman = imageView;
	  else
		  getChildren().add(imageView);
	  return imageView;
	}
	
	public void build()
	{
		for (ImageView fruit : m_fruits)
			getChildren().add(fruit);
		for (ImageView ghosts : m_ghosts)
			getChildren().add(ghosts);
		getChildren().add(m_pacman);
	}
	
	public void refresh()
	{
		this.getChildren().clear();
		m_fruits.clear();
		m_ghosts.clear();
		m_pacman = null;
	}
}
