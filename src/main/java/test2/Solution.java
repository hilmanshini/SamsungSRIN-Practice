package test2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {

    public static void main(String[] args) {
        List<Gate> gates = new ArrayList<>();
        gates.add(new Gate(7, 5));
        gates.add(new Gate(8, 1));
        gates.add(new Gate(9, 2));

        int numberSpots = 10;

        List<List<Gate>> allPossibleOrderGates = Gate.generateByIndex(
                PermutationGenerator.generate(gates.size()), gates
        );

        List<Spot> fishingSpots = IntStream.range(0, numberSpots)
                .mapToObj(FishingSpot::new)
                .collect(Collectors.toList());

        int minTotalDistance = Integer.MAX_VALUE;

        for (List<Gate> gateOrder : allPossibleOrderGates) {
            FishingSpot.purge(fishingSpots);
            int totalDistance = findDistance(
                    fishingSpots,
                    gateOrder,
                    0,
                    gateOrder.get(0).fishermen,
                    0,
                    minTotalDistance
            );

            minTotalDistance = Math.min(minTotalDistance, totalDistance);

        }

        System.out.println("Minimal Total Distance = " + minTotalDistance);
    }
    public static int findDistance(
            List<Spot> spots,
            List<Gate> gates,
            int gateIndex,
            int remainingFishermen,
            int currentDistance,
                 int bestSoFar
    ) {
        if (gateIndex == gates.size()) {
            return currentDistance;
        }

        Gate gate = gates.get(gateIndex);

        if (remainingFishermen == 0) {
            int nextGateIndex = gateIndex + 1;
            if (nextGateIndex == gates.size()) {
                return currentDistance;
            }
            return findDistance(
                    spots,
                    gates,
                    nextGateIndex,
                    gates.get(nextGateIndex).fishermen,
                    currentDistance,
                    bestSoFar
            );
        }

        int minDistance = bestSoFar;

        for (int dist = 0; dist < spots.size(); dist++) {
            List<Spot> candidates = new ArrayList<>();

            if (gate.isLeftDistanceValid(dist, spots)) {
                candidates.add(spots.get(gate.index - dist));
            }
            if (gate.isRightDistanceValid(dist, spots)) {
                candidates.add(spots.get(gate.index + dist));
            }

            if (!candidates.isEmpty()) {
                for (Spot s : candidates) {
                    s.occupied = true;

                    int distance = findDistance(
                            spots,
                            gates,
                            gateIndex,
                            remainingFishermen - 1,
                            currentDistance + dist + 1,
                            minDistance
                    );
                    minDistance = Math.min(minDistance, distance);

                    s.occupied = false; // backtrack
                }
                break; // only nearest distance
            }
        }

        return minDistance;
    }
}
