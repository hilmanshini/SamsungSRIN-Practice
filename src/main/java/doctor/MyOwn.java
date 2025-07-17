package doctor;

import java.util.*;

import static utils.Reader.readInt;
/*

1 - > 2 -> 3 -> 4 -> 2

    1 2 2 3 3 4 4 2 2 5 5 6 6 2 2 7 7 8 8 2
    1 2
    2 3
    3 4
    4 2
    2 5
    5 6
    6 2
    2 7
    7 8
    8 2
 */
public class MyOwn {
    public static void main(String[] args) {
        int edgeNum = 10;
        int nodes = 8;
        Map<Integer,Node> nodeList = new HashMap<>();
        for (int i = 1; i <= nodes; i++) {
            nodeList.put(i,new Node(i));
        }
        for (int i = 0; i < edgeNum; i++) {
            Node from = nodeList.get(readInt());
            Node to = nodeList.get(readInt());
            from.connections.add(to);
        }
        List<Cycle> cycles = new ArrayList<Cycle>();
        travel(null,new ArrayList<>(nodeList.values()),new ArrayList<Edge>(),cycles,edgeNum);
//        for (Cycle cycle : cycles) {
//            System.out.println(cycle);
//        }
    }

    private static void travel(Node prev, List<Node> nodes, List<Edge> edges, List<Cycle> cycles, int edgeNum) {
        System.out.println(edges);
        for (Node node : nodes) {
            if (prev != null) {
                Edge edge = new Edge(prev, node);
                if (edges.contains(edge)) {
                    List<Edge> edgesCycle = new ArrayList<>();
                    for (int i1 = edges.indexOf(edge); i1 < edges.size(); i1++) {
                        edgesCycle.add(edges.get(i1));
                    }
                    Cycle cycle = new Cycle(edge, edgesCycle);
                    if (!cycles.contains(cycle) && edgeNum != edgesCycle.size()) {
                        cycles.add(cycle);
                        System.out.println(cycle);
                    }
                    continue;
                } else {
                    edges.add(edge);
                }
            }

            if (edges.size() < edgeNum) {

                System.out.printf("%d %d \n", edges.size(), edgeNum);
                travel(node, node.connections, edges, cycles, edgeNum);
            }
        }
    }

    static  class Node{
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
            return "Node{" +
                    "index=" + index +
                    '}';
        }
    }

    static class Cycle{
        Edge edge;
        List<Edge> edges;

        public Cycle(Edge edge, List<Edge> edges) {
            this.edge = edge;
            this.edges = edges;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Cycle)) return false;
            Cycle cycle = (Cycle) o;
            return Objects.equals(edge, cycle.edge);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(edge);
        }

        @Override
        public String toString() {
            return "Cycle{" +
                    "edge=" + edge +
                    ", edges=" + edges +
                    '}';
        }
    }

    static class Edge{
        Node from,to;

        public Edge(Node from, Node to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Edge)) return false;
            Edge edge = (Edge) o;
            return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }

        @Override
        public String toString() {
            return "[" +from.index +
                    "-> " + to.index +
                    ']';
        }
    }
}
