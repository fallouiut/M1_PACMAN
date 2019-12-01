package view.Interface;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sounds {

	private final static String MUSIC_PATH = "files/music/main_theme.mp3";
	private final static String DEATH_PATH = "files/music/death.wav";
	private final static String FRUIT_PATH = "files/music/fruit.wav";
	private final static String BONUS_PATH = "files/music/death.wav";
	private final static String GAME_OVER_PATH = "files/music/game_over.mp3";
	private final static String VICTORY_PATH = "files/music/victory.mp3";
	private final static String LIFE_LOST_PATH = "files/music/life_lost.mp3";
	private final static String GHOST_PATH = "files/music/ghost.mp3";
	private static MediaPlayer theme, death, fruit, victory, lifeLost, ghost, bonus, gameOver;
	
	public Sounds()
	{  
        theme = createMediaPlayer(MUSIC_PATH);
        theme.getOnRepeat();
        theme.setAutoPlay(true);
        death = createMediaPlayer(DEATH_PATH);
        fruit = createMediaPlayer(FRUIT_PATH);
        bonus = createMediaPlayer(BONUS_PATH);
        gameOver = createMediaPlayer(GAME_OVER_PATH);
        victory = createMediaPlayer(VICTORY_PATH);
        lifeLost = createMediaPlayer(LIFE_LOST_PATH);
        ghost = createMediaPlayer(GHOST_PATH);
	}
	
	public static void restartTheme()
	{
		theme.stop();
		theme.play();
	}
	
	public static void death()
	{
        death.play();   
	}

	
	public static void fruit()
	{
        fruit.play();   
	}
	
	public static void bonus()
	{
        bonus.play();   
	}
	
	public static void gameOver()
	{
       gameOver.play();   
	}
	
	public static void ghost()
	{
        ghost.play();   
	}
	
	public static void victory()
	{
       victory.play();   
	}
	
	public static void lifeLost()
	{
        lifeLost.play();   
	}
	
	private MediaPlayer createMediaPlayer(String pathMedia)
	{
		return new MediaPlayer(new Media(new File(pathMedia).toURI().toString()));
	}
	
}
