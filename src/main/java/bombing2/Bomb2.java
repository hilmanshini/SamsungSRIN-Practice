package bombing2;

import java.util.ArrayList;
import java.util.List;
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

 0 1 0 2 0
 0 2 2 2 1
 0 2 1 1 1
 1 0 1 0 0
 0 0 1 2 2
 1 1 0 0 1
 x x S x x
 It contains nothing. The highlighted(yellow) zone is the control zone.
 S is a spaceship that we need to control so that we can get maximum coins.
 Now, S's initial position will be at the center and we can only move it right or left by one
 cell or do not move. At each time, the non-highlighted part of the grid will move down by one unit.
 We can also use a bomb but only once. If we use that, all the enemies in the 5×5 region above the
 control zone will be killed. If we use a bomb at the very beginning, the grid will look like this:
 0 1 0 2 0
 0 0 0 0 1
 0 0 1 1 1
 1 0 1 0 0
 0 0 1 0 0
 1 1 0 0 1
 x x S x x

 0 1 0 2 0
 0 1 0 0 0
 0 2 2 2 1

 10
 2 2 2 2 0
 1 2 0 0 2
 0 2 0 0 0
 2 2 0 2 2
 0 2 2 2 0
 0 0 0 0 0
 1 0 0 0 2
 0 0 0 0 0
 0 2 0 2 1
 0 2 2 2 0



 */

public class Bomb2 {

    private static Scanner scanner = new Scanner(System.in);

    private static int readInt() {
        return scanner.nextInt();
    }

    public static void main(String[] args) {
        int numTest = readInt();
        for (int currentTest = 0; currentTest < numTest; currentTest++) {
            int numRows = readInt();
            int numCols = 5;
            int[][] data = new int[numCols][numRows];
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    data[col][row] = readInt();
                }
            }
            maxSoFar = 0;

                        // Check if starting position has a coin or enemy
            int initialCoins = 0;
            if (data[2][numRows - 1] == 1) {
                initialCoins = 1;
            } else if (data[2][numRows - 1] == 2) {
                // Starting on an enemy - game over immediately
                initialCoins = 0;
                maxSoFar = 0; // Force result to be 0
            }
            
            // If starting position is an enemy, game over immediately
            if (data[2][numRows - 1] == 2) {
                maxSoFar = 0;
            } else {
                // Try without using bomb
                Pair pair = new Pair(2, numRows - 1, initialCoins, -2);
                solve(data, 2, numRows - 1, initialCoins, false, pair);
                
                // Try with using bomb at the beginning
                int[][] bombedData = copyBombed(data, numRows);
                solve(bombedData, 2, numRows - 1, initialCoins, true, pair);
            }

            System.out.println("#" + (currentTest + 1) + " " + maxSoFar);
            bestPair = null;
        }
    }

    static int maxSoFar = 0;

    static class Pair implements Cloneable {
        public int value;
        List<Pair> prev = new ArrayList<>();
        int max = Integer.MIN_VALUE;
        int cointCount;

        public Pair(int x, int y, int cointCount, int value) {
            this.x = x;
            this.y = y;
            this.cointCount = cointCount;
            this.value = value;
        }

        int x, y;

        @Override
        public Pair clone() {
            return new Pair(x, y, cointCount, value);
        }
    }

    static Pair bestPair = null;

    private static void trace(Pair previous) {
        List<Pair> prevs = previous.prev;
        // Debug tracing code (commented out)
    }

        private static void solve(int[][] data, int x, int y, int i1, boolean bombUsed, Pair previous) {
        // Base case: reached the top of the grid
        if (y < 0) {
            if (i1 > maxSoFar) {
                maxSoFar = i1;
                bestPair = previous;
            }
            return;
        }
        

        
        if (i1 > maxSoFar) {
            maxSoFar = i1;
            bestPair = previous;
            trace(previous);
        }

                for (int i = -1; i <= 1; i++) {
            int col = x + i;
            int row = y - 1;

            // Check if movement is within bounds
            if (col >= 0 && col < 5 && row >= 0) {
                int value = data[col][row];

                Pair current = new Pair(col, row, i1, value);
                if (previous != null) {
                    current.prev.add(previous.clone());
                }
                
                if (value == 1) {
                    solve(data, col, row, i1 + 1, bombUsed, current);
                } else if (value == 2) {
                    // Hit an enemy - game over for this path
                    if (i1 > maxSoFar) {
                        maxSoFar = i1;
                        bestPair = previous;
                    }
                    // Don't continue this path - but we can still try other movements
                } else if (value == 0) {
                    solve(data, col, row, i1, bombUsed, current);
                }
            }
        }
    }

    private static int[][] copyBombed(int[][] data, int rowStarting) {
        int numCols = data.length;
        int numRows = data[0].length;
        int[][] copied = new int[numCols][numRows];

        // Copy the data
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                copied[col][row] = data[col][row];
            }
        }

        // Clear all enemies in the entire grid
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (copied[col][row] == 2) {
                    copied[col][row] = 0;
                }
            }
        }

        return copied;
    }

    private static boolean inBounds(int[][] data, int col, int row) {
        if (col < 0 || row < 0) return false;
        if (col >= data.length) {
            return false;
        }
        if (row >= data[0].length) {
            return false;
        }
        return true;
    }
}
/***
 5
 7
 1 2 0 0 1
 2 0 0 1 0
 0 1 2 0 1
 1 0 0 2 1
 0 2 1 0 1
 0 1 2 2 2
 1 0 1 1 0
 5
 1 1 0 0 0
 1 2 2 2 1
 1 1 2 2 1
 2 2 2 1 2
 2 2 0 2 0
 6
 2 2 2 2 2
 0 0 0 0 0
 0 0 2 0 0
 2 0 0 0 2
 0 0 0 0 0
 1 2 2 2 1
 10
 2 2 2 2 0
 1 2 0 0 2
 0 2 0 0 0
 2 2 0 2 2
 0 2 2 2 0
 0 0 0 0 0
 1 0 0 0 2
 0 0 0 0 0
 0 2 0 2 1
 0 2 2 2 0
 12
 2 2 0 2 2
 0 1 0 2 1
 0 2 0 1 0
 2 1 2 1 0
 0 2 2 1 2
 0 1 2 2 2
 0 2 1 0 2
 2 0 1 1 2
 2 1 1 0 1
 0 2 2 0 0
 2 0 1 2 2
 2 2 1 2 1

 #1 6
 #2 3
 #3 0
 #4 1
 #5 9
 */
