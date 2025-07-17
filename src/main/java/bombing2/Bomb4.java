package bombing2;

import static utils.Reader.readInt;

public class Bomb4 {
    public static void main(String[] args) {
        int numTest = readInt();
        for (int test = 0; test < numTest; test++) {
            int numRows = readInt();
            int numCols = 5;
            int[][] data = new int[numCols][numRows];
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    data[col][row] = readInt();
                }
            }
            solve(data, (numCols / 2), numRows - 1, 0, 0, false);
            System.out.println(maxValue);
            maxValue = 0;
        }
    }

    private static int maxValue = 0;

    private static void solve(int[][] data, int x, int y, int coinCount, int bombCount, boolean bombUsed) {
        if (maxValue < coinCount) {
            maxValue = coinCount;
        }
        for (int i = -1; i <= 1; i++) {
            int row = y;
            int col = x + i;
            if (inBounds(data, col, row)) {
                int value = data[col][row];
                if (value == 0) {
                    solve(data, col, row - 1, coinCount, bombUsed?bombCount+1:bombCount, bombUsed);
                } else if (value == 1) {
                    solve(data, col, row - 1, coinCount + 1, bombUsed?bombCount+1:bombCount, bombUsed);
                } else if (value == 2) {
                    if (bombUsed) {
                        if (bombCount >= 5) {
                            continue;
                        }
                        solve(data, col, row - 1, coinCount, bombCount + 1, true);
                    } else {
                        solve(data, col, row - 1, coinCount, 0, true);
                    }
                }
            }
        }
    }

    private static boolean inBounds(int[][] data, int col, int row) {
        return  col>= 0 && col < data.length && row >= 0 && row < data[0].length;
    }
}
