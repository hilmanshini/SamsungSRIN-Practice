package bipartite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.Reader.readInt;

/***
 7 7
 1 2
 2 3
 2 5
 5 6
 3 4
 4 6
 6 7
 */
public class Test {
    public static void main(String[] args) {
        Map<Integer, Node> nodes = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            int left = readInt();
            int right = readInt();
            Node leftNode;
            if (!nodes.containsKey(left)) {
                leftNode = new Node(left);
                nodes.put(left, leftNode);
            } else {
                leftNode = nodes.get(left);
            }

            Node rightNode;
            if (!nodes.containsKey(right)) {
                rightNode = new Node(right);
                nodes.put(right, rightNode);
            } else {
                rightNode = nodes.get(right);
            }
            leftNode.connected.add(rightNode);
            rightNode.connected.add(leftNode);
        }
        System.out.println(isBipartite(nodes));
        System.out.println("ok");
    }

    static  boolean isBipartite(Map<Integer, Node> nodes) {
        for (Node node : nodes.values()) {
            if (!assignGroup(node, 0)) {
                return false;
            }
        }
        return true;
    }

    static  boolean assignGroup(Node node,int group) {
//        color.put(node, c);

        node.group = group;
        for (Node neighbor : node.connected) {
            if (neighbor.group == -1) {
                if (!assignGroup(neighbor, 1 - group)) return false;
            } else if (neighbor.group == group) {
                return false; // Same group as neighbor, not bipartite
            }
        }
        return true;
    }

}

class Node {
    int group = -1;
    public Node(int index) {
        this.index = index;
    }

    int index;
    List<Node> connected = new ArrayList<>();
}
