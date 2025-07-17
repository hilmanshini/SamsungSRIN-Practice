package bombing2;

import java.util.Scanner;

/***
 1
 7
 1 2 0 0 1
 2 0 0 1 0
 0 1 2 0 1
 1 0 0 2 1
 0 2 1 0 1
 0 1 2 2 2
 1 0 1 1 0
 */
public class Bommmb {

    private static Scanner scanner = new Scanner(System.in);

    private static int readInt() {
        return scanner.nextInt();
    }

    public static void main(String[] args) {
        int numTest = readInt();

        for (int currentTest = 1; currentTest <= numTest; currentTest++) {
            int numRows = readInt();
            int numCols = 5;

            // Read the grid data - store as data[row][col] as per problem description
            int[][] data = new int[numRows][numCols];
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    data[row][col] = readInt();
                }
            }
            solve(data,numRows-1,2,false,0,0,numCols);
            System.out.println(max);
            max = Integer.MIN_VALUE;
        }
    }

    private static int max = Integer.MIN_VALUE;

    private static void solve(int[][] data, int x, int y, boolean bombUsed, int coinCount,int bombCount, int numCols) {

        if(coinCount > max){
            max = coinCount;
        }
        for(int i = -1 ; i <= 1;i++){
            int row = x;
            int col = y+i;
            if(row < 0 || row >= data.length || col < 0 || col >= numCols){
                continue;
            }
            int value = data[row][col];
            if(value == 0 || value == 1){
//                if(value == 1) {
//                    System.out.println(" "+x +" "+y);
//                }
                solve(data,row-1,col,bombUsed,coinCount+value,bombUsed?bombCount+1:bombCount, numCols);
            } else if(value == 2){
                if(bombCount == 5){
                    if(!bombUsed){
                        solve(data,col,row-1,true,coinCount,bombCount+1,numCols);
                    }
                } else {
                    solve(data,col,row-1,bombUsed,coinCount,bombCount+1,numCols);
                }
            }
        }

    }

}
