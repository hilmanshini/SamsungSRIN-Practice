package cycle_detection_graph;

import java.util.*;

public class CycleDetect {
    static Map<Integer, Node> nodes = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); // number of nodes
        int m = sc.nextInt(); // number of edges

        // Create nodes
        for (int i = 1; i <= n; i++) {
            nodes.put(i, new Node(i));
        }

        // Read edges
        for (int i = 1; i <= m; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            nodes.get(x).addNeighbor(nodes.get(y));
        }

        boolean hasCycle = false;

        // Check for cycles starting from each unvisited node
        for (Node node : nodes.values()) {
            if (!node.isVisited()) {
                if (dfs(node)) {
                    hasCycle = true;
                    break;
                }
            }
        }

        if (hasCycle) {
            System.out.println("Cycle detected");
        } else {
            System.out.println("No cycle detected");
        }
    }

    static boolean dfs(Node node) {
        node.setVisited(true);
        node.setInPath(true);

        for (Node neighbor : node.getNeighbors()) {
            // If neighbor is not visited
            if (!neighbor.isVisited()) {
                boolean check = dfs(neighbor);
                if (check) {
                    return true;
                }
            }
            // If neighbor is visited and in current path
            else if (neighbor.isInPath()) {
                return true;
            }
        }

        node.setInPath(false); // Remove from current path
        return false;
    }
}

class Node {
    private int id;
    private List<Node> neighbors;
    private boolean visited;
    private boolean inPath;

    public Node(int id) {
        this.id = id;
        this.neighbors = new ArrayList<>();
        this.visited = false;
        this.inPath = false;
    }

    public void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isInPath() {
        return inPath;
    }

    public void setInPath(boolean inPath) {
        this.inPath = inPath;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Node " + id;
    }
}




