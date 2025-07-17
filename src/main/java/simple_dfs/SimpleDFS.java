package simple_dfs;

import java.util.*;

public class SimpleDFS {

    /***
                 1
                / \
               2   3
               \  /
                4
     * @param args
     */
    public static void main(String[] args) {
        // Build a simple graph: 1 -> 2, 1 -> 3
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, Arrays.asList(2, 3));
        graph.put(2, List.of(4));
        graph.put(3, List.of(4));
        graph.put(4, Collections.emptyList());

        List<Integer> path = new ArrayList<>();
        System.out.println("All paths from node 1 to leaves:");
        dfsAllPaths(1, graph, path);
    }

    static void dfsAllPaths(int node, Map<Integer, List<Integer>> graph, List<Integer> path) {
        path.add(node);
        if (graph.get(node).isEmpty()) {
            // Leaf node, print the path
            System.out.println(path);
        } else {
            for (int neighbor : graph.get(node)) {
                dfsAllPaths(neighbor, graph, path);
            }
        }
//        path.remove(path.size() - 1); // backtrack
        System.out.println();
    }
}


