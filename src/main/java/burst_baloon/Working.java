package burst_baloon;

import java.util.*;

/*
There are N Balloons marked with value Bi (where B(i…N)).
User will be given Gun with N Bullets and user must shot N times.
When any balloon explodes then its adjacent balloons becomes next to each other.
User has to score highest points to get the prize and score starts at 0.
Below is the condition to calculate the score.
When Balloon Bi Explodes then score will be
 a product of Bi-1 & Bi+1 (score = Bi-1 * Bi+1).
 When Balloon Bi Explodes and there is only left Balloon present then score will be Bi-1.
 When Balloon Bi Explodes and there is only right Balloon present then score will be Bi+1.
 When Balloon Bi explodes and there is no left and right Balloon present then score will be Bi.
 Write a program to score maximum points.

Input 4 1 2 3 4

Output

20
*/
public class Working {
    private static int maxScore = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cols = sc.nextInt();
        List<Integer> balloons = new ArrayList<>();
        balloons.add(1); // Padding left
        for (int i = 0; i < cols; i++) {
            balloons.add(sc.nextInt());
        }
        balloons.add(1); // Padding right
        solve(balloons, 0);
        System.out.println(maxScore);
    }

    private static void solve(List<Integer> balloons, int point) {
        if (balloons.size() == 2) {
            maxScore = Math.max(point, maxScore);
            return;
        }
        for (int i = 1; i < balloons.size() - 1; i++) {
            int gain;
            if (balloons.size() == 3) {
                // Only one balloon left (between paddings)
                gain = balloons.get(i);
            } else if (i == 1) {
                // Only right neighbor
                gain = balloons.get(i + 1);
            } else if (i == balloons.size() - 2) {
                // Only left neighbor
                gain = balloons.get(i - 1);
            } else {
                // Both neighbors
                gain = balloons.get(i - 1) * balloons.get(i + 1);
            }
            int removed = balloons.remove(i);
            solve(balloons, point + gain);
            balloons.add(i, removed); // backtrack
        }
    }
}
