package sum_nodes_ink;

public class Sol {
    public static void main(String[] args) {
//        int n =
//                (0
//        (5
//            (16()())
//            (4()(9()())))
//        (7
//            (1()())
//            (3()())
//        )
//        );
//        String x = "(0441(5(16()())(4()(9()())))(7(1()())(3()())))";
//        String x = "(16(9()())(9()()))";
        String x = "(16(5()())())";
        /*
        (16
            (5()())
            ()
        )
         */
//

        parseNode(x,0);
//        System.out.println(n);
        ;
    }

//    static

    static void parseNode(String str,int charProcess) {
//        String x = "(16()())";
        int i = 0;
        String x = str.substring(charProcess);
        int level = 0;
        int direction = 0;
        StringBuilder sb = new StringBuilder();
        Node parent = new Node(null);
        Node node = new Node(parent);
        while (i < x.length()) {
            Character currentChar = x.charAt(i);
            String monitor = x.substring(i);
            if(Character.isDigit(currentChar)){
                sb.append(currentChar);
            } else if(currentChar == '('){

                if(sb.length() != 0){
                    parent.value = Integer.parseInt(sb.toString());
                    sb = new StringBuilder();
                }
                node = new Node(parent);
                parent = node;
                System.out.println();
            } else if(currentChar == ')'){
                if(direction == 1){
                    direction = 0;
                    parent.right = node;

                    level--;
                } else {
                    direction = 1;
                    parent.left = node;
                }
            }
            i++;
        }
        System.out.println(parent);
    }
    static class Node{
        int level;
        int value;
        Node left,right,parent;

        public Node(Node parent) {
            this.parent = parent;
        }
    }


}

