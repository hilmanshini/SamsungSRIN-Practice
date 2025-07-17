package frog;

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
public class Sol {
    public static void main(String[] args) {

        List<Move> moves = new ArrayList<>();
        moves.add(new Move(1,0,1));
        moves.add(new Move(-1,0,1));
        moves.add(new Move(0,1,0));
        moves.add(new Move(0,-1,0));
        Area area = new Area();
        area.read();
        List<Point> visited = new ArrayList<>();
        visited.add(area.start);
        travel(area,moves,area.start,area.end,visited,0);
        System.out.println(min);
    }
    static  List<List<Point>> paths = new ArrayList<>();
    static  int min = Integer.MAX_VALUE;

    static  void travel(Area area, List<Move> moves, Point start,Point dest, List<Point> visited,int cost){
        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            Optional<Point> next = area.move(start,move);
            if(next.isEmpty() || visited.contains(next.get()) || !area.canVisit(next.get())){
                continue;
            }
            visited.add(next.get());
            if(next.get().equals(dest)){
                min = Math.min(min,cost);
                continue;
            }
            travel(area,moves,next.get(),dest,visited,cost+move.cost);
        }
    }

    static class Point{
        int x,y;

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
            return
                    x+":"+ y ;
        }
    }

    static class Area{

        Point start,end;
        int[][] data;

        void  read(){
            Scanner scanner = new Scanner(System.in);
            int size = scanner.nextInt();
            data = new int[size][size];
            for (int col = 0; col < size; col++) {
                for (int row = 0; row < size; row++) {
                    data[row][col] = scanner.nextInt();
                }
            }

            end = new Point(scanner.nextInt(),scanner.nextInt());
            start = new Point(scanner.nextInt(),scanner.nextInt());
        }

        Optional<Point> move(Point current, Move move){
            Point next = new Point(current.x + move.x ,current.y + move.y);
            if(next.x >= 0 && next.x < data.length && next.y >= 0 && next.y < data[0].length){
                return Optional.of(next);
            }
            return Optional.empty();
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
