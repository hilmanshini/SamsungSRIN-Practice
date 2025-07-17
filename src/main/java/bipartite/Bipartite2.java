package bipartite;

import java.util.*;

import static utils.Reader.readInt;

public class Bipartite2 {
    /***
     7
     1 2
     2 3
     2 5
     5 6
     3 4
     4 6
     6 7



     1 -> 2 -> 3 -> 4
          V         v
          5 ----->  6 -> 7

     6
     1 2
     2 3
     2 5
     3 4
     4 6
     6 7

     1 -> 2  -> 3
          v     v
          5     4 -> 6 -> 7


     // bipartite
    3
     7
     1 2
     2 3
     3 4
     4 5
     5 6
     6 7
     1 7
     1 -> 2 ------> 3
     v              v
     7 <- 6 <- 5 <- 4

     7
     1 2
     2 3
     3 4
     4 5
     5 6
     6 7
     2 6

     7
     1 2
     1 3
     2 4
     3 5
     4 6
     5 6
     6 7



     * @param args
     */
    public static void main(String[] args) {
        int test = readInt();
        for (int r = 0; r < test; r++) {
            int num = readInt();
            Map<Integer, Node> nodeMap = new HashMap<>();
            for (int i = 0; i < num; i++) {
                int leftIndex = readInt();
                int rightIndex = readInt();
                Node left, right;
                if (nodeMap.containsKey(leftIndex)) {
                    left = nodeMap.get(leftIndex);
                } else {
                    left = new Node(leftIndex);
                    nodeMap.put(leftIndex, left);
                }
                if (nodeMap.containsKey(rightIndex)) {
                    right = nodeMap.get(rightIndex);
                } else {
                    right = new Node(rightIndex);
                    nodeMap.put(rightIndex, right);
                }
                left.connect(right);
            }
            List<Cycle> cycles = new ArrayList<>();
            List<Node> travlled = new ArrayList<>();

            List<Node> nodes = new ArrayList<>(nodeMap.values());
            travlled.add(nodes.get(0));
            nodes.get(0).group = 1;
            boolean bi = solve(nodes.get(0), cycles, travlled);
            System.out.println(bi);
            findCycle();
        }

    }

    private static void findCycle() {
    }

    static List<List<Node>> paths = new ArrayList<>();

    private static boolean solve(Node node, List<Cycle> cycles, List<Node> travlled) {
//        if(node.connection.isEmpty()){
//            List<Node> newPaths = new ArrayList<>(travlled);
//            paths.add(newPaths);
//        }
        boolean result = true;
        for (Node conntected : node.connection) {
            travlled.add(conntected);
//            boolean assigned = conntected.assignGroup(node);
            boolean assigned = conntected.assignGroup(node);
            if(!assigned){
                return  false;
            }
            assigned = solve(conntected,cycles,travlled);
            if(!assigned){
                return  false;
            }
//            if(){
//                return  solve(conntected, cycles, travlled);
//            } else {
//                return  false;
//            }

            travlled.remove(travlled.size()-1);
        }
        return result;
    }

    static class Cycle {
        List<Node> nodes = new ArrayList<>();
    }

    static class Node {
        public Node(int index) {
            this.index = index;
        }

        int index;
        int group = 0;
        List<Node> connection = new ArrayList<>();

        public void connect(Node node) {
            if (!connection.contains(node)) {
                connection.add(node);
            }
        }

        public boolean assignGroup(Node previous){
            if(group == 0){
                group = previous.group == 1 ? 2 : 1;
                return  true;
            } else {
                int supposeGroup = previous.group == 1 ? 2 : 1;
                return  supposeGroup == group;
            }
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
    }
}
