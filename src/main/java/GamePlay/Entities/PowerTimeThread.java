package GamePlay.Entities;

import GamePlay.GamePlay;
import GamePlay.Map.PacMap;

import java.util.concurrent.TimeUnit;

public class PowerTimeThread extends Thread {

    private int timeSec;
    private GamePlay gamePlay;
    private PacMap.ENTITIES power;

    public PowerTimeThread(GamePlay gamePlay, PacMap.ENTITIES power, int time) {
        this.timeSec = time;
        this.gamePlay = gamePlay;
        this.power = power;
    }

    public void run() {
        try {
            TimeUnit.SECONDS.sleep(timeSec);
            this.gamePlay.stopPower(power);
        } catch (Exception e) {
            e.printStackTrace();
            this.gamePlay.stopPower(this.power);
        }
    }
}
