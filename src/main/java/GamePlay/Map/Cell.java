package GamePlay.Map;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    // peut y avoir plusieurs elements même s'il y a collision direct
    private List<PacMap.ENTITIES> elems;
    private final Position position;

    public List<PacMap.ENTITIES> getElems() {
        return elems;
    }

    public Cell(PacMap.ENTITIES basicFirst, Position position) {
        this.elems = new ArrayList<>();
        this.elems.add(basicFirst);

        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void removeElem(PacMap.ENTITIES elem) {
        this.elems.remove(elem);
    }

    public boolean find(PacMap.ENTITIES elem) {
        return this.elems.contains(elem);
    }

    public void addElems(PacMap.ENTITIES elem) {
        this.elems.add(elem);
    }

    @Override
    public String toString() {
        return String.valueOf(elems.get(0));
    }

    /* // FONCTION DE FALLOU
    public PacMap.ENTITIES getMainElem() {
        PacMap.ENTITIES mainOne = null;
        int priority = 0;
        for (PacMap.ENTITIES entity : elems) {
            if (entity == PacMap.ENTITIES.EMPTY && 1 > priority){
                mainOne = PacMap.ENTITIES.EMPTY;
                priority = 1;
            }
            if (entity == PacMap.ENTITIES.FRUTE && 2 > priority){
                mainOne = PacMap.ENTITIES.FRUTE;
                priority = 2;
            }
            if (entity == PacMap.ENTITIES.BLOC && 3 > priority){
                mainOne = PacMap.ENTITIES.BLOC;
                priority = 3;
            }
            if (entity == PacMap.ENTITIES.POWER && 4 > priority){
                mainOne = PacMap.ENTITIES.POWER;
                priority = 4;
            }
            if (entity == PacMap.ENTITIES.PACMAN && 5 > priority){
                mainOne = PacMap.ENTITIES.PACMAN;
                priority = 5;
            }
            if (entity == PacMap.ENTITIES.GHOST && 6 > priority){
                mainOne = PacMap.ENTITIES.GHOST;
                break;
            }
        }
        return mainOne;
    }
*/

    /*
        FONCTION DE MEHDI
        UNE CELLULE PEUT COMPORTER PLUSIEURS ENTITÉS
        IL PEUT AUSSI Y AVOIR UN FRUIT ET L'ELEMENT VIDE
        LE BUT EST DE RETROUVER L’ÉLÉMENT LE PLUS INTÉRESSANT A L'INSTANT T
        ORDRES DE PRIORITES = EMPTY < FRUIT < POWER < BLOC < PACMAN < GHOST
     */
    public PacMap.ENTITIES getMainElem() {
        boolean bloc, pacman, ghost, frute, speedPower, killPower, slowGhostPower;
        bloc = false;
        pacman = false;
        ghost = false;
        frute = false;
        speedPower = false;
        killPower = false;
        slowGhostPower = false;

        for (PacMap.ENTITIES elem : elems) {
            if (elem == PacMap.ENTITIES.GHOST)
                ghost = true;
            if (elem == PacMap.ENTITIES.PACMAN)
                pacman = true;
            if (elem == PacMap.ENTITIES.BLOC)
                bloc = true;
            if (elem == PacMap.ENTITIES.FRUTE)
                frute = true;
            if (elem == PacMap.ENTITIES.SLOW_GHOST_POWER)
                slowGhostPower = true;
            if (elem == PacMap.ENTITIES.KILLING_POWER)
                killPower = true;
            if (elem == PacMap.ENTITIES.SPEED_POWER)
                speedPower = true;
        }

        if (ghost) return PacMap.ENTITIES.GHOST;
        if (pacman) return PacMap.ENTITIES.PACMAN;
        if (bloc) return PacMap.ENTITIES.BLOC;
        if (speedPower) return PacMap.ENTITIES.SPEED_POWER;
        if (slowGhostPower) return PacMap.ENTITIES.SLOW_GHOST_POWER;
        if (killPower) return PacMap.ENTITIES.KILLING_POWER;
        if (frute) return PacMap.ENTITIES.FRUTE;
        return PacMap.ENTITIES.EMPTY;
    }

    public static void main(String args[]) {
        Cell cell = new Cell(PacMap.ENTITIES.BLOC, new Position(0, 0));
        cell.addElems(PacMap.ENTITIES.EMPTY);
    }
}