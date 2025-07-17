package test9;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solutionn {
    private static final Scanner sc = new Scanner(System.in);
    private static int minSoFar = Integer.MAX_VALUE;

    private static int readInt() {
        return sc.nextInt();
    }


    public static void main(String[] args) {
        int numOfTest = readInt();
        IntStream.range(0, numOfTest).forEach(value -> {
            int numOfSpots = readInt();
            List<Gate> gates = IntStream.range(0, 3).mapToObj(value1 -> new Gate(
                    readInt() - 1, readInt()
            )).collect(Collectors.toList());
            List<List<Gate>> permutated = new ArrayList<>();
            assignPermutated(gates, permutated, 0, 1, 2);
            assignPermutated(gates, permutated, 0, 2, 1);
            assignPermutated(gates, permutated, 1, 0, 2);
            assignPermutated(gates, permutated, 1, 2, 0);
            assignPermutated(gates, permutated, 2, 1, 0);
            assignPermutated(gates, permutated, 2, 0, 1);
            minSoFar = Integer.MAX_VALUE;
            permutated.forEach(gates1 -> {
                var spots = IntStream.range(0, numOfSpots).mapToObj(value1 -> new Spot(
                        value1, false
                )).collect(Collectors.toList());
                solve(spots, gates1, 0, 0);
            });
            System.out.println(minSoFar);

        });
    }

    private static void solve(List<Spot> spots, List<Gate> gates1, int distance, int gateIndex) {
        if(distance > minSoFar)return;
        if(gateIndex == gates1.size()){
            minSoFar = Math.min(minSoFar,distance);
            return;
        }
        Gate g = gates1.get(gateIndex);
        if(g.fishermenAssigned == g.fishermenCapacity){
            solve(spots,gates1,distance,gateIndex+1);
            return;
        }
        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < spots.size(); i++) {
            int left = g.index-i;
            int right = g.index+i;
            boolean found = false;
            if(left >= 0 && !spots.get(left).occupied){
                candidates.add(left);
                found = true;
            }

            if(right <spots.size() && left != right&& !spots.get(right).occupied){
                candidates.add(right);
                found = true;
            }
            if(found){
                break;
            }
        }
        for (Integer candidate : candidates) {
            g.fishermenAssigned++;
            spots.get(candidate).occupied = true;
            solve(spots,gates1,distance+Math.abs(g.index-candidate)+1,gateIndex);
            g.fishermenAssigned--;
            spots.get(candidate).occupied = false;
        }
    }

    private static void assignPermutated(List<Gate> gates, List<List<Gate>> permutated, int i, int i1, int i2) {
        List<Gate> result = new ArrayList<>();
        result.add(new Gate(gates.get(i).index, gates.get(i).fishermenCapacity));
        result.add(new Gate(gates.get(i1).index, gates.get(i1).fishermenCapacity));
        result.add(new Gate(gates.get(i2).index, gates.get(i2).fishermenCapacity));
        permutated.add(result);
    }
}


class Gate {
    int index;
    int fishermenCapacity;
    int fishermenAssigned;

    public Gate(int index, int fishermenCapacity) {
        this.index = index;
        this.fishermenCapacity = fishermenCapacity;
    }
}


class Spot {
    int index;

    public Spot(int index, boolean occupied) {
        this.index = index;
        this.occupied = occupied;
    }

    boolean occupied;

}
