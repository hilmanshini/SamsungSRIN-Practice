package burst_baloon;

import java.util.*;

import static utils.Reader.readInt;

public class Solution {
    private static int maxScore = 0;

    public static void main(String[] args) {
        int cols = readInt();
        List<Integer> baloons = new ArrayList<>();
        baloons.add(1);
        for (int i = 0; i < cols; i++) {
            baloons.add(readInt());
        }
        baloons.add(1);
        solve(baloons,0);
        System.out.println(maxScore);
    }

    private static void solve(List<Integer> balloons, int point) {
        if(balloons.size() == 2){
            maxScore = Math.max(point,maxScore);
            return;
        }
//        System.out.println(point);
        for (int i = 1; i < balloons.size()-1; i++) {
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
            solve(balloons,point+gain);
            balloons.add(i,removed);
        }
    }
}
