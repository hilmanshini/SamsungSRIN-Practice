package doctor;

import java.util.*;

public class Sol {
    static class Node {
        int index;
        List<Node> connections = new ArrayList<>();
        public Node(int index) {
            this.index = index;
        }
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Node)) return false;
            Node node = (Node) o;
            return index == node.index;
        }
        @Override
        public int hashCode() {
            return Objects.hashCode(index);
        }
        @Override
        public String toString() {
            return "Node{" + "index=" + index + '}';
        }
    }

    public static void main(String[] args) {
        // Example: build a directed graph with a cycle
        int nodes = 5;
        int[][] edges = {
            {1, 2},
            {2, 3},
            {3, 4},
            {4, 2}, // This edge creates a cycle: 2 -> 3 -> 4 -> 2
            {4, 5}
        };
        Map<Integer, Node> nodeList = new HashMap<>();
        for (int i = 1; i <= nodes; i++) {
            nodeList.put(i, new Node(i));
        }
        for (int[] edge : edges) {
            Node from = nodeList.get(edge[0]);
            Node to = nodeList.get(edge[1]);
            from.connections.add(to);
        }

        Set<Node> visited = new HashSet<>();
        Set<Node> recStack = new HashSet<>();
        boolean cycleFound = false;
        for (Node node : nodeList.values()) {
            if (!visited.contains(node)) {
                if (hasCycle(node, visited, recStack)) {
                    cycleFound = true;
                    break;
                }
            }
        }
        System.out.println("Cycle found? " + cycleFound);
    }

    static boolean hasCycle(Node node, Set<Node> visited, Set<Node> recStack) {
        if (recStack.contains(node)) return true; // found a cycle
        if (visited.contains(node)) return false; // already processed

        visited.add(node);
        recStack.add(node);

        for (Node neighbor : node.connections) {
            if (hasCycle(neighbor, visited, recStack)) return true;
        }

        recStack.remove(node); // backtracking step
        return false;
    }
}

