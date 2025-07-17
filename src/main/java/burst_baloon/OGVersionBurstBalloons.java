package burst_baloon;

import java.util.*;

public class OGVersionBurstBalloons {
    static int n;
    static List<Integer> v = new ArrayList<>();
    static int[][] dp;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        v.add(1); // Padding left
        for (int i = 1; i <= n; i++) {
            v.add(sc.nextInt());
        }
        v.add(1); // Padding right

        dp = new int[n + 2][n + 2];
        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println(burst(1, n));
    }

    static int burst(int i, int j) {
        if (i > j) return 0;
        if (dp[i][j] != -1) return dp[i][j];
        int ans = Integer.MIN_VALUE;
        for (int index = i; index <= j; index++) {
            int cost;
            if (i == 1 && j == n) {
                cost = v.get(index) + burst(i, index - 1) + burst(index + 1, j);
            } else {
                cost = v.get(i - 1) * v.get(j + 1) + burst(i, index - 1) + burst(index + 1, j);
            }
            ans = Math.max(ans, cost);
        }
        dp[i][j] = ans;
        return ans;
    }
}
