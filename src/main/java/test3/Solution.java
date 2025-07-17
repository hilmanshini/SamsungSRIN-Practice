package test3;

import java.util.Scanner;

public class Solution {

    static final int INT_MAX = 999999;
    static int numberOfFishingSpots, minimumSoFar;
    static boolean[] visited = new boolean[100];

    static class Gate {
        int index;
        int fishermanCount;
    }

    static Gate[] gates = new Gate[4]; // indices 1 to 3 used

    static int findLeft(int index) {
        for (int i = index; i > 0; i--) {
            if (!visited[i]) {
                return i;
            }
        }
        return INT_MAX;
    }

    static int findRight(int index) {
        for (int i = index + 1; i <= numberOfFishingSpots; i++) {
            if (!visited[i]) {
                return i;
            }
        }
        return INT_MAX;
    }

    static void solve(int x, int y, int z, int distance, int curGate) {
        if (distance > minimumSoFar) return;

        if (gates[curGate].fishermanCount == 0) {
            if (curGate == x) curGate = y;
            else if (curGate == y) curGate = z;
            else {
                minimumSoFar = Math.min(minimumSoFar, distance);
                return;
            }
        }

        int l = findLeft(gates[curGate].index);
        int r = findRight(gates[curGate].index);
        int index = gates[curGate].index;
        int costL = Math.abs(index - l) + 1;
        int costR = Math.abs(index - r) + 1;

        boolean goLeft = true;
        boolean goRight = true;

        if (costL > costR) goLeft = false;
        if (costR > costL) goRight = false;

        if (goLeft && l != INT_MAX) {
            visited[l] = true;
            gates[curGate].fishermanCount--;
            solve(x, y, z, distance + costL, curGate);
            visited[l] = false;
            gates[curGate].fishermanCount++;
        }

        if (goRight && r != INT_MAX) {
            visited[r] = true;
            gates[curGate].fishermanCount--;
            solve(x, y, z, distance + costR, curGate);
            visited[r] = false;
            gates[curGate].fishermanCount++;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0) {
            numberOfFishingSpots = sc.nextInt();

            // Initialize gates array
            for (int i = 1; i <= 3; i++) {
                if (gates[i] == null) {
                    gates[i] = new Gate();
                }
                gates[i].index = sc.nextInt();
                gates[i].fishermanCount = sc.nextInt();
            }

            for (int i = 0; i < visited.length; i++) {
                visited[i] = false;
            }

            minimumSoFar = INT_MAX;

            solve(1, 2, 3, 0, 1);
            solve(1, 3, 2, 0, 1);
            solve(2, 1, 3, 0, 2);
            solve(2, 3, 1, 0, 2);
            solve(3, 1, 2, 0, 3);
            solve(3, 2, 1, 0, 3);

            System.out.println(minimumSoFar);
        }
        sc.close();
    }
}