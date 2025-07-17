package doctor;

import java.util.*;

import static utils.Reader.readFloat;
import static utils.Reader.readInt;

/***
 2
 6 10 40
 1 2 0.3 1 3 0.7 3 3 0.2 3 4 0.8 2 4 1 4 5 0.9 4 4 0.1 5 6 1.0 6 3 0.5 6 6 0.5
 6 10 10
 1 2 0.3 1 3 0.7 3 3 0.2 3 4 0.8 2 4 1 4 5 0.9 4 4 0.1 5 6 1.0 6 3 0.5 6 6 0.5


 1
 6 10 40
 1 2 0.3 1 3 0.7 3 3 0.2 3 4 0.8 2 4 1 4 5 0.9 4 4 0.1 5 6 1.0 6 3 0.5 6 6 0.5

             1
             / \
         0.3/   \0.7
         /     \
         2       3
         \     / \
         1.0\ /0.8
         4
         / \
     0.9/   \0.1
     /     \
     5       (self-loop)
     \
     1.0\
         6
         / \
     0.5/   \0.5
     /     \
     3     (self-loop)
 (Additional self-loops:)
 3 --0.2--> 3
 4 --0.1--> 4
 6 --0.5--> 6
 */
import java.util.*;

public class Solution2 {
    public static void main(String[] args) {
        int numTest = readInt();
        for (int test = 0; test < numTest; test++) {
            int nodes = readInt();
            int edges = readInt();
            Map<Integer, Node> nodeMap = new HashMap<>();
            for (int i = 1; i <= nodes; i++) {
                nodeMap.put(i, new Node(i));
            }
            int time = readInt(); // Not used in traversal, but kept for compatibility
            for (int i = 0; i < edges; i++) {
                int starts = readInt();
                int ends = readInt();
                float probability = readFloat();
                Node startingNode = nodeMap.get(starts);
                Node endingNode = nodeMap.get(ends);
                Connection2 connection2 = new Connection2(startingNode, endingNode, probability);
                startingNode.connection2.add(connection2);
            }
            Node starting = nodeMap.get(1);
            travel(null, starting, new ArrayList<>(), new ArrayList<>());
        }
    }

    private static void travel(Node previous, Node current, List<Connection2> travelled, List<Integer> path) {
        path.add(current.index);
        boolean hasNext = false;
        for (Connection2 connection2 : current.connection2) {
            if (travelled.contains(connection2)) continue;
            hasNext = true;
            travelled.add(connection2);
            travel(current, connection2.destination, travelled, path);
            travelled.remove(travelled.size() - 1); // backtrack
        }
        if (!hasNext) {
            // Print the path when you reach a node with no unvisited outgoing edges
            System.out.println("Path: " + path);
        }
        path.remove(path.size() - 1); // backtrack
    }

    // Dummy input methods (replace with your own input logic)
    static Scanner sc = new Scanner(System.in);
    static int readInt() { return sc.nextInt(); }
    static float readFloat() { return sc.nextFloat(); }
}

class Node {
    int index;
    List<Connection2> connection2 = new ArrayList<>();

    public Node(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Node{" +
                "index=" + index +
                '}';
    }
}

class Connection2 {
    Node from;
    Node destination;
    float probability;

    public Connection2(Node from, Node destination, float probability) {
        this.destination = destination;
        this.probability = probability;
        this.from = from;
    }

    @Override
    public String toString() {
        return "Connection2{" +
                "from=" + from.index +
                ", destination=" + destination.index +
                ", probability=" + probability +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection2 that = (Connection2) o;
        return destination.index == that.destination.index && from.index == that.from.index && this.probability == that.probability;
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination.index, from.index, probability);
    }
}