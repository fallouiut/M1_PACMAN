package view.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import view.Interface.Cell;
import view.Interface.Map;
import view.Interface.Sprites;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import view.Interface.Cell.ENTITIES;

public class MapController {

	public final static int CONFIG_X = 25;
	public final static int CONFIG_Y = 25;
	public int m_sizeRow;
	public int m_nGhost = 0;
	
	private ImageView m_pacman;
	private  double m_pacmanX, m_pacmanY;
	private ArrayList <ImageView> m_ghosts = new ArrayList <ImageView> ();
	private Map m_map = new Map();
	
	private ArrayList <ArrayList <Cell>> m_cells;
	
	public MapController()
	{
		computeTxtMap();
		initializeMap();
	}
	
	private void initializeMap()
	{
		m_sizeRow = m_cells.get(0).size() - 1;
		int x = 0;
		int y = 0;
		Cell currentCell;
		for (int i = 0; i < m_cells.size(); i++)
		{
			for (int j = 0; j < m_cells.get(i).size(); j++)
			{
				currentCell = getCell(i, j);
				if (currentCell.getMainElem() == ENTITIES.EMPTY)
				{
					m_map.addTile(Sprites.empty, x, y, ENTITIES.EMPTY);
				}
				else if (currentCell.getMainElem() == ENTITIES.FRUTE)
				{
					m_map.addTile(Sprites.empty_fruit, x, y, ENTITIES.FRUTE);
					m_map.addTile(Sprites.empty, x, y, ENTITIES.EMPTY);
				}
				else if (currentCell.getMainElem() == ENTITIES.PACMAN)
				{
					m_pacman = m_map.addTile(Sprites.pacman_left2, x, y, ENTITIES.PACMAN);
					m_pacmanX = x;
					m_pacmanY = y;
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
				if (x < CONFIG_X * m_sizeRow)
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
	
	public void refresh()
	{
		m_map.refresh();
		m_cells.clear();
		m_ghosts.clear();
		m_nGhost = 0;
		computeTxtMap();
		initializeMap();
	}
	// To move once to the left, moveGhost(1, 0, nGhost);
	public void moveGhost(int x, int y, int nGhost)
	{
		TranslateTransition translateTransition = 
	    new TranslateTransition(Duration.millis(view.Controller.ApplicationController.SPEED), m_ghosts.get(nGhost));
		if (x > 0 && y == 0)
		{
	        translateTransition.setByX(MapController.CONFIG_X);
	        this.setPacmanX(1);
	        m_pacman.setImage(Sprites.pacman_right2);
		}
		if (x < 0 && y == 0)
		{
	        translateTransition.setByX(- MapController.CONFIG_X);
	        this.setPacmanX(- 1);
	        m_pacman.setImage(Sprites.pacman_left2);
		}
		else if (x == 0 && y < 0)
		{
	        translateTransition.setByY(- MapController.CONFIG_Y);
	        this.setPacmanY(- 1);
	        m_pacman.setImage(Sprites.pacman_up2);
		}
		else if (x == 0 && y > 0)
		{
	        translateTransition.setByY(MapController.CONFIG_Y);
	        this.setPacmanY(1);
	        m_pacman.setImage(Sprites.pacman_down2);
		}
        translateTransition.setAutoReverse(false);
        translateTransition.play();
	}

	public void movePacman(String character) 
	{
		TranslateTransition translateTransition = 
	    new TranslateTransition(Duration.millis(view.Controller.ApplicationController.SPEED), m_pacman);
		switch (character)
		{
			case "RIGHT" :
		        translateTransition.setByX(MapController.CONFIG_X);
		        this.setPacmanX(1);
		        m_pacman.setImage(Sprites.pacman_right2);
				break;
			case "DOWN" :
		        translateTransition.setByY(MapController.CONFIG_Y);
		        this.setPacmanY(1);
		        m_pacman.setImage(Sprites.pacman_down2);
				break;
			case "LEFT" :
		        translateTransition.setByX(- MapController.CONFIG_X);
		        this.setPacmanX(- 1);
		        m_pacman.setImage(Sprites.pacman_left2);
				break;
			case "UP" :
		        translateTransition.setByY(- MapController.CONFIG_Y);
		        this.setPacmanY(- 1);
		        m_pacman.setImage(Sprites.pacman_up2);
				break;
		}
        translateTransition.setAutoReverse(false);
        translateTransition.play();
	}
	
	private void computeTxtMap()
	{
		m_cells = new ArrayList <ArrayList <Cell>> ();
		try {
			BufferedReader br = new BufferedReader(new FileReader("files/maps/map1.txt"));
			String line;
			int row = 0;
			while ( (line = br.readLine()) != null)
			{
				Cell c;
				m_cells.add(new ArrayList <Cell> ());
				for (int i = 0; i < line.length(); i++)
				{
					c = new Cell();
					switch (line.charAt(i))
					{
						case '#' :
							c.addEntities(ENTITIES.BLOC);
							break;
						case '&' :
							c.addEntities(ENTITIES.GHOST);
							c.addEntities(ENTITIES.EMPTY);
							break;
						case '@' :
							c.addEntities(ENTITIES.PACMAN);
							c.addEntities(ENTITIES.EMPTY);
							break;
						case ' ' :
							c.addEntities(ENTITIES.EMPTY);
							break;
						case '-' :
							c.addEntities(ENTITIES.EMPTY);
							c.addEntities(ENTITIES.FRUTE);
							break;
					}
					m_cells.get(row).add(c);
				}
				row = row + 1;
			}
		br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	private void initializeBlock(int i, int j)
    {
    	if (cellPerimeter(i, j))
    	{
    		if (cellCorner(i, j))
    			return;
    		else if (cellWall(i, j))
    			return;
    	}
		else if (insideWall(i, j))
			return;
    } */
    
/*    private boolean insideWall(int i, int j) 
    {
    	Cell c, c1, c2;
		c = m_cells.get(i).get(j);
		c1 = m_cells.get(i).get(j - 1);
		c2 = m_cells.get(i).get(j + 1);
    	if (c1.getMainElem() == ENTITIES.BLOC && c2.getMainElem() == ENTITIES.BLOC) // Si bloc avant et bloc après, mur
    	{
			if (m_cells.get(i - 1).get(j).getMainElem() == ENTITIES.EMPTY)
				c.setImage(Sprites.upper_wall);
			else
				c.setImage(Sprites.bottom_wall);
			add(c.getImage(), j, i);
			return true;
    	}
    	else if (c1.getMainElem() == ENTITIES.BLOC && c2.getMainElem() != ENTITIES.BLOC) // Si bloc avant et rien après, "coin"
    	{
    		if (m_cells.get(i + 1).get(j).getMainElem() == ENTITIES.BLOC)
				c.setImage(Sprites.upper_right_angle);
			else if (m_cells.get(i - 1).get(j).getMainElem() == ENTITIES.BLOC)
				c.setImage(Sprites.bottom_right_angle);
			else if (m_cells.get(i - 1).get(j).getMainElem() != ENTITIES.BLOC &&
					 m_cells.get(i + 1).get(j).getMainElem() != ENTITIES.BLOC) 
				c.setImage(Sprites.left_edge);
			add(c.getImage(), j, i);
			return true;
    	}
    	else if (c1.getMainElem() != ENTITIES.BLOC && c2.getMainElem() == ENTITIES.BLOC) // Si rien d'abord et bloc après, "coin"
    	{
    		if (m_cells.get(i + 1).get(j).getMainElem() == ENTITIES.BLOC)
				c.setImage(Sprites.upper_left_angle);
			else if (m_cells.get(i - 1).get(j).getMainElem() == ENTITIES.BLOC)
				c.setImage(Sprites.bottom_left_angle);
			else if (m_cells.get(i - 1).get(j).getMainElem() != ENTITIES.BLOC &&
					 m_cells.get(i + 1).get(j).getMainElem() != ENTITIES.BLOC) 
				c.setImage(Sprites.right_edge);
			add(c.getImage(), j, i);
			return true;
    	}
		c1 = m_cells.get(i - 1).get(j);
		c2 = m_cells.get(i + 1).get(j);
    	if (c1.getMainElem() == ENTITIES.BLOC && c2.getMainElem() == ENTITIES.BLOC)
    	{
    		if (m_cells.get(i - 1).get(j - 1).getMainElem() == ENTITIES.BLOC)
    			c.setImage(Sprites.left_wall);
    		else if (m_cells.get(i - 1).get(j + 1).getMainElem() == ENTITIES.BLOC)
    			c.setImage(Sprites.right_wall);
    		else
    			c.setImage(Sprites.left_wall);
			add(c.getImage(), j, i);
			return true;		
    	}
    	else if (c1.getMainElem() == ENTITIES.BLOC && c2.getMainElem() != ENTITIES.BLOC)
    	{
			c.setImage(Sprites.bottom_edge);
			add(c.getImage(), j, i);
    	}
    	else if (c1.getMainElem() != ENTITIES.BLOC && c2.getMainElem() == ENTITIES.BLOC)
    	{
			c.setImage(Sprites.upper_edge);
			add(c.getImage(), j, i);
    	}
    	return false;
	} */

	/* private boolean cellPerimeter(int i, int j)
    {
		int row = m_cells.size() - 1;
    	int column = m_cells.get(0).size() - 1;
    	if (j == 0 || j == column)
    		return true;
    	if (i == 0 || i == row)
    		return true;
    	return false;
    }
    
    public boolean cellCorner(int i, int j)
    {	
    	Cell c;
    	int row = m_cells.size() - 1;
    	int column = m_cells.get(0).size() - 1;
    	if (i == 0 && j == 0)
    	{
			c = m_cells.get(i).get(j);
    		c.setImage(Sprites.upper_left_corner);
			add(c.getImage(), j, i);
    	}
    	else if (i == 0 && j == column)
    	{
			c = m_cells.get(i).get(j);
    		c.setImage(Sprites.upper_right_corner);
			add(c.getImage(), j, i);
    	}
    	else if (i == row && j == 0)
    	{
			c = m_cells.get(i).get(j);
    		c.setImage(Sprites.bottom_left_corner);
			add(c.getImage(), j, i);
    	}
    	else if (i == row && j == column)
    	{
			c = m_cells.get(i).get(j);
    		c.setImage(Sprites.bottom_right_corner);
			add(c.getImage(), j, i);
    	}
    	else return false;
    	return true;
    } */
    
    /* private boolean cellWall(int i, int j)
    {
    	Cell c, c1, c2;
    	int row = m_cells.size() - 1;
    	int column = m_cells.get(0).size() - 1;
    	if (i == 0)
    	{
			c = m_cells.get(i).get(j);
			c1 = m_cells.get(i).get(j - 1);
			c2 = m_cells.get(i).get(j + 1);
			if (c1.getMainElem() == ENTITIES.BLOC && c2.getMainElem() == ENTITIES.BLOC)
    		{
				c.setImage(Sprites.upper_wall);
				add(c.getImage(), j, i);
    		}
    	}
    	else if (i == row)
    	{
			c = m_cells.get(i).get(j);
			c1 = m_cells.get(i).get(j - 1);
			c2 = m_cells.get(i).get(j + 1);
			if (c1.getMainElem() == ENTITIES.BLOC && c2.getMainElem() == ENTITIES.BLOC)
    		{
	    		c.setImage(Sprites.bottom_wall);
				add(c.getImage(), j, i);
				
    		}
    	}
    	else if (j == 0)
    	{
			c = m_cells.get(i).get(j);
			c1 = m_cells.get(i - 1).get(j);
			c2 = m_cells.get(i + 1).get(j);
			if (c1.getMainElem() == ENTITIES.BLOC && c2.getMainElem() == ENTITIES.BLOC)
    		{
				c.setImage(Sprites.left_wall);
				add(c.getImage(), j, i);
    		}
			else if (c1.getMainElem() == ENTITIES.BLOC && c2.getMainElem() == ENTITIES.EMPTY)
			{
				c.setImage(Sprites.bottom_left_corner);
				add(c.getImage(), j, i);
			}
			else if (c1.getMainElem() == ENTITIES.EMPTY && c2.getMainElem() == ENTITIES.BLOC)
			{
				c.setImage(Sprites.upper_left_corner);
				add(c.getImage(), j, i);
			}
    	}
    	else if (j == column)
    	{
			c = m_cells.get(i).get(j);
			c1 = m_cells.get(i - 1).get(j);
			c2 = m_cells.get(i + 1).get(j);
			if (c1.getMainElem() == ENTITIES.BLOC && c2.getMainElem() == ENTITIES.BLOC)
    		{
				c.setImage(Sprites.right_wall);
				add(c.getImage(), j, i);
    		}
			else if (c1.getMainElem() == ENTITIES.BLOC && c2.getMainElem() == ENTITIES.EMPTY)
			{
				c.setImage(Sprites.bottom_right_corner);
				add(c.getImage(), j, i);
			}
			else if (c1.getMainElem() == ENTITIES.EMPTY && c2.getMainElem() == ENTITIES.BLOC)
			{
				c.setImage(Sprites.upper_right_corner);
				add(c.getImage(), j, i);
			}
    	}
    	else return false;
    	return true;
    } */
	
	// Recoit les instuctions clavier
	
	public double getPacmanX() 
	{
		return m_pacmanX;
	}

	public void setPacmanX(double m_pacmanX)
	{
		this.m_pacmanX += m_pacmanX;
	}

	public double getPacmanY() 
	{
		return m_pacmanY;
	}

	public void setPacmanY(double m_pacmanY) 
	{
		this.m_pacmanY += m_pacmanY;
	}

	private Cell getCell(int i, int j)
	{
		return m_cells.get(i).get(j);
	}
	
	public ImageView getPacman()
	{
		return m_pacman;
	}
	
	public Map getMap()
	{
		return m_map;
	}
}
