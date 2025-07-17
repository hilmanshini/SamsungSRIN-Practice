package test6;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    private static Scanner scanner = new Scanner(System.in);
    private static int minSoFar = Integer.MAX_VALUE;

    private static int readInt() {
        return scanner.nextInt();
    }

    public static void main(String[] args) {
        int numOfTest = readInt();
        IntStream.range(0, numOfTest).forEach(value -> {
            int numOfSpots = readInt();
            List<Gate> gates = IntStream.range(0, 3).mapToObj(value1 -> new Gate(
                    readInt()-1,
                    readInt()
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
                List<FishingSpot> spots = IntStream.range(0,numOfSpots).mapToObj(FishingSpot::new).collect(Collectors.toList());
                solve(spots,gates1,0,0);
            });;
            System.out.println(minSoFar);
        });
    }

    private static void solve(List<FishingSpot> spots, List<Gate> gates1, int distance, int gateIndex) {
        if(distance >= minSoFar)return;
        if(gateIndex == gates1.size()){
            minSoFar = Math.min(minSoFar,distance);
            return;
        }
        Gate gate = gates1.get(gateIndex);
        if(gate.fisherMenCapacity == gate.fishermenAssigned){
            solve(spots,gates1,distance,gateIndex+1);
            return;
        }
        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < spots.size(); i++) {
            int left = gate.index-i;
            int right = gate.index+i;
            boolean found = false;
            if(left >= 0 && !spots.get(left).occupied){
                candidates.add(left);
                found = true;
            }
            if(right < spots.size()&& left != right && !spots.get(right).occupied){
                candidates.add(right);
                found =true;
            }
            if(found){
                break;
            }
        }
        for (Integer candidate : candidates) {
            spots.get(candidate).occupied = true;
            gate.fishermenAssigned++;
            solve(spots,gates1,distance+Math.abs(gate.index-candidate)+1,gateIndex);
            gate.fishermenAssigned--;
            spots.get(candidate).occupied = false;
        }
    }

    private static void assignPermutated(List<Gate> gates, List<List<Gate>> permutated, int i, int i1, int i2) {
        List<Gate> result = new ArrayList<>();
        result.add(new Gate(gates.get(i).index,gates.get(i).fisherMenCapacity));
        result.add(new Gate(gates.get(i1).index,gates.get(i1).fisherMenCapacity));
        result.add(new Gate(gates.get(i2).index,gates.get(i2).fisherMenCapacity));
        permutated.add(result);
    }


}

class Gate extends Spot {
    final int fisherMenCapacity;
    int fishermenAssigned = 0;

    public Gate(int index, int fisherMenCapacity) {
        super(index);
        this.fisherMenCapacity = fisherMenCapacity;
    }
}

class Spot {
    final int index;
    boolean occupied;

    public Spot(int index) {
        this.index = index;
    }
}

class FishingSpot extends Spot {

    public FishingSpot(int index) {
        super(index);
    }
}
