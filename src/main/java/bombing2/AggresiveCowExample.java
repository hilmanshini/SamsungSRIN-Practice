package bombing2;

import java.util.*;

public class AggresiveCowExample {

    private static boolean canPlaceCows(int[] stalls, int cows, int minDistance) {
        int cowsPlaced = 1;
        int lastPosition = stalls[0];

        System.out.println("Trying to place " + cows + " cows with minimum distance " + minDistance);
        System.out.println("Place cow 1 at position " + lastPosition);

        for (int i = 1; i < stalls.length; i++) {
            if (stalls[i] - lastPosition >= minDistance) {
                cowsPlaced++;
                lastPosition = stalls[i];
                System.out.println("Place cow " + cowsPlaced + " at position " + lastPosition +
                        " (distance from previous: " + (stalls[i] - (cowsPlaced > 1 ? stalls[Arrays.asList(stalls).indexOf(lastPosition) - 1] : stalls[0])) + ")");
            } else {
                System.out.println("Position " + stalls[i] + " is too close (distance: " + (stalls[i] - lastPosition) +
                        ", need: " + minDistance + ")");
            }
        }

        System.out.println("Total cows placed: " + cowsPlaced);
        System.out.println("Can place " + cows + " cows? " + (cowsPlaced >= cows));
        System.out.println("----------------------------------------");

        return cowsPlaced >= cows;
    }

    private static int largestMinimumDistance(int[] stalls, int cows) {
        int n = stalls.length;
        int left = 0;
        int right = stalls[n-1] - stalls[0];

        int result = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (canPlaceCows(stalls, cows, mid)) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        // EXAMPLE 1: canPlaceCows returns TRUE
        System.out.println("========== EXAMPLE 1: canPlaceCows = TRUE ==========");
        int[] stalls1 = {1, 2, 4, 8, 9, 15, 18, 25, 30, 35, 40, 45, 50};
        int cows1 = 4;

        System.out.println("Stalls: " + Arrays.toString(stalls1));
        System.out.println("Cows to place: " + cows1);
        System.out.println();

        // Test with distance 10 - should return TRUE
        System.out.println("Testing distance 10:");
        boolean result1 = canPlaceCows(stalls1, cows1, 10);
        System.out.println("Result: " + result1 + " (TRUE - we can place 4 cows)\n");

        // Show the actual algorithm
        System.out.println("Running full algorithm:");
        int answer1 = largestMinimumDistance(stalls1, cows1);
        System.out.println("Maximum minimum distance: " + answer1);

        System.out.println("\n\n");

        // EXAMPLE 2: canPlaceCows returns FALSE
        System.out.println("========== EXAMPLE 2: canPlaceCows = FALSE ==========");
        int[] stalls2 = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29};
        int cows2 = 6;

        System.out.println("Stalls: " + Arrays.toString(stalls2));
        System.out.println("Cows to place: " + cows2);
        System.out.println();

        // Test with distance 8 - should return FALSE
        System.out.println("Testing distance 8:");
        boolean result2 = canPlaceCows(stalls2, cows2, 8);
        System.out.println("Result: " + result2 + " (FALSE - we can only place 4 cows, need 6)\n");

        // Show the actual algorithm
        System.out.println("Running full algorithm:");
        int answer2 = largestMinimumDistance(stalls2, cows2);
        System.out.println("Maximum minimum distance: " + answer2);

        System.out.println("\n\n");

        // BONUS: Show the transition point
        System.out.println("========== BONUS: Showing TRUE to FALSE transition ==========");
        int[] stalls3 = {1, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60};
        int cows3 = 5;

        System.out.println("Stalls: " + Arrays.toString(stalls3));
        System.out.println("Cows to place: " + cows3);
        System.out.println();

        // Test different distances to show the transition
        for (int dist = 10; dist <= 15; dist++) {
            System.out.println("Testing distance " + dist + ":");
            boolean result = canPlaceCows(stalls3, cows3, dist);
            System.out.println("Result: " + result + "\n");
        }

        int answer3 = largestMinimumDistance(stalls3, cows3);
        System.out.println("Maximum minimum distance: " + answer3);
    }
}

/*
Expected Output Summary:

EXAMPLE 1 (TRUE):
- Stalls: [1, 2, 4, 8, 9, 15, 18, 25, 30, 35, 40, 45, 50]
- Cows: 4, Distance: 10
- Placement: positions 1, 15, 25, 35 (or similar)
- Result: TRUE (can place 4 cows)

EXAMPLE 2 (FALSE):
- Stalls: [1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29]
- Cows: 6, Distance: 8
- Can only place about 4 cows with distance 8
- Result: FALSE (need 6 cows, can only place 4)

This shows the clear difference between when the function returns true vs false!
**/