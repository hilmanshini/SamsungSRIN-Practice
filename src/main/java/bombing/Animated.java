package bombing;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Animated extends JFrame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] mat = new int[n + 2][6]; // 1-based index
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= 5; j++) {
                mat[i][j] = scanner.nextInt();
            }
        }
        BombGame game = new BombGame(n, mat);
        List<BombGame.Step> steps = game.getBestPath();

        SwingUtilities.invokeLater(() -> {
            BombPanel panel = new BombPanel(n, mat, steps, game.maxScore, game.bombUsed);
            JFrame frame = new Animated();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            panel.startAnimation();
        });
    }

    static class BombPanel extends JPanel {
        private final int n, cols = 5, cellSize = 50, legendWidth = 200;
        private final int[][] mat;
        private final List<BombGame.Step> steps;
        private final int maxScore, bombUsed;
        private int stepIndex = 0;

        BombPanel(int n, int[][] mat, List<BombGame.Step> steps, int maxScore, int bombUsed) {
            this.n = n;
            this.mat = mat;
            this.steps = steps;
            this.maxScore = maxScore;
            this.bombUsed = bombUsed;
            setPreferredSize(new Dimension(cols * cellSize + legendWidth, n * cellSize));
        }

        public void startAnimation() {
            Timer timer = new Timer(2000, e -> {
                stepIndex++;
                if (stepIndex >= steps.size()) {
                    ((Timer) e.getSource()).stop();
                }
                repaint();
            });
            timer.start();
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
            // Draw path up to current step
            for (int i = 0; i <= stepIndex && i < steps.size(); i++) {
                BombGame.Step s = steps.get(i);
                if (s.row >= 1 && s.row <= n && s.col >= 1 && s.col <= cols) {
                    int x = (s.col - 1) * cellSize;
                    int y = (s.row - 1) * cellSize;
                    g.setColor(s.usedBomb>0 ? Color.MAGENTA : Color.BLUE);
                    g.fillOval(x + 10, y + 10, 30, 30);
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial", Font.BOLD, 18));
                    g.drawString("X", x + 17, y + 33);
                }
            }
            // Draw current position
            if (stepIndex < steps.size()) {
                BombGame.Step s = steps.get(stepIndex);
                if (s.row >= 1 && s.row <= n && s.col >= 1 && s.col <= cols) {
                    int x = (s.col - 1) * cellSize;
                    int y = (s.row - 1) * cellSize;
                    g.setColor(Color.YELLOW);
                    g.fillOval(x + 15, y + 15, 20, 20);
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial", Font.BOLD, 18));
                    g.drawString("O", x + 20, y + 33);
                }
            }
            // Draw only maxScore and bomb used
            int legendX = cols * cellSize + 10;
            int legendY = 50;
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString("Max Score: " + maxScore, legendX, legendY);
            g.drawString("Bomb Used: " + bombUsed, legendX, legendY + 40);
        }
    }

    static class BombGame {
        private final int n;
        private final int[][] mat;
        int maxScore = 0;
        int bombUsed = 0;

        public BombGame(int n, int[][] mat) {
            this.n = n;
            this.mat = mat;
        }

        public List<Step> getBestPath() {
            // dp[row][col][usedBomb] = max coins
            int[][][] dp = new int[n + 2][6][2];
            Step[][][] parent = new Step[n + 2][6][2];
            for (int[][] arr : dp) for (int[] a : arr) Arrays.fill(a, -1);

            dp[n][3][0] = 0; // Start at bottom row, center, bomb not used

            for (int row = n; row >= 1; row--) {
                for (int col = 1; col <= 5; col++) {
                    for (int usedBomb = 0; usedBomb <= 1; usedBomb++) {
                        if (dp[row][col][usedBomb] == -1) continue;
                        for (int d = -1; d <= 1; d++) {
                            int newCol = col + d;
                            if (newCol < 1 || newCol > 5) continue;
                            // Try both using and not using bomb if not used yet
                            for (int bombNow = 0; bombNow <= (usedBomb == 0 ? 1 : 0); bombNow++) {
                                int nextUsedBomb = usedBomb | bombNow;
                                int cell = mat[row - 1][newCol];
                                if (bombNow == 1 && cell == 2) cell = 0; // bomb clears enemy
                                if (cell == 2 && nextUsedBomb == 0) continue; // hit enemy, path ends
                                int coins = dp[row][col][usedBomb] + (cell == 1 ? 1 : 0);
                                if (coins > dp[row - 1][newCol][nextUsedBomb]) {
                                    dp[row - 1][newCol][nextUsedBomb] = coins;
                                    parent[row - 1][newCol][nextUsedBomb] = new Step(row, col, usedBomb, coins, 0);
                                }
                            }
                        }
                    }
                }
            }

            // Find the best end state
            int bestCoins = -1, bestRow = 0, bestCol = 0, bestBomb = 0;
            for (int col = 1; col <= 5; col++) {
                for (int usedBomb = 0; usedBomb <= 1; usedBomb++) {
                    if (dp[0][col][usedBomb] > bestCoins) {
                        bestCoins = dp[0][col][usedBomb];
                        bestRow = 0;
                        bestCol = col;
                        bestBomb = usedBomb;
                    }
                }
            }
            maxScore = bestCoins;
            bombUsed = bestBomb;

            // Reconstruct path
            List<Step> path = new ArrayList<>();
            Step cur = parent[bestRow][bestCol][bestBomb];
            int row = bestRow, col = bestCol, usedBomb = bestBomb;
            while (cur != null) {
                path.add(new Step(row, col, usedBomb , 0, cur.coins));
                int prevRow = cur.row;
                int prevCol = cur.col;
                int prevBomb = cur.usedBomb;
                cur = parent[prevRow][prevCol][prevBomb];
                row = prevRow;
                col = prevCol;
                usedBomb = prevBomb;
            }
            Collections.reverse(path);
            return path;
        }

        static class Step {
            int row, col;
            int usedBomb;
            int count;
            int coins;
            Step(int row, int col, int usedBomb, int count, int coins) {
                this.row = row;
                this.col = col;
                this.usedBomb = usedBomb;
                this.count = count;
                this.coins = coins;
            }
        }
    }
}