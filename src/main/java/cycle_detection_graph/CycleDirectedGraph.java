package cycle_detection_graph;

import java.util.*;

public class CycleDirectedGraph {
    static List<List<Integer>> adj = new ArrayList<>();
    static boolean[] vis = new boolean[20];
    static int[] stack = new int[20];
    static int top = -1;
    static int mini = Integer.MAX_VALUE;
    static List<Integer> ans = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        // Initialize adjacency list
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        // Read edges
        for (int i = 1; i <= m; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            adj.get(x).add(y);
        }

        // Try starting from each node
        for (int i = 1; i <= n; i++) {
            if (!vis[i]) {
                stack[++top] = i;
                detect(i);
            }
        }

        // Sort and print result
        Collections.sort(ans);
        for (int i = 0; i < ans.size(); i++) {
            System.out.print(ans.get(i));
            if (i < ans.size() - 1) System.out.print(" ");
        }
    }

    static void detect(int node) {
        if (vis[node]) {
            // Found cycle
            List<Integer> temp = new ArrayList<>();
            int sum = 0;
            for (int i = top - 1; i >= 0; i--) {
                temp.add(stack[i]);
                sum += stack[i];
                if (stack[i] == node) {
                    break;
                }
            }
            if (sum < mini) {
                mini = sum;
                ans = new ArrayList<>(temp);
            }
            return;
        }

        if (!vis[node]) {
            vis[node] = true;
            for (int neighbor : adj.get(node)) {
                stack[++top] = neighbor;
                detect(neighbor);
                top--;
            }
            vis[node] = false;
        }
    }
}
