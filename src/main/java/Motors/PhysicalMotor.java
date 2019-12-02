package Motors;

import GamePlay.Entities.Entity;
import GamePlay.Map.PacMap;
import GamePlay.Map.Position;
import view.Controller.MapController;

import java.util.ArrayList;
import java.util.List;

public class PhysicalMotor {

    private MapController window;
    private GameMotor gameMotor;

    // TODO: IMPORTANT
    // J'AURAI BESOIN QUE TU RAJOUTE UNE LISTE DE TOUS LES ELEMENTS FRUIT PACMAN
    // QUI PEUT ETRE AU LOADING DE (MAP -> WINDOW) GENRE JUSTE UNE MATRICE D'ENTITIES
    // JE PENSE QUON VA ENLEVER TON CODE DE CHARGEMENT DE LA MAP ET JUSTE LAISSER PACMAP
    // LE FAIRE PUIS LE PASSER EN PARAMETRE DE TA FONCTION QUI BUILD()
    // SI TU PEUX MODIFIER CA
    // DE FAIRE UNE FONCTION DE DETECTION DE COLLISION EN PARCOURANT LA MATRICE D'ENTITIES
    // QUI VERIFIE QUI EST A CETTE POSITION
    // MODIFIER UN PEU MAPCONTROLLER POUR PRENDRE LA POSITION EN PARAMETRE DE MOVE() ET NON UN STRING
    // DE MODIFIER LE CHARGEMENT DE LA MAP POUR QU'ELLE PRENNE UN PaCMAP EN PARAMETRE
    // si tu lance l'appli et que tu cliques sur une fleche tu vas voir tout le cheminement de gameplay a ici

    List<Entity> entities = new ArrayList<>();

    public PhysicalMotor(MapController window) {
        this.window = window;
    }

    public void setGameMotor(GameMotor gameMotor) {
        this.gameMotor = gameMotor;
    }

    public void translation(Entity entity, Position newOne) {
        System.out.println("PhysicalMotor.translation()");
        // TODO: NUMBER_1_A_FAIRE: une fonction
        // window.movePacman();
        // TODO: calculer le nv moubement en fonction de currentPosition et newPosition
    }

    public void checkCollision() {
        // chercher une collision
    }

    public void createWindows(PacMap pacMap) {
        //TODO: charger l'interface
    }

    public boolean create(PacMap stateMap) {
        // TODO: mapCOntroller buildMap avec cette map d'etat en parametre
        return true;
    }
}
