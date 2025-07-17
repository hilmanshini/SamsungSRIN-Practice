package worm_hole;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static utils.Reader.readInt;

/***
 1
 3
 0 0 100 100
 1 2 120 120 16
 2 5 120 100 21
 6 8 150 180 16

 */
public class Sol {
    public static void main(String[] args) {
        int testCase = readInt();
        int numHoles = readInt();
        Point starting = new Point(readInt(), readInt());
        Point destination = new Point(readInt(), readInt());
        List<Teleport> teleportList = new ArrayList<>();
//        List<TeleportStep> teleportStepList = new ArrayList<>();
        for (int holeIndex = 0; holeIndex < numHoles*2; holeIndex += 2) {
            Teleport teleportCurrent = new Teleport(holeIndex, new Point(readInt(), readInt()), new Point(readInt(), readInt()), readInt(),holeIndex/2);
            Teleport teleportReversed = new Teleport(holeIndex+1, teleportCurrent.destination, teleportCurrent.start, teleportCurrent.cost,holeIndex/2);
            teleportList.add(teleportCurrent);
            teleportList.add(teleportReversed);
//            TeleportStep teleportStep = new TeleportStep(teleportCurrent,teleportReversed);
//            teleportStepList.add(teleportStep);
        }


        List<TeleportStep> travels = new ArrayList<>();
        travel( destination, teleportList, travels,starting,new Point(0,0));
        System.out.println(min);
    }
    private static boolean isTravelled(List<TeleportStep> step, int index){
        for (TeleportStep teleportStep : step) {
            if(teleportStep.index == index){
                return  true;
            }
        }
        return  false;
    }
    private static  int min = Integer.MAX_VALUE;

    private static void travel( Point finalDestination, List<Teleport> teleportStepList, List<TeleportStep> travel,Point starting,Point current) {
//        int distance = starting.getDistance(destination);
//        System.out.println(travel.size());
//        System.out.println(travel);
        if(teleportStepList.size()/2==travel.size()){
            int distance = 0;
            TeleportStep last = null;
            for (TeleportStep teleportStep : travel) {
                last = teleportStep;
                distance += teleportStep.getCost();
            }
            distance += last.teleport.destination.getDistance(finalDestination); // Add final distance to destination
            if(distance < min){
                min = distance;
            }
            System.out.println("ends "+travel+" => "+distance+" minNow= "+min);
        }
        for (int i = 0; i < teleportStepList.size(); i++) {
            Teleport teleportStep = teleportStepList.get(i);
            if (isTravelled(travel,teleportStep.originalIndex)) {
                continue;
            }
//            System.out.println(teleportStep.originalIndex+" = travel from "+teleportStep.start+" to "+teleportStep.destination);
            travel.add(new TeleportStep(teleportStep.originalIndex,teleportStep,current));
            travel(finalDestination, teleportStepList, travel,starting,teleportStep.destination);
            travel.remove(travel.size()-1);
        }
    }
}

class Point {
    int x;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int y;

    int getDistance(Point point) {
        return Math.abs(point.x - x) + Math.abs(point.y - y);
    }

    @Override
    public String toString() {
        return
                "x=" + x +
                ", y=" + y;
    }
}

class TeleportStep {
    int index;
    Teleport teleport;
    Point from;

    public TeleportStep(int index, Teleport teleport, Point from) {
        this.index = index;
        this.teleport = teleport;
        this.from = from;
    }



    @Override
    public String toString() {
        return
                "teleport=" + teleport.start +" => "+teleport.destination+" distance = "+from.getDistance(teleport.start);
    }

    public int getCost(){
        return  from.getDistance(teleport.start)+ teleport.cost;
    }
}

class Teleport {
    int index, cost;
    Point start;
    boolean reversed;
    int originalIndex;

    public Teleport(int index, Point start, Point destination, int cost,int originalIndex) {
        this.index = index;
        this.cost = cost;
        this.start = start;
        this.destination = destination;

        this.originalIndex =originalIndex;
    }

    Point destination;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Teleport)) return false;
        Teleport teleportStep = (Teleport) o;
        return index == teleportStep.index;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(index);
    }

    @Override
    public String toString() {
        return "Teleport{" +
                "index=" + index +
                "from=" + start.x + "," + start.y + " to=" + destination.x + "," + destination.y +
                '}';
    }
}