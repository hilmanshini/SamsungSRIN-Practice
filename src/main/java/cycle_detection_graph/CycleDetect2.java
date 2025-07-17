package cycle_detection_graph;

import java.util.*;

import static utils.Reader.readInt;

/***
 7 8
 1 2 2 4 4 5 5 3 3 2 4 6 6 7 7 4
           7 <- 6
            \ /
 1 - > 2 -> 4
        ^   v
       3 <- 5
output
 [Node{index=2}, Node{index=4}, Node{index=5}, Node{index=3}]
 [ Node{index=4}, Node{index=6}, Node{index=7}]

 5 5
 2 4 2 3 4 3 1 5 5 1

 6 7
 2 4 4 5 4 6 5 6 6 5 4 3 2 3
 2 -> 4 - 5
      |  /
      6
 */

/***
 this one is verified
 */
public class CycleDetect2 {
    public static void main(String[] args) {
        int nodes = readInt();
        int edges = readInt();
        Map<Integer,Node> nodeMap = new HashMap<>();
        for (int i = 0; i < nodes; i++) {
            nodeMap.put(i+1,new Node(i+1));
        }
        for (int i = 0; i < edges; i++) {
            Node from = nodeMap.get(readInt());
            Node to = nodeMap.get(readInt());
            from.connect(to);
        }
        List<Node> nodesValues = new ArrayList<>(nodeMap.values());
        List<Node> travelled = new ArrayList<>();
        for (Node value : nodeMap.values()) {

            process(nodeMap,value,travelled);
        }
        List<Node> graphMin = new ArrayList<>();
        for (List<Node> graph : graphs) {
            if(graphMin.isEmpty()){
                graphMin = graph;
            } else {
                if(graph.size() < graphMin.size()){
                    graphMin = graph;
                }
            }
        }
        for (Node node : graphMin) {
            System.out.printf("%d ",node.index);
        }
    }

    private static void process(Map<Integer, Node> nodeMap, Node currentNode, List<Node> travelled) {
//        if(currentNode.connected.isEmpty()){
//            List<Node> copy = new ArrayList<>(travelled);
//            Collections.sort(copy);
//            for (Node node : copy) {
//                System.out.printf("%d ",node.index);
//            }
////            System.out.println(travelled);
//        }
        for (int i = 0; i < currentNode.connected.size(); i++) {
            Node connection = currentNode.connected.get(i);
            if(travelled.contains(connection)){
                List<Node> copy = new ArrayList<>(travelled);
                Collections.sort(copy);
                graphs.add(copy);
                travelled.remove(connection);
                continue;
            }
            travelled.add(connection);
            process(nodeMap, connection, travelled);
            travelled.remove(connection);
        }
    }
    static List<List<Node>> graphs = new ArrayList<>();

    static class Node implements Comparable<Node> {
        List<Node> connected = new ArrayList<>();
        public Node(int index) {
            this.index = index;
        }

        public void connect(Node node){
            connected.add(node);
        }

        int index;

        @Override
        public String toString() {
            return "Node{" +
                    "index=" + index +
                    '}';
        }

        @SuppressWarnings("ComparatorMethodParameterNotUsed")
        @Override
        public int compareTo(Node o) {
            return  index > o.index ? 1 : -1;
        }
    }
}

