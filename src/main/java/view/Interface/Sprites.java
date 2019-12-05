package view.Interface;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

				  statebarBackground, select_arrow,
				  
				  letter_A, letter_B, letter_C, letter_D, letter_E, letter_F, letter_G,
				  letter_H, letter_I, letter_J, letter_K , letter_L, letter_M, letter_N,
				  letter_O, letter_P, letter_Q, letter_R, letter_S, letter_T, letter_U,
				  letter_V, letter_W, letter_X, letter_Y, letter_Z, letter_underscore;


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
		
		letter_A = createImage("lettre_A.png");
		letter_B = createImage("lettre_B.png");
		letter_C = createImage("lettre_C.png");
		letter_D = createImage("lettre_D.png");
		letter_E = createImage("lettre_E.png");
		letter_F = createImage("lettre_F.png");
		letter_G = createImage("lettre_G.png");
		letter_H = createImage("lettre_H.png");
		letter_I = createImage("lettre_I.png");
		letter_J = createImage("lettre_J.png");
		letter_K = createImage("lettre_K.png");
		letter_L = createImage("lettre_L.png");
		letter_M = createImage("lettre_M.png");
		letter_N = createImage("lettre_N.png");
		letter_O = createImage("lettre_O.png");
		letter_P = createImage("lettre_P.png");
	    letter_Q = createImage("lettre_Q.png");
	    letter_R = createImage("lettre_R.png");
	    letter_S = createImage("lettre_S.png");
	    letter_T = createImage("lettre_T.png");
	    letter_U = createImage("lettre_U.png");
	    letter_V = createImage("lettre_V.png");
	    letter_W = createImage("lettre_W.png");
	    letter_X = createImage("lettre_X.png");
	    letter_Y = createImage("lettre_Y.png");
	    letter_Z = createImage("lettre_Z.png");
	    letter_underscore = createImage("lettre_underscore.png");
	    
	    select_arrow = createImage("menu_select.png");
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

	public static Image getLetterSprite(char c) {
		switch (c)
		{
			case 'a':
			case 'A':
				return letter_A;
			case 'b':
			case 'B':
				return letter_B;
			case 'c':
			case 'C':
				return letter_C;
			case 'd':
			case 'D':
				return letter_D;
			case 'e':
			case 'E':
				return letter_E;
			case 'f':
			case 'F':
				return letter_F;
			case 'g':
			case 'G':
				return letter_G;
			case 'h':
			case 'H':
				return letter_H;
			case 'i':
			case 'I':
				return letter_I;
			case 'j':
			case 'J':
				return letter_J;
			case 'k':
			case 'K':
				return letter_K;
			case 'l':
			case 'L':
				return letter_L;
			case 'm':
			case 'M':
				return letter_M;
			case 'n':
			case 'N':
				return letter_N;
			case 'o':
			case 'O':
				return letter_O;
			case 'p':
			case 'P':
				return letter_P;
			case 'q':
			case 'Q':
				return letter_Q;
			case 'r':
			case 'R':
				return letter_R;
			case 's':
			case 'S':
				return letter_S;
			case 't':
			case 'T':
				return letter_T;
			case 'u':
			case 'U':
				return letter_U;
			case 'v':
			case 'V':
				return letter_V;
			case 'w':
			case 'W':
				return letter_W;
			case 'x':
			case 'X':
				return letter_X;
			case 'y':
			case 'Y':
				return letter_Y;
			case 'z':
			case 'Z':
				return letter_Z;
			case ' ':
				return empty;
			default :
				return letter_underscore;
		}
	}

	public static Image getNumSprite(int initialNumberLife) {
		switch (initialNumberLife)
		{
			case 0:
				return score_zero;
			case 1:
				return score_one;
			case 2:
				return score_two;
			case 3:
				return score_three;
			case 4:
				return score_four;
			case 5:
				return score_five;
			case 6:
				return score_six;
			case 7:
				return score_seven;
			case 8:
				return score_eight;
			case 9:
				return score_nine;
			default :
				return null;
		}
	}
}
