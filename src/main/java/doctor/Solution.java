package doctor;

import java.util.*;

public class Solution {

    static void dfs(int source, int time, double prob, List<Pair>[] v, double[] ans) {
        if (time <= 0) {
            ans[source] += prob;
            return;
        }

        for (Pair it : v[source]) {
            if (it.probability != 0.0) {
                int newSource = it.node;
                double p = it.probability;

                prob = p * prob;
                dfs(newSource, time - 10, prob, v, ans);
                prob /= p; // backtrack
            }
        }
    }

    static void solve() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int e = sc.nextInt();
        int t = sc.nextInt();

        @SuppressWarnings("unchecked")
        List<Pair>[] v = new List[20];
        for (int i = 0; i < 20; i++) {
            v[i] = new ArrayList<>();
        }

        double[] ans = new double[20];

        for (int i = 1; i <= e; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            double p = sc.nextDouble();
            v[x].add(new Pair(y, p));
        }

        dfs(1, t, 1.0, v, ans);

        double maxi = 0.0;
        int node = 1;
        for (int i = 1; i <= n; i++) {
            if (maxi < ans[i]) {
                maxi = ans[i];
                node = i;
            }
        }

        System.out.printf("%d %.6f%n", node, ans[node]);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int tt = sc.nextInt();
        while (tt-- > 0) {
            solve();
        }
    }

    static class Pair {
        int node;
        double probability;

        Pair(int node, double probability) {
            this.node = node;
            this.probability = probability;
        }
    }
}
