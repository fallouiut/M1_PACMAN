package view.Interface;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cell  {

    public enum ENTITIES {BLOC, PACMAN, GHOST, FRUTE, POWER, EMPTY}
    private List <ENTITIES> m_elems = new ArrayList <ENTITIES> ();
    
    public ENTITIES getMainElem() {
    	boolean bloc, pacman, ghost, frute, power;
    	bloc = false;
    	pacman = false;
    	ghost = false;
    	frute = false;
    	power = false;  	
    	for (ENTITIES elem : m_elems)
    	{
    		if (elem == ENTITIES.GHOST)
    			ghost = true;
    		if (elem == ENTITIES.PACMAN)
    			pacman = true;
    		if (elem == ENTITIES.BLOC)
    			bloc = true;
    		if (elem == ENTITIES.FRUTE)
    			frute = true;
    		if (elem == ENTITIES.POWER)
    			power = true;	
    	}
    	
    	if (ghost) return ENTITIES.GHOST;
    	if (pacman) return ENTITIES.PACMAN;
    	if (bloc) return ENTITIES.BLOC;
    	if (power) return ENTITIES.POWER;
    	if (frute) return ENTITIES.FRUTE;
    	return ENTITIES.EMPTY;
    	
    }
    
    public void addEntities(ENTITIES ent)
    {
    	m_elems.add(ent);
    }

}