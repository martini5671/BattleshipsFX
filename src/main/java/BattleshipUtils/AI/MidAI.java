
package BattleshipUtils.AI;

import BattleshipUtils.Battlefield;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MidAI {
    private ArrayList<Point> PlusCellsHits = new ArrayList<>();
    private ArrayList<Point> PlusCells = new ArrayList<>();
    private ArrayList<Point> possiblePointCoordinates = new ArrayList<>();
    private ArrayList<Integer> feedback = new ArrayList<>();
    private ArrayList<Point> shotsHistory = new ArrayList<>();

    public MidAI() {
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

    private Point getRandomShot(ArrayList<Point> arrayList)
    {
        Random random = new Random();
        // get checker board pattern coordinates else try different
        List<Point> checkers = arrayList.stream().filter(point -> (point.x + point.y)% 2 == 0).toList();
        if(!checkers.isEmpty())
        {
            int random_index = random.nextInt(checkers.size());
            Point random_checkers_point = checkers.get(random_index);
            archivePoint(random_checkers_point);
            return random_checkers_point;
        }
        else
        {
            int random_index = random.nextInt(arrayList.size());
            Point random_point = arrayList.get(random_index);
            archivePoint(random_point);
            return random_point;
        }
    }
    private void archivePoint(Point point)
    {
        possiblePointCoordinates.remove(point);
        shotsHistory.add(point);
    }
    private void setPlusCells(ArrayList<Point> points)
    {
        for (Point point: points)
        {
            archivePoint(point);
            PlusCells.add(point);
        }
    }
    public void getFeedback(int isHit)
    {
        feedback.add(isHit);
    }
    public Point getShot()
    {
        if (shotsHistory.isEmpty() && feedback.isEmpty()) {
            return getRandomShot(possiblePointCoordinates);
        } else if (feedback.getLast() == 0 && PlusCells.isEmpty()){
            return getRandomShot(possiblePointCoordinates);
        } else if (feedback.getLast() == 1 && PlusCells.isEmpty()) {
            // start hunter mode
            setPlusCellsHits(shotsHistory.getLast());
            setPlusCells(shotsHistory.getLast());
            return getRandomShot(getPlusCells());
        } else if (feedback.getLast() == 1 && !PlusCells.isEmpty()) {
            // determine ship orientation
        }
        return new Point(0,0);
    }

    private void setPlusCells(Point point) {
        ArrayList<Point> points = new ArrayList<>();
        Point point_up = new Point(point.x - 1, point.y);
        Point point_down = new Point(point.x + 1, point.y);
        Point point_left = new Point(point.x, point.y - 1);
        Point point_right = new Point(point.x, point.y + 1);
        points.add(point_up);
        points.add(point_down);
        points.add(point_left);
        points.add(point_right);
        // discard those which are outside the borders
        java.util.List<Point> filtered_list_points = points.stream()
                .filter(p -> p.x >= 1 && p.x <= 10)
                .filter(p -> p.y >= 1 && p.y <= 10)
                .collect(Collectors.toList());
        ArrayList<Point> adjacent_cells = (ArrayList<Point>) filtered_list_points;
        // filter adjacent cells and get intersection with the available coordinates
        ArrayList<Point> intersection = getIntersectionWithAvailableCoordinates(adjacent_cells);
        // get that orginal point to the first index
        intersection.addFirst(point);
        // reserve space for adjacent cells
        setPlusCells(intersection);
    }
    private ArrayList<Point> getIntersectionWithAvailableCoordinates(ArrayList<Point> list)
    {
        java.util.List<Point> intersect = list.stream()
                .filter(possiblePointCoordinates::contains)
                .collect(Collectors.toList());
        return (ArrayList<Point>) intersect;
    }

public static void main(String[] args) {
    MidAI midAI = new MidAI();
    Battlefield battlefield = new Battlefield(false);
    battlefield.displayFullBattlefield();
    for (int i = 0; i < 80; i++) {
        //Point randomPoint = midAI.getRandomShot();
        //battlefield.receiveHit(randomPoint.x, randomPoint.y);
    }
    battlefield.displayFullBattlefield();
    // ok działa

}

    public ArrayList<Point> getPlusCells() {
        if(PlusCells.size() == 1)
        {
            PlusCells.clear();
            return PlusCells;
        }
        else
        {
            return (ArrayList<Point>) PlusCells.subList(1, PlusCells.size());
        }
    }

    public ArrayList<Point> getPlusCellsHits() {
        return PlusCellsHits;
    }

    public void setPlusCellsHits(Point plusCellsHit) {
        PlusCellsHits.add(plusCellsHit);
    }
}
