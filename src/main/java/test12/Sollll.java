package test12;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Sollll {
    private static final Scanner sc = new Scanner(System.in);
    private static   int minSoFar = Integer.MAX_VALUE;

    private static int readInt() {
        return sc.nextInt();
    }

    public static void main(String[] args) {
        int numTest = sc.nextInt();
        IntStream.range(0, numTest).forEach(value -> {
            int numSpots = readInt();
            var gates = IntStream.range(0, 3).mapToObj(value1 -> new Gate(readInt() - 1, readInt())).collect(Collectors.toList());
            List<List<Gate>> gatePermutated = new ArrayList<>();
            assignPermutation(gatePermutated,gates,0,1,2);
            assignPermutation(gatePermutated,gates,0,2,1);
            assignPermutation(gatePermutated,gates,1,0,2);
            assignPermutation(gatePermutated,gates,1,2,0);
            assignPermutation(gatePermutated,gates,2,1,0);
            assignPermutation(gatePermutated,gates,2,0,1);
            minSoFar = Integer.MAX_VALUE;
            gatePermutated.forEach(gates1 -> {
                var spots = IntStream.range(0,numSpots).mapToObj(value1 -> new Spot(value1)).collect(Collectors.toList());
                solve(spots,gates1,0,0);
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
                found = true;
                candidates.add(left);
            }
            if(right < spots.size() && left != right && !spots.get(right).occupied){
                found = true;
                candidates.add(right);
            }
            if(found){
                break;
            }
        }

        for (Integer candidate : candidates) {
            g.fishermenAssigned++;
            spots.get(candidate).occupied = true;
            solve(spots,gates1,distance+Math.abs(g.index-candidate)+1,gateIndex);
            spots.get(candidate).occupied = false;
            g.fishermenAssigned--;
        }
    }

    private static void assignPermutation(List<List<Gate>> gatePermutated, List<Gate> gates, int i, int i1, int i2) {
        List<Gate> result = new ArrayList<>();
        result.add(new Gate(gates.get(i).index,gates.get(i).fishermenCapacity));
        result.add(new Gate(gates.get(i1).index,gates.get(i1).fishermenCapacity));
        result.add(new Gate(gates.get(i2).index,gates.get(i2).fishermenCapacity));
        gatePermutated.add(result);
    }
}

class Gate {
    final int fishermenCapacity;
    int fishermenAssigned;

    public Gate(int index, int fishermenCapacity) {
        this.index = index;
        this.fishermenCapacity = fishermenCapacity;
    }

    int index;
}

class Spot {
    final int index;
    boolean occupied;

    Spot(int index) {
        this.index = index;
    }
}
