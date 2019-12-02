package view.Interface;

import java.io.File;
import javafx.scene.image.Image;

public class Sprites {
	
	final static String SPRITES_PATH = "files/images/";
	public static Image simple_wall, upper_left_corner, upper_right_corner,
				  bottom_left_corner, bottom_right_corner,

				  left_wall, right_wall, bottom_wall, upper_wall,

				  upper_left_angle, upper_right_angle,
				  bottom_left_angle, bottom_right_angle,

				  upper_left_turn, upper_right_turn,
				  bottom_left_turn, bottom_right_turn,

				 left_edge, right_edge, upper_edge, bottom_edge,

				  empty, empty_fruit, super_fruit,

				  pacman_left1, pacman_right1, pacman_up1, pacman_down1,
				  pacman_left2, pacman_right2, pacman_up2, pacman_down2,

				  ghost1, ghost2, ghost3, ghost4,

				  score_zero, score_one, score_two, score_three, score_four,
				  score_five, score_six, score_seven, score_eight, score_nine,

				  statebarBackground;


	static {
		simple_wall = createImage("mur_simple.png");

		upper_left_corner = createImage("coin_haut_gauche.png");
		upper_right_corner = createImage("coin_haut_droit.png");
		bottom_left_corner = createImage("coin_bas_gauche.png");
		bottom_right_corner = createImage("coin_bas_droit.png");

		left_wall = createImage("bord_gauche.png");
		bottom_wall = createImage("bord_bas.png");
		right_wall = createImage("bord_droit.png");
		upper_wall = createImage("bord_haut.png");

		upper_left_angle = createImage("angle_haut_gauche.png");
		upper_right_angle = createImage("angle_haut_droit.png");
		bottom_left_angle = createImage("angle_bas_gauche.png");
		bottom_right_angle = createImage("angle_bas_droit.png");

		left_edge = createImage("bout_gauche.png");
		right_edge = createImage("bout_droit.png");
		bottom_edge = createImage("bout_bas.png");
		upper_edge = createImage("bout_haut.png");

		//upper_left_turn = createImage("virage_haut_gauche.png");
		upper_right_turn = createImage("virage_haut_droit.png");
		//bottom_left_turn = createImage("virage_bas_gauche.png");
		bottom_right_turn = createImage("virage_bas_droit.png");

		empty_fruit = createImage("vide_fruit.png");
		super_fruit = createImage("fruit_pouvoir.png");
		empty = createImage("vide.png");

		pacman_left1 = createImage("pacman_gauche_1.png");
		pacman_left2 = createImage("pacman_gauche_2.png");
		pacman_right1 = createImage("pacman_droit_1.png");
		pacman_right2 = createImage("pacman_droit_2.png");
		pacman_up1 = createImage("pacman_haut_1.png");
		pacman_up2 = createImage("pacman_haut_2.png");
		pacman_down1 = createImage("pacman_bas_1.png");
		pacman_down2 = createImage("pacman_bas_2.png");

		ghost1 = createImage("ghost1.png");
		ghost2 = createImage("ghost2.png");
		ghost3 = createImage("ghost3.png");
		ghost4 = createImage("ghost4.png");

		score_zero = createImage("score_zero.png");
		score_one = createImage("score_one.png");
		score_two = createImage("score_two.png");
		score_three = createImage("score_three.png");
		score_four = createImage("score_four.png");
		score_five = createImage("score_five.png");
		score_six = createImage("score_six.png");
		score_seven = createImage("score_seven.png");
		score_eight = createImage("score_eight.png");
		score_nine = createImage("score_nine.png");

		statebarBackground = createImage("statebar_background.png");
	}

	public static boolean isPacman(Image image)
	{
		if (image == pacman_down1 || image == pacman_down2)
			return true;
		if (image == pacman_up1 || image == pacman_up2)
			return true;
		if (image == pacman_left1 || image == pacman_left2)
			return true;
		if (image == pacman_right1 || image == pacman_right2)
			return true;
		return false;
		
	}
	
	public static Image createImage(String path)
	{
		return new Image(new File(SPRITES_PATH + path).toURI().toString());
	}

	public static Image getGhostSPrite(int numberGhost) {
		switch (numberGhost)
		{
			case 0:
			return ghost1;
			case 1:
			return ghost2;
			case 2:
			return ghost3;
			case 3:
			return ghost4;
			default:
			return ghost1;
		}
	}
	
	public static Image animatePacman(Image currentImage)
	{
		if (currentImage == Sprites.pacman_left1)
			return Sprites.pacman_left2;
		else if (currentImage == Sprites.pacman_left2)
			return Sprites.pacman_left1;
		else if (currentImage == Sprites.pacman_right1)
			return Sprites.pacman_right2;
		else if (currentImage == Sprites.pacman_right2)
			return Sprites.pacman_right1;
		else if (currentImage == Sprites.pacman_up1)
			return Sprites.pacman_up2;
		else if (currentImage == Sprites.pacman_up2)
			return Sprites.pacman_up1;
		else if (currentImage == Sprites.pacman_down1)
			return Sprites.pacman_down2;
		else if (currentImage == Sprites.pacman_down2)
			return Sprites.pacman_down1;
		else
			return Sprites.pacman_left2;
	}
	
	public static Image orientatePacman(Image currentImage, String direction)
	{
		boolean one;
		if (currentImage == Sprites.pacman_down1 ||
			currentImage == Sprites.pacman_left1 ||
			currentImage == Sprites.pacman_up1   ||
			currentImage == Sprites.pacman_right1)
			one = true;
		else
			one = false;
		switch (direction)
		{
				case "RIGHT":
					if (one)
						return Sprites.pacman_right1;
					else return Sprites.pacman_right2;
				case "LEFT":
					if (one)
						return Sprites.pacman_left1;
					else return Sprites.pacman_left2;
				case "UP":
					if (one)
						return Sprites.pacman_up1;
					else return Sprites.pacman_up2;
				case "DOWN":
					if (one)
						return Sprites.pacman_down1;
					else return Sprites.pacman_down2;
		}
		return null;
	}
}
