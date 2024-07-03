package BattleshipUtils;

import java.awt.*;
import java.util.ArrayList;

import java.util.ArrayList;
import java.util.Arrays;

public class Ship {
    private final int length;
    private int health;
    private String name;
    private ArrayList<Integer> rowCoordinates = new ArrayList<>();
    private ArrayList<Integer> columnCoordinates = new ArrayList<>();

    private final String[][] ships_names = {
            {"Carrier", "5"},
            {"Battleship", "4"},
            {"Cruiser/Submarine", "3"},
            {"Destroyer", "2"}
    };

    public Ship(int length) {
        try {
            for (String[] shipsName : ships_names) {
                if (shipsName[1].equals(Integer.toString(length))) {
                    this.name = shipsName[0];
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("IndexOutOfBoundsException caught");
        }
        this.length = length;
        this.health = length;
    }

    public Ship(int length, ArrayList<Integer> rowCoordinates, ArrayList<Integer> columnCoordinates) {
        try {
            for (String[] shipsName : ships_names) {
                if (shipsName[1].equals(Integer.toString(length))) {
                    this.name = shipsName[0];
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("IndexOutOfBoundsException caught");
        }
        this.length = length;
        this.health = length;
        if (this.length != rowCoordinates.size() || this.length != columnCoordinates.size()) {
            throw new IllegalArgumentException("Invalid argument. Ship's length does not match the size of the array with coordinates.");
        } else {
            addShipCoordinates(rowCoordinates, columnCoordinates);
        }

    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public void addShipCoordinates(int row, int column) {
        rowCoordinates.add(row);
        columnCoordinates.add(column);
    }

    public void addShipCoordinates(ArrayList<Integer> rowCoordinatesList, ArrayList<Integer> columnCoordinatesList) {
        rowCoordinates.addAll(rowCoordinatesList);
        columnCoordinates.addAll(columnCoordinatesList);
    }

    public void addShipCoordinates(ArrayList<Point> coordinatesList) {
        for (Point point : coordinatesList) {
            rowCoordinates.add(point.x);
            columnCoordinates.add(point.y);
        }
    }

    public int takeHit(int row, int column) {
        for (int i = 0; i < rowCoordinates.size(); i++) {
            int row_coordinate = rowCoordinates.get(i);
            int column_coordinate = columnCoordinates.get(i);
            if (row_coordinate == row && column_coordinate == column) {
                // health
                health --;
                return 1;
            }
        }
        return 0;
    }

    public void printShipCoordinates() {
        for (int i = 0; i < rowCoordinates.size(); i++) {
            System.out.println("(" + rowCoordinates.get(i) + ", " + columnCoordinates.get(i) + ")");
        }
    }

    public void printShipName() {
        System.out.println(name);
    }

    public void clearShipCoordinates() {
        this.rowCoordinates.clear();
        this.columnCoordinates.clear();
    }

    public ArrayList<Integer> getRowCoordinates() {
        return rowCoordinates;
    }

    public ArrayList<Integer> getColumnCoordinates() {
        return columnCoordinates;
    }

    public int getHealth() {
        return health;
    }
}