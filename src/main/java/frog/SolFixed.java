package frog;

//package doctor;

import java.util.*;
/*
5
0 1 1 0 1
1 0 0 1 0
1 0 0 0 1
0 1 0 1 1
1 0 1 1 1
4 4 2 4

 */
public class SolFixed {
    public static void main(String[] args) {
        Area area = new Area();
        area.read();
        Set<Point> visited = new HashSet<>();
        visited.add(area.start);
        travel(area, area.start, area.end, visited, 0);
        System.out.println(min);
    }
    static int min = Integer.MAX_VALUE;

    static void travel(Area area, Point start, Point dest, Set<Point> visited, int cost) {
        // Move left as far as possible
        int y = start.y;
        while (y - 1 >= 0 && area.data[start.x][y - 1] == 1 && !visited.contains(new Point(start.x, y - 1))) {
            Point next = new Point(start.x, y - 1);
            visited.add(next);
            if (next.equals(dest)) {
                System.out.println(visited);
                min = Math.min(min, cost);
            } else {
                travel(area, next, dest, visited, cost);
            }
            visited.remove(next);
            y--;
        }
        // Move right as far as possible
        y = start.y;
        while (y + 1 < area.data[0].length && area.data[start.x][y + 1] == 1 && !visited.contains(new Point(start.x, y + 1))) {
            Point next = new Point(start.x, y + 1);
            visited.add(next);
            if (next.equals(dest)) {
                System.out.println(visited);
                min = Math.min(min, cost);
            } else {
                travel(area, next, dest, visited, cost);
            }
            visited.remove(next);
            y++;
        }
        // Jump vertically (up and down) to any other 1 in the same column
        for (int x = 0; x < area.data.length; x++) {
            if (x != start.x && area.data[x][start.y] == 1) {
                Point next = new Point(x, start.y);
                if (!visited.contains(next)) {
                    visited.add(next);
                    if (next.equals(dest)) {
                        System.out.println(visited);
                        min = Math.min(min, cost + 1);
                    } else {
                        travel(area, next, dest, visited, cost + 1);
                    }
                    visited.remove(next);
                }
            }
        }
    }

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Point)) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return x + ":" + y;
        }
    }

    static class Area {
        Point start, end;
        int[][] data;

        void read() {
            Scanner scanner = new Scanner(System.in);
            int size = scanner.nextInt();
            data = new int[size][size];
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    data[row][col] = scanner.nextInt();
                }
            }
            end = new Point(scanner.nextInt(), scanner.nextInt());
            start = new Point(scanner.nextInt(), scanner.nextInt());
        }

        public boolean canVisit(Point point) {
            return data[point.x][point.y] == 1;
        }
    }

    static class Move {
        int x;
        int y;
        int cost;

        public Move(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }
}