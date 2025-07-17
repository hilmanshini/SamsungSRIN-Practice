package doctor;

import java.util.*;

import static utils.Reader.readFloat;
import static utils.Reader.readInt;

/**
 2
 6 10 40
 1 2 0.3 1 3 0.7 3 3 0.2 3 4 0.8 2 4 1 4 5 0.9 4 4 0.1 5 6 1.0 6 3 0.5 6 6 0.5
 6 10 10
 1 2 0.3 1 3 0.7 3 3 0.2 3 4 0.8 2 4 1 4 5 0.9 4 4 0.1 5 6 1.0 6 3 0.5 6 6 0.5
 */
public class Solution3 {

    public static void main(String[] args) {
        int numTest = readInt();
        for (int test = 0; test < numTest; test++) {
            int nodes = readInt();
            Map<Integer, Node> nodeMap = new HashMap<>();
            for (int i = 0; i < nodes; i++) {
                nodeMap.put(i + 1, new Node(i + 1));
            }
            int edges = readInt();
            int time = readInt();
            for (int i = 0; i < edges; i++) {
                int from = readInt();
                int to = readInt();
                float probability = readFloat();
                Connection connection = new Connection(nodeMap.get(from), nodeMap.get(to), probability);
                nodeMap.get(from).connection.add(connection);
            }
            List<Connection> travelled = new ArrayList<>();
            Node current = nodeMap.get(1);
            travel(current, travelled, time, 1.0f);
            Optional<Map.Entry<Integer, Float>> max = Optional.empty();
            for (Map.Entry<Integer, Float> integerFloatEntry : probabilities.entrySet()) {
                if (max.isEmpty()) {
                    max = Optional.of(integerFloatEntry);
                } else {
                    if (max.get().getValue() < integerFloatEntry.getValue()) {
                        max = Optional.of(integerFloatEntry);
                    }
                }
            }
            max.ifPresent(integerFloatEntry -> System.out.printf("%d %f ", integerFloatEntry.getKey(), integerFloatEntry.getValue()));
//            System.out.println();
        }

    }

    static Map<Integer, Float> probabilities = new HashMap<>();

    private static void travel(Node node, List<Connection> travelled, int time, float probability) {
        if (time <= 0) {

            probabilities.put(node.index, probabilities.getOrDefault(node.index, 0f) + probability);
            if (travelled.isEmpty()) {
                return;
            }
            System.out.println();
            ;
            float probs = travelled.get(0).probability;
            for (int i = 1; i < travelled.size(); i++) {
                probs = probs * travelled.get(i).probability;
            }
//            for (Connection connection : travelled) {
//                System.out.printf("travel from %d to  %d \n",connection.from.index,connection.to.index);
//            }
//            System.out.println(probs);
//            System.out.println();
            return;
        }
        int sum = 0;
        for (Connection connection : node.connection) {
//            if (travelled.contains(connection)) continue;
            travelled.add(connection);
            travel(connection.to, travelled, time - 10, connection.probability * probability);
            travelled.remove(travelled.size() - 1);
        }

    }

    static class Node {
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


        int index;
        List<Connection> connection = new ArrayList<>();
    }

    static class Connection {
        public Connection(Node from, Node to, float probability) {
            this.from = from;
            this.to = to;
            this.probability = probability;
        }

        Node from;
        Node to;
        float probability;

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Connection)) return false;
            Connection that = (Connection) o;
            return Float.compare(probability, that.probability) == 0 && Objects.equals(from, that.from) && Objects.equals(to, that.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to, probability);
        }
    }
}


