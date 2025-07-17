package bombing;

import java.util.ArrayList;
import java.util.*;
import java.util.Scanner;

public class Sol {
    static int n, maxi;
    static int[][] mat = new int[20][6];
    static List<int[]> bestPath = new ArrayList<>();
    static List<int[]> curPath = new ArrayList<>();

    static void bomb(int x, int y, int cost, boolean use, int count) {
        curPath.add(new int[]{x, y});
        if (cost > maxi) {
            maxi = cost;
            bestPath = new ArrayList<>(curPath);
        }
        for (int i = -1; i <= 1; i++) {
            int row = x - 1, col = y + i;
            if (row >= 1 && row <= n && col >= 1 && col <= 5) {
                if ((mat[row][col] == 0 || mat[row][col] == 1) && use) {
                    bomb(row, col, cost + mat[row][col], true, count + 1);
                } else if ((mat[row][col] == 0 || mat[row][col] == 1) && !use) {
                    bomb(row, col, cost + mat[row][col], false, count);
                } else if (mat[row][col] == 2 && use) {
                    if (count >= 5) break;
                    bomb(row, col, cost, true, count + 1);
                } else if (mat[row][col] == 2 && !use) {
                    bomb(row, col, cost, true, count);
                }
            }
        }
        curPath.remove(curPath.size() - 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int tt = sc.nextInt(), id = 1;
        while (tt-- > 0) {
            maxi = Integer.MIN_VALUE;
            n = sc.nextInt();
            for (int i = 1; i <= n; i++)
                for (int j = 1; j <= 5; j++)
                    mat[i][j] = sc.nextInt();
            bestPath.clear();
            curPath.clear();
            bomb(n + 1, 3, 0, false, 0);
            System.out.println("#" + (id++) + " " + maxi);
            System.out.print("Best path: ");
            for (int[] coord : bestPath) {
                System.out.print("(" + coord[0] + "," + coord[1] + ") ");
            }
            System.out.println();
        }
    }


}


