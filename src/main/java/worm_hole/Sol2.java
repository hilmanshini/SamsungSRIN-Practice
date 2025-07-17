package worm_hole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 1
 3
 0 0 100 100
 1 2 120 120 16
 2 5 120 100 21
 6 8 150 180 16
 */
import static utils.Reader.readInt;

public class Sol2 {
    public static void main(String[] args) {
        int testCount = readInt();
        for (int i = 0; i < testCount; i++) {
            int numHoles = readInt();
            starting = readPoint();
            ends = readPoint();
            var z = generateHoles(numHoles);
            holes = z.holes;
            holeMap = z.holeMap;

            //
            List<Integer> indexes = new ArrayList<>();
            List<Integer> travel = new ArrayList<>();
            for (int i1 = 0; i1 < numHoles * 2; i1++) {
                indexes.add(i1);
            }
            travel(indexes, travel);
            System.out.println(travel);
             //

//            generatePermutations(numHoles * 2, holes);
            System.out.println();
            List<Integer> travelled = new ArrayList<>();
            travel(holes, travelled);
            System.out.println(optim);
        }
    }

    static Map<Integer, Hole> holes;
    static Point starting, ends;
    static  Map<Integer,GroupHole> holeMap;




    private static void travel(List<Integer> items, List<Integer> travel) {
        for (int i = 0; i < items.size(); i++) {
            int current = items.get(i);
            if (travel.contains(current)) {
                continue;
            }
            items.remove((Object) current);
            travel(items, travel);
            items.add(current);
        }
    }

    private static Point readPoint() {
        return new Point(readInt(), readInt());
    }

    private static GroupHoles generateHoles(int size) {
        Map<Integer, Hole> result = new HashMap<>();
        GroupHoles holes = new GroupHoles(result);
        for (int i = 0; i < size * 2; i += 2) {
            Point starting = new Point(readInt(), readInt());
            Point ending = new Point(readInt(), readInt());
            int cost = readInt();
            result.put(i, new Hole(starting, ending, cost));
            result.put(i + 1, new Hole(ending, starting, cost));
            GroupHole groupHole = new GroupHole(i,i+1);
            holes.holeMap.put(i,groupHole);
            holes.holeMap.put(i+1,groupHole);
        }
        return holes;
    }

    private static void travel(Map<Integer, Hole> data, List<Integer> travelled) {
        calculateDistance(travelled);
        for (int i = 0; i < data.size(); i++) {
            GroupHole groupHole = holeMap.get(i);
            if (travelled.contains(groupHole.holeIndex) || travelled.contains(groupHole.reverseHoleIndex)) {
                continue;
            }
            travelled.add(i);
            travel(data, travelled);
            travelled.remove((Object) i);
        }
    }
    static int min = Integer.MAX_VALUE;
    static List<Hole> optim = new ArrayList<>();

    private static void calculateDistance(List<Integer> travelled){
        int distance = 0;
        Point lastPoint = starting;
        List<Hole> paths = new ArrayList<>();
        for (Integer i : travelled) {
            Hole hole = holes.get(i);
            paths.add(hole);
            distance += Math.abs(lastPoint.x-hole.starting.x)+Math.abs(lastPoint.y-hole.starting.y)+hole.cost;
            lastPoint = hole.ending;
        }
        distance += Math.abs(ends.x-lastPoint.x)+Math.abs(ends.y-lastPoint.y);
        if(distance < min){
            min = distance;
            optim = paths;
        }
        System.out.printf("%s  %d \n",travelled,distance);
    }

    private static void permutation(int i, List<Integer> data, List<List<Integer>> permList) {
        int current = data.get(i);
        data.remove(i);
        for (int i1 = 0; i1 < data.size(); i1++) {
            permutation(i1, data, permList);
        }
        data.add(current);
    }

    static class Point {
        int x;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int y;

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    static  class GroupHoles {
        Map<Integer,GroupHole> holeMap = new HashMap<>();
        Map<Integer, Hole> holes;

        public GroupHoles(Map<Integer, Hole> holes) {
            this.holes = holes;
        }
    }
    static  class GroupHole{
        int holeIndex;

        public GroupHole(int holeIndex, int reverseHoleIndex) {
            this.holeIndex = holeIndex;
            this.reverseHoleIndex = reverseHoleIndex;
        }

        int reverseHoleIndex;
    }

    static class Hole {
        Point starting;
        Point ending;

        public Hole(Point starting, Point ending, int cost) {
            this.starting = starting;
            this.ending = ending;
            this.cost = cost;
        }

        int cost;

        @Override
        public String toString() {
            return "Hole{" +
                    "starting=" + starting +
                    ", ending=" + ending +
                    ", cost=" + cost +
                    '}';
        }
    }
}


