package test4;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solll {
    static int minSoFar;

    public static void main(String[] args) {
        int numberOfGates = 3;
        Scanner scanner = new Scanner(System.in);
        int numberOfTest = scanner.nextInt();
        IntStream.range(0, numberOfTest).forEach(value -> {
            int numberOfSpots = scanner.nextInt();
            List<Gate> gates = IntStream.range(0, numberOfGates).mapToObj(value1 -> {
                int gateIndex = scanner.nextInt() - 1;
                int fishermanCount = scanner.nextInt();
                return new Gate(gateIndex, fishermanCount);
            }).collect(Collectors.toList());

            // Generate all permutations of gate order
            List<List<Gate>> permutations = new ArrayList<>();
            manualPermutations(permutations, gates, 0, 1, 2);
            manualPermutations(permutations, gates, 0, 2, 1);
            manualPermutations(permutations, gates, 1, 0, 2);
            manualPermutations(permutations, gates, 1, 2, 0);
            manualPermutations(permutations, gates, 2, 1, 0);
            manualPermutations(permutations, gates, 2, 0, 1);

            minSoFar = Integer.MAX_VALUE; // reset min for each test case

            for (List<Gate> gateOrder : permutations) {
                // Create fresh spots for each permutation
                List<Spot> spots = IntStream.range(0, numberOfSpots)
                        .mapToObj(FishingSpot::new)
                        .collect(Collectors.toList());
                // Reset gate assignment
                for (Gate g : gates) g.fisherManAssigned = 0;
                solve(spots, 0, gateOrder, 0);
            }
            System.out.println(minSoFar);
        });
    }

    private static void manualPermutations(List<List<Gate>> result, List<Gate> permutations, int a, int b, int c) {
        List<Gate> resultNested = new ArrayList<>();
        resultNested.add(permutations.get(a));
        resultNested.add(permutations.get(b));
        resultNested.add(permutations.get(c));
        result.add(resultNested);
    }

    // OOP recursive solve, matching reference logic
    private static void solve(List<Spot> spots, int distance, List<Gate> gateOrder, int gateIdx) {
        if (distance >= minSoFar) return;
        if (gateIdx == gateOrder.size()) {
            minSoFar = Math.min(minSoFar, distance);
            return;
        }
        Gate gate = gateOrder.get(gateIdx);
        if (gate.fisherManAssigned == gate.fishermenCount) {
            solve(spots, distance, gateOrder, gateIdx + 1);
            return;
        }

        // Find all nearest available spots (including gate's own spot)
        List<Integer> candidates = new ArrayList<>();
        for (int d = 0; d < spots.size(); d++) {
            int left = gate.index - d;
            int right = gate.index + d;
            boolean found = false;
            if (left >= 0 && !spots.get(left).isOccupied()) {
                candidates.add(left);
//                minDist = d + 1;
                found = true;
            }
            if (right < spots.size() && right != left && !spots.get(right).isOccupied()) {
                candidates.add(right);
//                minDist = d + 1;
                found = true;
            }
            if (found) break;
        }

        for (int pos : candidates) {
            spots.get(pos).setOccupied(true);
            gate.fisherManAssigned++;
            solve(spots, distance + Math.abs(gate.index - pos) + 1, gateOrder, gateIdx);
            spots.get(pos).setOccupied(false);
            gate.fisherManAssigned--;
        }
    }
}

class Gate {
    final int index;
    final int fishermenCount;
    int fisherManAssigned;

    Gate(int index, int fishermenCount) {
        this.index = index;
        this.fishermenCount = fishermenCount;
        this.fisherManAssigned = 0;
    }
}

class FishingSpot extends Spot {
    FishingSpot(int index) {
        super(index);
    }

    @Override
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}

abstract class Spot {
    final int index;
    protected boolean occupied;

    Spot(int index) {
        this.index = index;
        this.occupied = false;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public abstract void setOccupied(boolean occupied);
}