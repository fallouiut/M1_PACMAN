package Motors;

import GamePlay.Entities.Entity;
import GamePlay.GamePlay;
import GamePlay.Map.PacMap;
import GamePlay.Map.Position;
import view.Interface.Sounds;

public class GameMotor {

    private GamePlay gamePlay;

    // rajouter une liste des elements et leur position
    private PhysicalMotor physicalMotor;

    private Sounds sound;

    public GameMotor(PhysicalMotor physicalMotor, Sounds sound) {
        this.sound = sound;
        this.physicalMotor = physicalMotor;
    }

    public void launchParty(PacMap stateMap) {
        physicalMotor.create(stateMap);
        sound.restartTheme();
    }

    public void setGamePlay(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    public void gameIsReached() {
        // TODO: mapController popup indiquant que le jeu est fini
        // TODO: sound particulier pour fin de jeu s'il y en a
    }

    public void makeMove(Entity entity, Position newOne) {
        System.out.println("gameMotor.makeMove()");
        physicalMotor.translation(entity, newOne);
        // sound de translation s'il y en a un
    }
}
