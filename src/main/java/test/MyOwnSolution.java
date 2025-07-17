package test;

import java.util.*;

public class MyOwnSolution {

    public static void main(String[] args) {
        List<FishingSpot> fishingSpots = new ArrayList<>();
        int numberOfFishingSpots = 10;
        for (int i = 0; i < numberOfFishingSpots; i++) {
            fishingSpots.add(new FishingSpot(i));
        }
        List<Gate> gates = new ArrayList<>();
        gates.add(new Gate(4, 5)); // Gate at spot 4 (0-based), 5 fishermen
        gates.add(new Gate(6, 2)); // Gate at spot 6 (0-based), 2 fishermen
        gates.add(new Gate(9, 2)); // Gate at spot 9 (0-based), 2 fishermen

        Permutation permutation = new Permutation(gates.size());
        List<int[]> indexes = permutation.getResult();
        List<List<Gate>> allPermutatedGates = Gate.convertPermutation(indexes,gates);
        int minTotalDistance = Integer.MAX_VALUE;
        Assignment bestAssignment = null;

        for (List<Gate> permutatedGates : allPermutatedGates) {
            for (FishingSpot spot : fishingSpots) spot.release();
            Assignment assignment = new Assignment();
            int totalDistance = assignAndCalculateBacktrack(fishingSpots, permutatedGates, 0, 0, assignment, minTotalDistance);
            if (totalDistance < minTotalDistance) {
                minTotalDistance = totalDistance;
                bestAssignment = assignment.copy();
            }
        }

        System.out.println("Minimum total distance: " + minTotalDistance);
        if (bestAssignment != null) {
            System.out.println("Assignments (GatePos, SpotIndex, Distance):");
            for (Assignment.Step step : bestAssignment.steps) {
                System.out.println("Gate " + step.gateIndex + " -> Spot " + step.spotIndex + " (distance: " + step.distance + ")");
            }
        }
    }

    private static int assignAndCalculateBacktrack(List<FishingSpot> fishingSpots, List<Gate> gates, int gateIndex, int currentDistance, Assignment assignment, int minSoFar) {
        if (currentDistance >= minSoFar) return Integer.MAX_VALUE; // Prune
        if (gateIndex == gates.size()) {
            assignment.totalDistance = currentDistance;
            return currentDistance;
        }

        Gate gate = gates.get(gateIndex);
        int gatePos = gate.index;
        int fishermen = gate.fishermen;

        List<Integer> available = new ArrayList<>();
        for (FishingSpot spot : fishingSpots) {
            if (!spot.isOccupied()) available.add(spot.index);
        }

        List<List<Integer>> assignments = backtrackAssign(available, gatePos, fishermen, new ArrayList<>());

        int min = Integer.MAX_VALUE;
        for (List<Integer> assignList : assignments) {
            List<Assignment.Step> stepsToAdd = new ArrayList<>();
            int addDist = 0;
            for (int spotIdx : assignList) {
                fishingSpots.get(spotIdx).occupy();
                int dist = Math.abs(gatePos - spotIdx) + 1;
                addDist += dist;
                stepsToAdd.add(new Assignment.Step(gatePos, spotIdx, dist));
            }
            assignment.steps.addAll(stepsToAdd);

            int total = assignAndCalculateBacktrack(fishingSpots, gates, gateIndex + 1, currentDistance + addDist, assignment, Math.min(minSoFar, min));
            if (total < min) {
                min = total;
                if (gateIndex == 0) { // Only copy at the top level to avoid unnecessary copies
                    assignment.totalDistance = min;
                }
            }

            for (int spotIdx : assignList) fishingSpots.get(spotIdx).release();
            for (int i = 0; i < stepsToAdd.size(); i++) assignment.steps.remove(assignment.steps.size() - 1);
        }
        return min;
    }


    private static List<List<Integer>> backtrackAssign(List<Integer> available, int gatePos, int fishermen, List<Integer> current) {
        List<List<Integer>> result = new ArrayList<>();
        if (current.size() == fishermen) {
            result.add(new ArrayList<>(current));
            return result;
        }
        int minDist = Integer.MAX_VALUE;
        for (int idx : available) minDist = Math.min(minDist, Math.abs(gatePos - idx));
        for (int idx : available) {
            if (Math.abs(gatePos - idx) == minDist) {
                List<Integer> nextAvailable = new ArrayList<>(available);
                nextAvailable.remove((Integer) idx);
                current.add(idx);
                result.addAll(backtrackAssign(nextAvailable, gatePos, fishermen, current));
                current.remove(current.size() - 1);
            }
        }
        return result;
    }
}

class Assignment {
    static class Step {
        int gateIndex;
        int spotIndex;
        int distance;
        Step(int gateIndex, int spotIndex, int distance) {
            this.gateIndex = gateIndex;
            this.spotIndex = spotIndex;
            this.distance = distance;
        }
    }
    List<Step> steps = new ArrayList<>();
    int totalDistance = Integer.MAX_VALUE;

    Assignment copy() {
        Assignment a = new Assignment();
        a.totalDistance = this.totalDistance;
        for (Step s : this.steps) {
            a.steps.add(new Step(s.gateIndex, s.spotIndex, s.distance));
        }
        return a;
    }
}

class Gate {
    int index;
    int fishermen;
    public Gate(int index, int fishermen) {
        this.index = index;
        this.fishermen = fishermen;
    }

    public static List<List<Gate>> convertPermutation(List<int[]> indexes,List<Gate> gates){
        List<List<Gate>> allPermutatedGates = new ArrayList<>();
        for (int[] ints : indexes) {
            List<Gate> perm = new ArrayList<>();
            for (int i : ints) {
                perm.add(new Gate(gates.get(i).index, gates.get(i).fishermen));
            }
            allPermutatedGates.add(perm);
        }
        return  allPermutatedGates;
    }
}

class FishingSpot {
    int index;
    private boolean occupied;
    public FishingSpot(int index) {
        this.index = index;
        this.occupied = false;
    }
    public void occupy() { this.occupied = true; }
    public void release() { this.occupied = false; }
    public boolean isOccupied() { return occupied; }
}

class Permutation {
    public List<int[]> getResult() { return result; }
    List<int[]> result = new ArrayList<>();
    public Permutation(int number) {
        int[] data = new int[number];
        for (int i = 0; i < data.length; i++) data[i] = i;
        permute(0, data);
    }
    private void permute(int index, int[] data) {
        if (index == data.length) result.add(data.clone());
        else {
            for (int k = index; k < data.length; k++) {
                swap(data, k, index);
                permute(index + 1, data);
                swap(data, k, index);
            }
        }
    }
    private void swap(int[] data, int i, int j) {
        if (i == j) return;
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }
}