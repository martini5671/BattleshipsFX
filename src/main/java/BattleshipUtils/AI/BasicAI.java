package BattleshipUtils.AI;

import BattleshipUtils.Battlefield;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class BasicAI {
    private ArrayList<Point> possiblePointCoordinates;

    // store history of shots
    public BasicAI()
    {
        this.possiblePointCoordinates = setPossiblePointCoordinates();
    }
    private ArrayList<Point> setPossiblePointCoordinates()
    {
        ArrayList<Point> pointArrayList = new ArrayList<>();
        for (int i = 1; i < 11; i++)
        {
            for (int j = 1; j < 11; j++)
            {
                Point point = new Point(i,j);
                pointArrayList.add(point);
            }
        }
        return pointArrayList;
    }

    public Point getRandomShot()
    {
        Random random = new Random();
        int random_index = random.nextInt(possiblePointCoordinates.size());
        Point random_point = possiblePointCoordinates.get(random_index);
        removePointFromCoordinatesList(random_point);
        return random_point;
    }
    public void removePointFromCoordinatesList(Point point)
    {
        possiblePointCoordinates.remove(point);
    }
}
