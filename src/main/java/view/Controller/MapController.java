package view.Controller;
import java.util.ArrayList;

import GamePlay.Entities.Entity;
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
		// METTRE LE CODE DES DEUX MOVE EN UN
		// NE PLUS CALCULER CELUI QUI BOUGE C LE ROLE DE PHYSICAL MOTOR
		// FAIRE JUSTE UNE TRANSLATION
		// JUSTE BOUGER LIMAGE DE LA POSITION DEPART A LA POSITION ARRIVEE
		ImageView imageToMove = null;
		if (e.getType() == ENTITIES.PACMAN)
			imageToMove = m_pacman;
		else if (e.getType() == ENTITIES.GHOST)
			imageToMove = m_ghosts.get(0);
		TranslateTransition translateTransition = 
				new TranslateTransition(Duration.millis(speed), imageToMove);
		if (y == 1)
		{
	        translateTransition.setByX(MapController.CONFIG_X);
	        m_pacman.setImage(Sprites.pacman_right2);
		}
		else if (y == -1)
		{
	        translateTransition.setByX(- MapController.CONFIG_X);
	        m_pacman.setImage(Sprites.pacman_left2);
		}
		else if (x == 1)
		{
	        translateTransition.setByY(MapController.CONFIG_Y);
	        m_pacman.setImage(Sprites.pacman_down2);
		}
		else if (x == -1)
		{
	        translateTransition.setByY(- MapController.CONFIG_Y);
	        m_pacman.setImage(Sprites.pacman_up2);
		}
		else
			System.err.println("Error in MapController.movePacman()");
        translateTransition.setAutoReverse(false);
        translateTransition.play();
        return true;
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
		int x = 0;
		int y = 0;
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
