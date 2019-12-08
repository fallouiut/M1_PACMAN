package GamePlay;

import java.io.BufferedReader;
import java.io.FileReader;

public class Level {

    private static final String LEVELS_PATH = "files/levels/level-";

    private int levelNumber;
    private int ghostSpeed;
    private int ghostReductionPower;
    private int pacmanSpeed;
    private int pacmanLives;
    private int timeForPower;
    private int pacmanPowerSpeed;

    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public boolean get() {
        String path = LEVELS_PATH + levelNumber + ".txt";

        try {
            BufferedReader bfr = new BufferedReader(new FileReader(path));
            String line;

            while ((line = bfr.readLine()) != null) {
                String[] lineTab = line.split(";");
                determine(lineTab[0], lineTab[1]);
            }
            bfr.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void determine(String attribute, String value) throws Exception {
        int intValue = Integer.valueOf(value);
        switch (attribute) {
            case "pacmanPowerSpeed":
                this.pacmanPowerSpeed = intValue;
                break;
            case "ghostSpeed":
                this.ghostSpeed = intValue;
                break;
            case "pacmanSpeed":
                this.pacmanSpeed = intValue;
                break;
            case "pacmanLives":
                this.pacmanLives = intValue;
                break;
            case "timeForPower":
                this.timeForPower = intValue;
                break;
			case "ghostReductionPower":
				this.ghostReductionPower = intValue;
				break;
            default:
                throw new Exception("Level.determine: Unrecognize attribute found int file 'level-" + levelNumber + ".txt'");
        }
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    /* TODO a utiliser */
    public int getGhostSpeed() {
        return ghostSpeed;
    }

    public int getPacmanSpeed() {
        return pacmanSpeed;
    }

    public int getPacmanLives() {
        return pacmanLives;
    }

    public int getTimeForPower() {
        return timeForPower;
    }

	public int getGhostReductionPower() {
		return ghostReductionPower;
	}

	public int getPacmanPowerSpeed() {
        return pacmanPowerSpeed;
    }

	@Override
	public String toString() {
		return "Level{" +
				"levelNumber=" + levelNumber +
				", ghostSpeed=" + ghostSpeed +
				", ghostReductionPower=" + ghostReductionPower +
				", pacmanSpeed=" + pacmanSpeed +
				", pacmanLives=" + pacmanLives +
				", timeForPower=" + timeForPower +
				", pacmanPowerSpeed=" + pacmanPowerSpeed +
				'}';
	}

	public static void main(String[] args) {
		Level level = new Level(1);

		if(level.get()) {
			System.out.println("LEVEL " + level.getLevelNumber() + " trouvé");
			System.out.println(level.toString());
		} else {
			System.out.println("PLus de niveau");
		}
	}
}