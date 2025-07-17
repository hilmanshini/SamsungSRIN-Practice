package bombing2;

import static utils.Reader.readInt;

public class Bomb3 {
    /**
     1
     7
     1 2 0 0 1
     2 0 0 1 0
     0 1 2 0 1
     1 0 0 2 1
     0 2 1 0 1
     0 1 2 2 2
     1 0 1 1 0
     * @param args
     */
    public static void main(String[] args) {
        int numTest = readInt();
        int numCols = 5;
        for (int i = 0; i < numTest; i++) {
            int numRows = readInt();
            int[][] data = new int[numCols][numRows];
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                        data[col][row] = readInt();
                }
            }
            solve(data,(numCols/2),numRows-1,0,0,false);
            System.out.println(coinMax);
            coinMax = 0;
        }
    }
    static  int coinMax = 0;

    private static void solve(int[][] data,int x,int y,int coinFound,int bombFound,boolean bombUsed) {
        if(coinFound > coinMax){
            coinMax = coinFound;
        }
        for(int i = -1 ; i <= 1;i++){
            int col = x+i;
            int row = y;
            if(inBounds(data,col,row)){
                int value = data[col][row];
                if(value == 0){
                    solve(data,col,row-1,coinFound,bombFound,bombUsed);
                } else if(value == 1){
                    solve(data,col,row-1,coinFound+1,bombFound,bombUsed);
                } else if(value == 2){
                    if(bombFound == 5){
                        if(!bombUsed){
                            solve(data,col,row-1,coinFound,bombFound+1,true);
                        } else {
                            break;
                        }
//                        solve(data,col,row-1,coinFound+1,bombFound,bombUsed);
                    } else {
                        solve(data,col,row-1,coinFound,bombFound+1,bombUsed);
                    }
                }
            }
        }
    }

    private static boolean inBounds(int[][] data, int col, int row) {
        return col >= 0 && col < data.length && row >= 0 && row < data[0].length;
    }
}
