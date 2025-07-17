import java.util.HashMap;
import java.util.Map;

import static utils.Reader.readInt;

/***
 7 6
 1 2
 2 3
 2 5
 3 4
 4 6
 6 7
 */
public class Bipartite {
    public static void main(String[] args) {
        Map<String ,String > s = new HashMap<>();
        Map<Node,Node> result = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            Node key = new Node(readInt());
            Node value = new Node(readInt());
            result.put(key,value);
        }


    }

    static class Node{
        int value;

        public Node(int value) {
            this.value = value;
        }

        int group;

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof  Node){
                Node node = (Node)obj;
                return  node.group == group && value == node.value;
            }
            return super.equals(obj);
        }
    }
}
