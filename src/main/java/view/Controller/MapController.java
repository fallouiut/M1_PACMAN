package view.Controller;
import java.util.ArrayList;

import GamePlay.Entities.Entity;
import GamePlay.Entities.Fruit;
import GamePlay.Entities.PacmanEntity;
import GamePlay.Map.Cell;
import GamePlay.Map.PacMap;
import GamePlay.Map.PacMap.ENTITIES;
import GamePlay.Map.Position;
import view.Interface.Map;
import view.Interface.Sprites;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class MapController {

	public final static int CONFIG_X = 25;
	public final static int CONFIG_Y = 25;
	ImageView m_pacman;
	ArrayList <ImageView> m_ghosts = new ArrayList <ImageView> ();
	private int m_speed;
	public int m_nGhost = 0;
	private Map m_map = new Map();
	
	private ArrayList <Entity> entities = new ArrayList <Entity> ();
	
	public MapController(int speed)
	{		
		m_speed = speed;
	}
	
	public void refresh(PacMap pacmap, int speed)
	{
		m_map.refresh();
		entities.clear();
		m_nGhost = 0;
		m_speed = speed;
		initializeMap(pacmap);
	}
	// To move once to the left, moveGhost(1, 0, nGhost);
	public void moveGhost(Position p, int nGhost)
	{
		TranslateTransition translateTransition = 
	    new TranslateTransition(Duration.millis(m_speed), m_ghosts.get(nGhost));
		int x = p.getX();
		int y = p.getY();
		if (x > 0 && y == 0)
	        translateTransition.setByX(MapController.CONFIG_X);
		if (x < 0 && y == 0)
	        translateTransition.setByX(- MapController.CONFIG_X);
		else if (x == 0 && y < 0)
	        translateTransition.setByY(- MapController.CONFIG_Y);
		else if (x == 0 && y > 0)
	        translateTransition.setByY(MapController.CONFIG_Y);
        translateTransition.setAutoReverse(false);
        translateTransition.play();
	}

	public void movePacman(Position newPosition) 
	{
		TranslateTransition translateTransition = 
	    new TranslateTransition(Duration.millis(m_speed), m_pacman);
		// TODO : remplacer m_pacman.getX et m_pacman.getY par la façon "normale" d'accéder
		// A la position de pacman, là j'utilise les coordonnées de l'image
		double pacmanX = m_pacman.getX();
		double pacmanY = m_pacman.getY();
		double newX = newPosition.getX();
		double newY = newPosition.getY();
		double difX = newX - pacmanX;
		double difY = newY - pacmanY;
		if (difX != 0 && difY != 0)
			System.err.println("Error in MapController.movePacman()");
		else if (difX == 1)
		{
	        translateTransition.setByX(MapController.CONFIG_X);
	        m_pacman.setImage(Sprites.pacman_right2);
		}
		else if (difX == -1)
		{
	        translateTransition.setByX(- MapController.CONFIG_X);
	        m_pacman.setImage(Sprites.pacman_left2);
		}
		else if (difY == 1)
		{
	        translateTransition.setByY(MapController.CONFIG_Y);
	        m_pacman.setImage(Sprites.pacman_down2);
		}
		else if (difY == -1)
		{
	        translateTransition.setByY(- MapController.CONFIG_Y);
	        m_pacman.setImage(Sprites.pacman_up2);
		}
		else
			System.err.println("Error in MapController.movePacman()");
        translateTransition.setAutoReverse(false);
        translateTransition.play();
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
		Cell[][] cells = pacmap.getLabyrinth();
		Cell currentCell;
		int x = 0;
		int y = 0;
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
					entities.add(new Fruit(new Position(x, y)));
					m_map.addTile(Sprites.empty_fruit, x, y, ENTITIES.FRUTE);
					m_map.addTile(Sprites.empty, x, y, ENTITIES.EMPTY);
				}
				else if (currentCell.getMainElem() == ENTITIES.PACMAN)
				{
					entities.add(new PacmanEntity(new Position(x, y)));
					m_pacman = m_map.addTile(Sprites.pacman_left2, x, y, ENTITIES.PACMAN);
					m_map.addTile(Sprites.empty, x, y, ENTITIES.EMPTY);
				}
				else if (currentCell.getMainElem() == ENTITIES.GHOST)
				{
					m_nGhost += 1;
					m_map.addTile(Sprites.empty, x, y, ENTITIES.EMPTY);
					m_ghosts.add(m_map.addTile(Sprites.getGhostSPrite(m_nGhost), x, y, ENTITIES.GHOST));
				}
				else
					m_map.addTile(Sprites.simple_wall, x, y, ENTITIES.BLOC);
				if (x < CONFIG_X * cells[0].length)
					x += CONFIG_X;
				else
				{
					x = 0;
					y += CONFIG_Y;
				}
			}
		}
		m_map.build();
	}
	
	public ArrayList <Entity> getEntities()
	{
		return entities;
	}
}
