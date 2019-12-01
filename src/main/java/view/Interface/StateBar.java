package view.Interface;
import view.Controller.MapController;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class StateBar {
	ToolBar m_bar;
	
	ImageView background,
			  m_imageLife, m_imageNumberLife,
			  m_imageDigits1, m_imageDigits2, m_imageDigits3, m_imageDigits4, m_imageDigits5, m_imageDigits6;
	
	
	public StateBar(int initialNumberLife)
	{
		initializeToolBar(initialNumberLife);
		Image background = Sprites.statebarBackground;
		m_bar = new ToolBar(m_imageNumberLife, m_imageLife, new ImageView(background), new ImageView(background),
							m_imageDigits1, m_imageDigits2, m_imageDigits3, m_imageDigits4,
							m_imageDigits5, m_imageDigits6);
		
		m_bar.setBackground(new Background(new BackgroundImage(background,
							BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
							BackgroundPosition.DEFAULT,
							BackgroundSize.DEFAULT)));
	}
	
	public void initializeToolBar(int initialNumberLife)
	{
		m_imageLife = new ImageView(Sprites.pacman_left2);
		m_imageLife.setFitHeight(MapController.CONFIG_X); 
		m_imageLife.setFitWidth(MapController.CONFIG_Y);   
		switch (initialNumberLife)
		{
			case 1:
				m_imageNumberLife = new ImageView(Sprites.score_one);
				break;
			case 2:
				m_imageNumberLife = new ImageView(Sprites.score_two);
				break;
			case 3:
				m_imageNumberLife = new ImageView(Sprites.score_three);
				break;
			case 4:
				m_imageNumberLife = new ImageView(Sprites.score_four);
				break;
			case 5:
				m_imageNumberLife = new ImageView(Sprites.score_five);
				break;
			default :
				m_imageNumberLife = new ImageView(Sprites.score_three);
		}
		m_imageNumberLife.setFitHeight(MapController.CONFIG_X); 
		m_imageNumberLife.setFitWidth(MapController.CONFIG_Y);  
		
		m_imageDigits1 = new ImageView(Sprites.score_zero);
		m_imageDigits1.setFitHeight(MapController.CONFIG_X); 
		m_imageDigits1.setFitWidth(MapController.CONFIG_Y);  
		
		m_imageDigits2 = new ImageView(Sprites.score_zero);
		m_imageDigits2.setFitHeight(MapController.CONFIG_X); 
		m_imageDigits2.setFitWidth(MapController.CONFIG_Y); 
		
		m_imageDigits3 = new ImageView(Sprites.score_zero);
		m_imageDigits3.setFitHeight(MapController.CONFIG_X); 
		m_imageDigits3.setFitWidth(MapController.CONFIG_Y);
		
		m_imageDigits4 = new ImageView(Sprites.score_zero);
		m_imageDigits4.setFitHeight(MapController.CONFIG_X); 
		m_imageDigits4.setFitWidth(MapController.CONFIG_Y); 
		
		m_imageDigits5 = new ImageView(Sprites.score_zero);
		m_imageDigits5.setFitHeight(MapController.CONFIG_X); 
		m_imageDigits5.setFitWidth(MapController.CONFIG_Y);
		
		m_imageDigits6 = new ImageView(Sprites.score_zero);
		m_imageDigits6.setFitHeight(MapController.CONFIG_X); 
		m_imageDigits6.setFitWidth(MapController.CONFIG_Y);  
	}
	
	public void setLife(int life)
	{
		switch (life)
		{
			case 0:
				m_imageLife.setImage(Sprites.score_zero);
				break;
			case 1:
				m_imageLife.setImage(Sprites.score_one);
				break;
			case 2:
				m_imageLife.setImage(Sprites.score_two);
				break;
			case 3:
				m_imageLife.setImage(Sprites.score_three);
				break;
			case 4:
				m_imageLife.setImage(Sprites.score_four);
				break;
			case 5:
				m_imageLife.setImage(Sprites.score_five);
				break;
			default :
				m_imageLife.setImage(Sprites.score_three);
		}
	}

	public void setScore(int score)
	{
		final int SCORE_MAX_DIGITS = 5;
		if (score > 999_999)
			score = 999_999;
		else if (score < 0)
		{
			System.err.println("Error : negative score.");
			score = 0;
		}
		String scoreText = Integer.toString(score);
		Image image;
		for (int i = 0; i != SCORE_MAX_DIGITS; i++)
		{
			switch (scoreText.charAt(i))
			{
				case '0':
					image = Sprites.score_zero;
					break;
				case '1':
					image = Sprites.score_one;
					break;
				case '2':
					image = Sprites.score_two;
					break;
				case '3':
					image = Sprites.score_three;
					break;
				case '4':
					image = Sprites.score_four;
					break;
				case '5':
					image = Sprites.score_five;
					break;
				case '6':
					image = Sprites.score_six;
					break;
				case '7':
					image = Sprites.score_seven;
					break;
				case '8':
					image = Sprites.score_eight;
					break;
				case '9':
					image = Sprites.score_nine;
					break;
				default:
					image = Sprites.score_zero;
					break;
			}
			if (i == 0)
				m_imageDigits1.setImage(image);
			else if (i == 1)
				m_imageDigits2.setImage(image);
			else if (i == 2)
				m_imageDigits3.setImage(image);
			else if (i == 3)
				m_imageDigits4.setImage(image);
			else if (i == 4)
				m_imageDigits5.setImage(image);
			else if (i == 5)
				m_imageDigits6.setImage(image);
		}
	}
	
	public void refresh(int initialNumberOfLife)
	{
		initializeToolBar(initialNumberOfLife);
		Image background = Sprites.statebarBackground;
		m_bar = new ToolBar(m_imageNumberLife, m_imageLife, new ImageView(background), new ImageView(background),
							m_imageDigits1, m_imageDigits2, m_imageDigits3, m_imageDigits4,
							m_imageDigits5, m_imageDigits6);
		
		m_bar.setBackground(new Background(new BackgroundImage(background,
							BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
							BackgroundPosition.DEFAULT,
							BackgroundSize.DEFAULT)));
	}
	
	public ToolBar getBar()
	{
		return m_bar;
	}
}
