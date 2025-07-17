package doctor;

import java.util.*;

import static utils.Reader.readInt;

public class Solution4 {
    public static void main(String[] args) {
        int test = readInt();
        int numNodes =readInt();
        int edges = readInt();
        Map<Integer,Node> nodeMap = new HashMap<>();
        for (int i = 0; i < numNodes; i++) {
            nodeMap.put(i+1,new Node(i+1));
        }
        List<Edge> edgeList = new ArrayList<>();
        for (int i = 0; i < edges; i++) {

        }

    }

    static class Edge{
        Node from;
        Node to;

        public Edge(Node from, Node to) {
            this.from = from;
            this.to = to;
        }
    }
    static class Node{
        public Node(int index) {
            this.index = index;
        }

        int index;

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
    }
}
