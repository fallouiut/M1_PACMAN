package view.Controller;
import java.util.ArrayList;

import GamePlay.Entities.Entity;
import GamePlay.Entities.Ghost;
import GamePlay.Map.Cell;
import GamePlay.Map.PacMap;
import GamePlay.Map.PacMap.ENTITIES;
import view.Interface.Map;
import view.Interface.Sprites;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class MapController {

	public final static int CONFIG_X = 25;
	public final static int CONFIG_Y = 25;
	
	ImageView m_pacman;
	ArrayList <ImageView> m_ghosts = new ArrayList <ImageView> ();
	private Map m_map = new Map();
	
	private ArrayList <Entity> entities = new ArrayList <Entity> ();
	
	public void refresh(PacMap pacmap, int speed)
	{
		m_map.refresh();
		entities.clear();
		initializeMap(pacmap);
	}

	public boolean moveEntity(Entity e, int x, int y, int speed) 
	{
		ImageView imageToMove = null;
		if (e.getType() == ENTITIES.PACMAN)
		{
			imageToMove = m_pacman;
			TranslateTransition translateTransition = 
					new TranslateTransition(Duration.millis(speed), imageToMove);
			if (y == 1)
			{
				translateTransition.setByX(MapController.CONFIG_X);
		        imageToMove.setImage(Sprites.orientatePacman(imageToMove.getImage(), "RIGHT"));
			}
			else if (y == -1)
			{
				translateTransition.setByX(- MapController.CONFIG_X);
		        imageToMove.setImage(Sprites.orientatePacman(imageToMove.getImage(), "LEFT"));
			}
			else if (x == 1)
			{
				translateTransition.setByY(MapController.CONFIG_X);
		        imageToMove.setImage(Sprites.orientatePacman(imageToMove.getImage(), "DOWN"));
			}
			else if (x == -1)
			{
				translateTransition.setByY(- MapController.CONFIG_X);
		        imageToMove.setImage(Sprites.orientatePacman(imageToMove.getImage(), "UP"));
			}
	        translateTransition.setAutoReverse(false);
	        translateTransition.play();
			replaceImage(e, ENTITIES.EMPTY); // TODO: recupérer ici l'entité "en dessous" du pacman
						// vu que pacman mange les fruits, ce sera souvent une entite vide
		}
		else if (e.getType() == ENTITIES.GHOST)
		{
			Ghost g = (Ghost) e;
			imageToMove = m_ghosts.get(g.getNumGhost());
			TranslateTransition translateTransition = 
					new TranslateTransition(Duration.millis(speed), imageToMove);
			if (y == 1)
			{
				translateTransition.setByX(MapController.CONFIG_X);
			}
			else if (y == -1)
			{
				translateTransition.setByX(- MapController.CONFIG_X);
			}
			else if (x == 1)
			{
				translateTransition.setByY(MapController.CONFIG_X);
			}
			else if (x == -1)
			{
				translateTransition.setByY(- MapController.CONFIG_X);
			}
	        translateTransition.setAutoReverse(false);
	        translateTransition.play();
			replaceImage(e, ENTITIES.FRUTE); // TODO: recupérer ici l'entité "en dessous" du ghost
		}
		else
			System.err.println("Error in MapController.movePacman()");
        return true;
	}
	
	private void replaceImage(Entity e, ENTITIES replaceByThisEntity) 
	{
		int x = e.getPosition().getX();
		int y = e.getPosition().getY();
		m_map.replaceImage(x, y, replaceByThisEntity);
		m_map.preserveFrontground(m_ghosts, m_pacman);
	}

	public ImageView getPacman()
	{
		return m_pacman;
	}
	
	public Map getMap()
	{
		return m_map;
	}
	
	public void initializeMap(PacMap pacmap)
	{
		System.out.println("initMap");
		Cell[][] cells = pacmap.getLabyrinth();
		Cell currentCell;
		double x = 0;
		double y = 0;
		int nGhost = 0;
		for (int i = 0; i < cells.length; i++)
		{
			for (int j = 0; j < cells[i].length; j++)
			{
				currentCell = cells[i][j];
				if (currentCell.getMainElem() == ENTITIES.EMPTY)
				{
					m_map.addTile(Sprites.empty, x, y, ENTITIES.EMPTY);
				}
				else if (currentCell.getMainElem() == ENTITIES.FRUTE)
				{
					//entities.add(new Fruit(new Position(x, y)));
					m_map.addTile(Sprites.empty_fruit, x, y, ENTITIES.FRUTE);
				}
				else if (currentCell.getMainElem() == ENTITIES.PACMAN)
				{
					//entities.add(new PacmanEntity(new Position(x, y)));
					System.out.println("pacman trouvé");
					m_pacman = m_map.addTile(Sprites.pacman_left2, x, y, ENTITIES.PACMAN);
				}
				else if (currentCell.getMainElem() == ENTITIES.GHOST)
				{
					nGhost++;
					m_ghosts.add(m_map.addTile(Sprites.getGhostSPrite(nGhost), x, y, ENTITIES.GHOST));
				}
				else if (currentCell.getMainElem() == ENTITIES.BLOC)
					m_map.addTile(Sprites.simple_wall, x, y, ENTITIES.BLOC);
				else if (currentCell.getMainElem() == ENTITIES.KILLING_POWER ||
						 currentCell.getMainElem() == ENTITIES.SLOW_GHOST_POWER ||
						 currentCell.getMainElem() == ENTITIES.SPEED_POWER)
					m_map.addTile(Sprites.super_fruit, x, y, currentCell.getMainElem());
				if (x < CONFIG_X * (cells[0].length - 1))
					x += CONFIG_X;
				else
				{
					x = 0;
					y += CONFIG_Y;
				}
			}
		}
	}
	
	public ArrayList <Entity> getEntities()
	{
		return entities;
	}
}
