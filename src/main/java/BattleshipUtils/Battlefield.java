package BattleshipUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Battlefield {

    private char[][] Battlefield = new char[12][12];
    private ArrayList<Ship> ShipsArray = new ArrayList<>();

    public Battlefield(boolean isEnemyBattlefield) {
        if (!isEnemyBattlefield)
        {
            this.Battlefield = generateEmptyBattlefield();
        }
        else
        {
            this.Battlefield = generateEmptyBattlefield();
            addRandomShips();
        }

    }

    private char[][] generateEmptyBattlefield() {
        // Declaration of 2D array
        char[][] battlefield = new char[12][12];

        int char_ascii = 65;
        int number_ascii = 48;

        for (int i = 0; i < battlefield.length; i++) {
            for (int j = 0; j < battlefield.length; j++) {
                if (j == 0 && i > 0 && i != 11) {   // pionowo jest wstawiany alfabet
                    battlefield[i][j] = (char) char_ascii;
                    battlefield[i][j + 11] = (char) char_ascii;
                    char_ascii++;
                }
                if (i == 0 && j > 0 && j != 11) {   //poziomo wstawiamy numery
                    battlefield[i][j] = (char) number_ascii;
                    battlefield[i + 11][j] = (char) number_ascii;
                    number_ascii++;
                }
                if ((i > 0 && i < 11) && (j > 0 && j < 11)) {
                    battlefield[i][j] = '~';
                }
            }
        }
        battlefield[0][0] = '+'; // Top left corner
        battlefield[0][11] = '+'; // Top right corner
        battlefield[11][0] = '+'; // Bottom left corner
        battlefield[11][11] = '+'; // Bottom right corner

        return battlefield;
    }
    public void displayFullBattlefield() {
        System.out.println(" ");
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                System.out.print(this.Battlefield[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(" ");
    }
    public void addShipToBattlefield(int shipLength, ArrayList<Integer> rows, ArrayList<Integer> columns) {
        // add one to each coordinate
        this.ShipsArray.add(new Ship(shipLength, rows, columns));
        //adjust board
        for (int i = 0; i < rows.size(); i++) {
            Battlefield[rows.get(i)][columns.get(i)] = 'O';
        }
    }
    public char[][] getBattlefield() {
        return Battlefield;
    }

    private void addRandomShips() {

        this.ShipsArray.add(new Ship(5));
        this.ShipsArray.add(new Ship(4));
        this.ShipsArray.add(new Ship(3));
        this.ShipsArray.add(new Ship(3));
        this.ShipsArray.add(new Ship(2));

        for (Ship ship : ShipsArray) {
            boolean repeate = true;
            while (repeate) {
                // count loop
                // starting point + orientation
                Point random_starting_point = getRandomPointOnBoard(); // ok
                char random_orientation = getRandomOrientation(); //ok

                // other points:
                ArrayList<Point> other_points = getOtherPoints(random_starting_point, random_orientation, ship.getLength());

                // connect all points together:
                ArrayList<Point> full_points = connectPoints(random_starting_point, other_points);

                // translate to int array lists in hashmap
                HashMap<String, ArrayList<Integer>> coordinates_hashmap = translatePointsArrayToHashMap(full_points);

                // validate ship placement
                boolean isValid = Validators.validateShipPlacementForEnemy(coordinates_hashmap.get("rows"), coordinates_hashmap.get("columns"), Battlefield);
                if (isValid) {
                    // adjust the board
                    for (int j = 0; j < coordinates_hashmap.get("columns").size(); j++) {
                        Battlefield[coordinates_hashmap.get("rows").get(j)][coordinates_hashmap.get("columns").get(j)] = 'O';
                    }
                    // adjust the ships array
                    ship.addShipCoordinates(full_points);

                    // condition to break out of the loop
                    repeate = false;
                }
            }
        }
    }
    private Point getRandomPointOnBoard() {
        // x >= 1 and x <= 11 i to samo z y: y >= 1 i y <= 11
        Random random = new Random();
        int random_row = random.nextInt(11) + 1;
        int random_column = random.nextInt(11) + 1;
        return new Point(random_row, random_column);
    }

    private char getRandomOrientation() {
        Random rand = new Random();
        char[] orientation = {'v', 'h'};
        int random_index = rand.nextInt(2);
        return orientation[random_index];
    }

    private ArrayList<Point> getOtherPoints(Point starting_point, char orientation, int ship_length) {
        ArrayList<Point> additional_points = new ArrayList<>();
        int row_starting_point = starting_point.x;
        int column_starting_point = starting_point.y;
        if (orientation == 'v') {
            // get other coordinates vertical: same column different rows
            for (int i = 1; i < ship_length; i++) {
                Point additional_point = new Point(row_starting_point + i, column_starting_point);
                additional_points.add(additional_point);
            }
        } else {
            for (int i = 1; i < ship_length; i++) {
                Point additional_point = new Point(row_starting_point, column_starting_point + i);
                additional_points.add(additional_point);
            }
        }
        return additional_points;
    }

    private ArrayList<Point> connectPoints(Point starting_point, ArrayList<Point> additional_points) {
        ArrayList<Point> connected_point_list = new ArrayList<>();
        connected_point_list.add(starting_point);
        connected_point_list.addAll(additional_points);
        return connected_point_list;
    }
    private HashMap<String, ArrayList<Integer>> translatePointsArrayToHashMap(ArrayList<Point> points) {
        HashMap<String, ArrayList<Integer>> resultMap = new HashMap<>();
        ArrayList<Integer> rows = new ArrayList<>();
        ArrayList<Integer> columns = new ArrayList<>();

        for (Point point : points) {
            rows.add(point.x);
            columns.add(point.y);
        }

        resultMap.put("rows", rows);
        resultMap.put("columns", columns);

        return resultMap;
    }
    public static Point translateBoardCoordinatesToPointCoordinates(char row_index, char column_index) {
        return new Point((int) row_index - 64, (int) column_index - 47);

    }
    public void displayShipsNamesAndTheirCoordinates() {
        for (Ship ship : this.ShipsArray) {
            ship.printShipName();
            ship.printShipCoordinates();
        }
    }
    public int receiveHit(int row, int column) {
        for (Ship ship : ShipsArray)
        {
            // skip ship if dead
            if (ship.getHealth() != 0) {
                int feedback = ship.takeHit(row, column);
                if (feedback == 1)
                {
                    Battlefield[row][column] = 'X';
                    return 1;
                }
            }
        }
        Battlefield[row][column] = 'M';
        return 0;
    }



    public static void main(String[] args) {
        BattleshipUtils.Battlefield battlefield = new Battlefield(true);
        battlefield.displayFullBattlefield();
        battlefield.displayShipsNamesAndTheirCoordinates();
        for (int i = 1; i < 11; i = i +1) {
            for (int j = 1; j < 11; j = j+1) {
                System.out.println("row: "+ i + " column: " + j);
                int feedback = battlefield.receiveHit(i,j);
                System.out.println(feedback);
            }
            System.out.println(battlefield.getDestroyedShipsCoordinates());
        }
        battlefield.displayShipsNamesAndTheirCoordinates();
        battlefield.displayFullBattlefield();
    }

    public ArrayList<Ship> getShipsArray() {
        return ShipsArray;
    }

    public HashMap<String,ArrayList<Integer>> getDestroyedShipsCoordinates()
    {
        HashMap<String,ArrayList<Integer>> dead_coordinates = new HashMap<>();
        ArrayList<Integer> rows = new ArrayList<>();
        ArrayList<Integer> columns = new ArrayList<>();

        for (Ship ship: ShipsArray)
        {
            if (ship.getHealth() == 0)
            {
                rows.addAll(ship.getRowCoordinates());
                columns.addAll(ship.getColumnCoordinates());
            }
        }
        dead_coordinates.put("rows", rows);
        dead_coordinates.put("columns", columns);
        return dead_coordinates;
    }
}
