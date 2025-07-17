package test5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numOfTest = scanner.nextInt();
        IntStream.range(0, numOfTest).forEach(value -> {
            minSoFar = Integer.MAX_VALUE;
            int numOfSpots = scanner.nextInt();
            List<Gate> gates = IntStream.range(0, 3).mapToObj(value1 -> {
                int gateIndex = scanner.nextInt()-1;
                int fishermenCOunt = scanner.nextInt();
                return new Gate(gateIndex, fishermenCOunt);
            }).collect(Collectors.toList());
            List<List<Gate>> permutated = new ArrayList<>();
            assignPermutated(0, 1, 2, permutated, gates);
            assignPermutated(0, 2, 1, permutated, gates);
            assignPermutated(1, 0, 2, permutated, gates);
            assignPermutated(1, 2, 0, permutated, gates);
            assignPermutated(2, 1, 0, permutated, gates);
            assignPermutated(2, 0, 1, permutated, gates);
            permutated.forEach(gates1 -> {
                var listOfSpots = IntStream.range(0, numOfSpots).mapToObj(FishingSpot::new).collect(Collectors.toList());
                solve(listOfSpots,0,gates1,0);
            });

            System.out.println(minSoFar);
        });
    }

    static  int minSoFar = Integer.MAX_VALUE;

    private static void solve(List<FishingSpot> listOfSpots, int distance, List<Gate> gateOrder, int gateIndex) {
        if(distance >= minSoFar)return;
        if(gateIndex == gateOrder.size()){
            minSoFar = Math.min(minSoFar,distance);
            return;
        }
        Gate gate = gateOrder.get(gateIndex);
        if(gate.fisherMenCount == gate.fishermanAssigned){
            solve(listOfSpots, distance, gateOrder, gateIndex+1);
            return;
        }
        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < listOfSpots.size(); i++) {
            int left = gate.index - i;
            int right = gate.index + i;
            boolean found = false;
            if(left >= 0 && !listOfSpots.get(left).occupied){
                candidates.add(left);
                found =true;
            }
            if(right < listOfSpots.size() && right != left && !listOfSpots.get(right).occupied){
                candidates.add(right);
                found = true;
            }
            if(found)break;

        }
        for (Integer candidate : candidates) {
            listOfSpots.get(candidate).occupied = true;
            gate.fishermanAssigned++;
            solve(listOfSpots,distance+Math.abs(gate.index-candidate)+1,gateOrder,gateIndex);
            gate.fishermanAssigned--;
            listOfSpots.get(candidate).occupied = false;
        }

    }

    private static void assignPermutated(int i, int i1, int i2, List<List<Gate>> permutated, List<Gate> gates) {
        List<Gate> result = new ArrayList<>();
        result.add(new Gate(gates.get(i).index, gates.get(i).fisherMenCount));
        result.add(new Gate(gates.get(i1).index, gates.get(i1).fisherMenCount));
        result.add(new Gate(gates.get(i2).index, gates.get(i2).fisherMenCount));
        permutated.add(result);
    }
}


class Gate extends Spot {
    final int fisherMenCount;
    int fishermanAssigned = 0;

    Gate(int index, int fisherMenCount) {
        super(index);
        this.fisherMenCount = fisherMenCount;
    }
}

class FishingSpot extends Spot {

    FishingSpot(int index) {
        super(index);
    }
}

class Spot {
    final int index;
    boolean occupied;

    Spot(int index) {
        this.index = index;
    }
}