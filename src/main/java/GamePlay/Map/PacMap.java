package GamePlay.Map;

import GamePlay.Entities.Entity;
import GamePlay.GamePlay;

import java.io.BufferedReader;
import java.io.FileReader;

public class PacMap {

    public final static String TEXT_BLOC = "#";
    public final static String TEXT_PACMAN = "@";
    public final static String TEXT_GHOST = "&";
    public final static String TEXT_FRUTE = "-";
    public final static String TEXT_SPEED_POWER = "2";
    public final static String TEXT_SLOW_GHOST_POWER = "3";
    public final static String TEXT_KILLING_POWER = "4";
    public final static String TEXT_EMPTY = " ";

    public void setPath(String filename) {
        this.file = filename;
    }

    public enum ENTITIES {BLOC, PACMAN, GHOST, FRUTE, SPEED_POWER, SLOW_GHOST_POWER, KILLING_POWER, EMPTY}

    public boolean isPosition(Position position) {
        return (position.getY() > 0 && position.getX() > 0) && (position.getX() < labyrinth.length && position.getY() < labyrinth[0].length);
    }

    public void removeEntity(Entity entity) {
        Cell cell = labyrinth[entity.getPosition().getX()][entity.getPosition().getY()];
        cell.removeElem(entity.getType());
    }

    public void destroy() {
        this.labyrinth = null;
    }

    public void moveEntity(Entity entity, Position newOne) {
        Cell cell = labyrinth[newOne.getX()][newOne.getY()];
        cell.addElems(entity.getType());
    }

    private Cell[][] labyrinth;
    private String file;
    private GamePlay gamePlay;

    public void setGamePlay(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    public Cell[][] getLabyrinth() {
        return labyrinth;
    }

    public void load() throws Exception {
        if (file.isEmpty()) throw new Exception("PacMap.load() File is empty");


        try {
            BufferedReader bfr = new BufferedReader(new FileReader(this.file));

            String buffer;
            int lineSize = 0;
            int colSize = 0;

            // determiner taille i et j
            while ((buffer = bfr.readLine()) != null) {
                lineSize += 1;
                if (colSize == 0) {
                    colSize = buffer.length();
                }
            }

            bfr.close();
            this.labyrinth = new Cell[lineSize][colSize];
            bfr = new BufferedReader(new FileReader(this.file));

            // remplir map
            int i = 0;
            while (((buffer = bfr.readLine()) != null) && (i < labyrinth.length)) {

                for (int j = 0; j < buffer.length(); ++j) {
                    Position pos = new Position(i, j);
                    ENTITIES entity = PacMap.getByChar(String.valueOf(buffer.charAt(j)));
                    this.labyrinth[i][j] = new Cell(entity, pos);
                    labyrinth[i][j].addElems(ENTITIES.EMPTY);
                    this.gamePlay.addEntity(entity, pos);
                }
                i += 1;
            }

        } catch (Exception e) {
            System.out.println("ERREUR MAP");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean find(ENTITIES entity, Position position) {
        Entity entity1 = new Entity(entity, position) {
            @Override
            public ENTITIES getType() {
                return entity;
            }
        };
        return find(entity1, position);
    }

    public boolean find(Entity entity, Position position) {
        if (this.isPosition(position)) {
            Cell cell = labyrinth[position.getX()][position.getY()];
            for (ENTITIES entities : cell.getElems()) {
                if (entities == entity.getType()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ENTITIES getByChar(String elem) {
        switch (elem) {
            case TEXT_BLOC:
                return ENTITIES.BLOC;
            case TEXT_PACMAN:
                return ENTITIES.PACMAN;
            case TEXT_GHOST:
                return ENTITIES.GHOST;
            case TEXT_FRUTE:
                return ENTITIES.FRUTE;
            case TEXT_SLOW_GHOST_POWER:
                return ENTITIES.SLOW_GHOST_POWER;
            case TEXT_KILLING_POWER:
                return ENTITIES.KILLING_POWER;
            case TEXT_SPEED_POWER:
                return ENTITIES.SPEED_POWER;
            default:
                return ENTITIES.EMPTY;
        }
    }

    public static Position getNewPositionByOrientation(Position p, String orientation) {
        switch (orientation) {
            case "LEFT":
                return new Position(p.getX(), p.getY()-1);
            case "UP":
                return new Position(p.getX()-1, p.getY());
            case "DOWN":
                return new Position(p.getX()+1, p.getY());
            case "RIGHT":
                return new Position(p.getX(), p.getY()+1);
            default:
                return p;
        }
    }
/*
    public String printMap() {
        for (int i = 0; i < labyrinth.length; ++i) {
            for (int j = 0; j < labyrinth[0].length; ++j) {

                // afficgafe postition
                PacMap.ENTITIES entity = this.labyrinth[i][j].getMainElem();

                if (entity == ENTITIES.BLOC) {
                    System.out.print("\u001B[35m" + TEXT_BLOC + "\u001B[0m");
                }
                if (entity == ENTITIES.PACMAN) {
                    System.out.print("\u001B[36m" + TEXT_PACMAN + "\u001B[0m");
                }
                if (entity == ENTITIES.FRUTE) {
                    System.out.print("\u001B[33m" + TEXT_FRUTE + "\u001B[0m");
                }
                if (entity == ENTITIES.POWER) {
                    System.out.print("\u001B[34m" + TEXT_POWER + "\u001B[0m");
                }
                if (entity == ENTITIES.GHOST) {
                    System.out.print("\u001B[31m" + TEXT_GHOST + "\u001B[0m");
                }
                if (entity == ENTITIES.EMPTY) {
                    System.out.print("\u001B[31m" + TEXT_EMPTY + "\u001B[0m");
                }
            }
            System.out.println();
        }
        return "NULL";
    }*/
}
