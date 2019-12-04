package view.Interface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import view.Controller.MapController;

public class Highscore {
	private final String HIGHSCORE_PATH = "files/highscore.txt";
	private GridPane pane;
	private final int LINE_HIGHSCORE_MAX = 10;
	ArrayList <ArrayList<ImageView>> highscores;
	
	public Highscore()
	{		
		pane = new GridPane();
		buildHighscores();
		for (int i = 0; i < LINE_HIGHSCORE_MAX + 2; i++)
		{
			for (int j = 0; j < 24; j++)
			{
				if (i == 0)
					pane.add(createHighscoreImage(Sprites.statebarBackground), j, i);
				else if (i < highscores.size())
					pane.add(highscores.get(i).get(j), j, i);
				else
					pane.add(createHighscoreImage(Sprites.statebarBackground), j, i);
			}
		}
	}
	
	private void buildHighscores() 
	{
		highscores = new ArrayList <ArrayList <ImageView>> ();
		int i = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(HIGHSCORE_PATH));
		    String highscoreLine = br.readLine();
		    while (highscoreLine != null || i < 10) 
		    {
		    	ArrayList <ImageView> highscoreLineImages = buildHighscoreLine(highscoreLine);
		    	highscores.add(highscoreLineImages);
		    	highscoreLine = br.readLine();
		    	i++;
		    }
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ArrayList<ImageView> buildHighscoreLine(String highscoreLine) {
		ArrayList <ImageView> highscoreLineImages = new ArrayList <ImageView> ();
		final int SCORE_LENGTH = 6;
		ImageView image;
		for (int i = 0; i < highscoreLine.length(); i++)
		{
			if (i < 4 || i > 20)
				image = createHighscoreImage(Sprites.empty);
			else if (i > highscoreLine.length() - SCORE_LENGTH)
			{
				int digitScore = Integer.valueOf("" + highscoreLine.charAt(i - 4));
				image = createHighscoreImage(Sprites.getNumSprite(digitScore));
			}
			else
				image = createHighscoreImage(Sprites.getLetterSprite(highscoreLine.charAt(i - 4)));
			highscoreLineImages.add(image);				
		}
		return highscoreLineImages;
	}

	public GridPane getRoot() 
	{
		return pane;
	}
	
	private ImageView createHighscoreImage(Image image)
	{
		ImageView imageView = new ImageView();
		imageView.setImage(image);
        imageView.setFitHeight(MapController.CONFIG_X * 2);
        imageView.setFitWidth(MapController.CONFIG_Y * 2);
        imageView.setPreserveRatio(true);
        return imageView;
	}

}
