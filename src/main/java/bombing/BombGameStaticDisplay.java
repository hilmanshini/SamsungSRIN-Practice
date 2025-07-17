package bombing;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class BombGameStaticDisplay extends JFrame {
    static int n, maxi;
    static int[][] mat = new int[20][6];
    static List<int[]> bestPath = new ArrayList<>();
    static List<int[]> curPath = new ArrayList<>();

    public static int countBombUsed(List<int[]> path) {
        // Bomb is used if there is a transition from use==false to use==true
        // But since your path only stores coordinates, we infer bomb usage by the path logic:
        // The bomb can only be used once, and only when stepping on a 2 or after a 2.
        // So, we can count how many times the path steps on a 2 and how many times the bomb is used.
        // But with your logic, bomb is used at most once, so we can just check if the path could have stepped on a 2 without breaking.
        // Instead, let's count how many times the path could have used the bomb (i.e., when stepping on a 2, not at the first step).
        int bombUsed = 0;
        boolean bombActive = false;
        int count = 0;
        int prevX = -1, prevY = -1;
        for (int i = 1; i < path.size(); i++) {
            int[] prev = path.get(i - 1);
            int[] curr = path.get(i);
            int row = curr[0], col = curr[1];
            if (mat[row][col] == 2) {
                count++;
                // If previous cell was not a 2, and this is the first time, count as bomb used
                if (!bombActive) {
                    bombUsed = 1;
                    bombActive = true;
                }
            }
        }
        return bombUsed;
    }

    public static int countStepOn2(List<int[]> path) {
        int count = 0;
        for (int[] step : path) {
            int row = step[0], col = step[1];
            if (mat[row][col] == 2) count++;
        }
        return count;
    }

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

    public BombGameStaticDisplay(int n, int[][] mat, List<int[]> bestPath, int maxScore, int bombUsed, int stepOn2) {
        setTitle("Bomb Game Best Path (Max Score: " + maxScore + ")");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(new BombPanel(n, mat, bestPath, bombUsed, stepOn2));
        pack();
        setLocationRelativeTo(null);
    }

    static class BombPanel extends JPanel {
        int n, cellSize = 50, cols = 5;
        int[][] mat;
        List<int[]> bestPath;
        int bombUsed, stepOn2;

        BombPanel(int n, int[][] mat, List<int[]> bestPath, int bombUsed, int stepOn2) {
            this.n = n;
            this.mat = mat;
            this.bestPath = bestPath;
            this.bombUsed = bombUsed;
            this.stepOn2 = stepOn2;
            setPreferredSize(new Dimension(cols * cellSize + 200, n * cellSize));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw grid
            for (int r = 1; r <= n; r++) {
                for (int c = 1; c <= cols; c++) {
                    int x = (c - 1) * cellSize;
                    int y = (r - 1) * cellSize;
                    if (mat[r][c] == 0) g.setColor(Color.LIGHT_GRAY);
                    else if (mat[r][c] == 1) g.setColor(Color.GREEN);
                    else if (mat[r][c] == 2) g.setColor(Color.RED);
                    g.fillRect(x, y, cellSize, cellSize);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, cellSize, cellSize);
                    g.drawString(String.valueOf(mat[r][c]), x + 20, y + 30);
                }
            }
            // Draw best path
            for (int i = 0; i < bestPath.size(); i++) {
                int[] step = bestPath.get(i);
                int r = step[0], c = step[1];
                int x = (c - 1) * cellSize;
                int y = (r - 1) * cellSize;
                g.setColor(Color.ORANGE);
                g.fillOval(x + 10, y + 10, 30, 30);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString("" + i, x + 20, y + 30);
            }
            // Draw legend and score
            int legendX = cols * cellSize + 10;
            int legendY = 30;
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Best Path: Orange", legendX, legendY);
            g.drawString("Bomb Used: " + bombUsed, legendX, legendY + 30);
            g.drawString("Step on '2': " + stepOn2, legendX, legendY + 60);
        }
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

            int bombUsed = 0;
            boolean bombActive = false;
            for (int i = 1; i < bestPath.size(); i++) {
                int[] prev = bestPath.get(i - 1);
                int[] curr = bestPath.get(i);
                int row = curr[0], col = curr[1];
                if (mat[row][col] == 2 && !bombActive) {
                    bombUsed = 1;
                    bombActive = true;
                }
            }
            int stepOn2 = 0;
            for (int[] step : bestPath) {
                int row = step[0], col = step[1];
                if (mat[row][col] == 2) stepOn2++;
            }

            BombGameStaticDisplay frame = new BombGameStaticDisplay(n, mat, bestPath, maxi, bombUsed, stepOn2);
            frame.setVisible(true);

            // Wait for the user to close the window before continuing
            final Object lock = new Object();
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    synchronized (lock) {
                        lock.notify();
                    }
                }
            });
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
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
 2 2 2 2 2
 2 0 1 2 2
 2 2 1 2 1

 */