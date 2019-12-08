package view.Interface;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import view.Controller.MapController;

public class Menu
{
	GridPane root;
	private boolean play = false;
	private boolean quit = false;
	private boolean highscore = false;
	private MenuArrowAnimation animation;
	
	private ImageView arrow;
	private ImageView playArrow;
	private ImageView quitArrow;
	private ImageView highscoreArrow;
	
	private String currentMenuChoice;
	
	public Menu()
	{
		currentMenuChoice = "PLAY";
		root = new GridPane();
		ArrayList <ImageView> play = createPlay();
		ArrayList <ImageView> highscore = createHighscore();
		ArrayList <ImageView> quit = createQuit();
		final int ROW_SIZE = 20;
		for (int i = 0; i < 7; i++)
		{
			for (int j = 0; j < ROW_SIZE; j++)
			{
				if (i%2 == 0)
					root.add(createMenuImage(Sprites.statebarBackground), j, i);
				else if (i == 1)
						root.add(play.get(j), j, i);
				else if (i == 3)
						root.add(highscore.get(j), j, i);
				else if (i == 5)
						root.add(quit.get(j), j, i);
			}
		}
		animation = new MenuArrowAnimation();
		animation.start();
	}

	public String selectMenuChoice(String direction, String currentPos) 
	{
		arrow.setImage(Sprites.empty);
		switch (direction)
		{
			case "DOWN":
				if (currentPos.compareTo("PLAY") == 0)
				{
					arrow = highscoreArrow;
					return "HIGHSCORE";
				}
				else if (currentPos.compareTo("HIGHSCORE") == 0)
				{
					arrow = quitArrow;
					return "QUIT";
				}
				else if (currentPos.compareTo("QUIT") == 0)
				{
					arrow = playArrow;
					return "PLAY";
				}
			case "UP":
				if (currentPos.compareTo("PLAY") == 0)
				{
					arrow = quitArrow;
					return "QUIT";
				}
				else if (currentPos.compareTo("HIGHSCORE") == 0)
				{
					arrow = playArrow;
					return "PLAY";
				}
				else if (currentPos.compareTo("QUIT") == 0)
				{
					arrow = highscoreArrow;
					return "HIGHSCORE";
				}
			case "ENTER" :
				validateMenuChoice(currentPos);
				return "ENTER";
			default :
				return "PLAY";
		}
	}

	public class MenuArrowAnimation extends AnimationTimer {

		boolean blink = true;
		long m_lastUpdate = System.nanoTime();
		public MenuArrowAnimation()
		{
			arrow = playArrow;
		}
	    @Override
	    public void handle(long arg0) {
	        long elapsedNanoSeconds = System.nanoTime() - m_lastUpdate;
	        double elapsedSeconds = elapsedNanoSeconds / 250_000_000.0;
	        if (elapsedSeconds < 3)
	            return;
	        m_lastUpdate = System.nanoTime();
	        if (blink)
	        	arrow.setImage(Sprites.empty);
	        else
	        	arrow.setImage(Sprites.select_arrow);;
	        blink = !blink;
	    }
	}

	private void validateMenuChoice(String currentPos)
	{	
		switch (currentPos)
		{
			case "PLAY":
			{
				play = true;
				animation.stop();
				break;
			}
			case "HIGHSCORE":
				highscore = true;
				break;
			case "QUIT" :
				System.exit(0);
		}
	}

	private ArrayList <ImageView> createQuit() {
		ArrayList <ImageView> quitImages = new ArrayList <ImageView> ();
		for (int i = 0; i < 7; i ++)
			quitImages.add(createMenuImage(Sprites.statebarBackground));
		quitArrow = createMenuImage(Sprites.empty);
		quitImages.add(quitArrow);
		quitImages.add(createMenuImage(Sprites.letter_Q));
		quitImages.add(createMenuImage(Sprites.letter_U));
		quitImages.add(createMenuImage(Sprites.letter_I));
		quitImages.add(createMenuImage(Sprites.letter_T));
		for (int i = 12; i < 21; i ++)
			quitImages.add(createMenuImage(Sprites.statebarBackground));	
		return quitImages;
	}

	private ArrayList <ImageView> createHighscore() {
		ArrayList <ImageView> highscoreImages = new ArrayList <ImageView> ();
		for (int i = 0; i < 5; i ++)
			highscoreImages.add(createMenuImage(Sprites.empty));
		highscoreArrow = createMenuImage(Sprites.empty);
		highscoreImages.add(highscoreArrow);
		highscoreImages.add(createMenuImage(Sprites.letter_H));
		highscoreImages.add(createMenuImage(Sprites.letter_I));
		highscoreImages.add(createMenuImage(Sprites.letter_G));
		highscoreImages.add(createMenuImage(Sprites.letter_H));
		highscoreImages.add(createMenuImage(Sprites.letter_S));
		highscoreImages.add(createMenuImage(Sprites.letter_C));
		highscoreImages.add(createMenuImage(Sprites.letter_O));
		highscoreImages.add(createMenuImage(Sprites.letter_R));
		highscoreImages.add(createMenuImage(Sprites.letter_E));
		for (int i = 14; i < 20; i ++)
			highscoreImages.add(createMenuImage(Sprites.empty));	
		return highscoreImages;
	}

	private ArrayList <ImageView> createPlay() {
		ArrayList <ImageView> playImages = new ArrayList <ImageView> ();
		for (int i = 0; i < 7; i ++)
		{
			playImages.add(createMenuImage(Sprites.empty));
		}
		playArrow = createMenuImage(Sprites.select_arrow);
		playImages.add(playArrow);
		playImages.add(createMenuImage(Sprites.letter_P));
		playImages.add(createMenuImage(Sprites.letter_L));
		playImages.add(createMenuImage(Sprites.letter_A));
		playImages.add(createMenuImage(Sprites.letter_Y));
		for (int i = 12; i < 21; i ++)
			playImages.add(createMenuImage(Sprites.empty));	
		return playImages;
	}
	
	private ImageView createMenuImage(Image image)
	{
		ImageView imageView = new ImageView();
		imageView.setImage(image);
        imageView.setFitHeight(MapController.CONFIG_X * 2);
        imageView.setFitWidth(MapController.CONFIG_Y * 2);
        imageView.setPreserveRatio(true);
        return imageView;
	}
	
	public boolean getPlay()
	{
		return play;
	}
	
	public GridPane getRoot()
	{
		return root;
	}

	public void setCurrentPos(String currentPos) {
		currentMenuChoice = currentPos;
	}

	public String getCurrentPos() {
		return currentMenuChoice;
	}
}
