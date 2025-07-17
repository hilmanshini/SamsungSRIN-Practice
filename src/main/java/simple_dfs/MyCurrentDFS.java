package simple_dfs;

import java.util.*;

import static utils.Reader.readInt;

public class MyCurrentDFS {

    /***
     1 2
     1 3
     2 4
     3 4
     * @param args
     */
    public static void main(String[] args) {
        int nodes = 4;
        List<Node> nodeList = new ArrayList<>();
        int edges = 4;
        for (int i = 0; i < nodes; i++) {
            nodeList.add(i,new Node(i+1));
        }
        for (int i = 0; i < edges; i++) {
            Node from = nodeList.get(readInt()-1);
            Node to = nodeList.get(readInt()-1);
            from.connected.add(to);
        }
        List<Node> travelled = new ArrayList<>();
        travelled.add(nodeList.get(0));
        solve(nodeList.get(0),travelled);
    }

    private static void solve(Node nodeMap, List<Node> travelled) {
        if(!travelled.isEmpty()){
            for (Node node : travelled) {
                System.out.printf("%d => ",node.index);
            }
            System.out.println();
        }
        for (Node node : nodeMap.connected) {
            travelled.add(node);
            solve(node, travelled);
            travelled.remove(travelled.size()-1);
        }
    }
}

class Node{
    int index;

    public Node(int index) {
        this.index = index;
    }

    List<Node> connected = new ArrayList<>();

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