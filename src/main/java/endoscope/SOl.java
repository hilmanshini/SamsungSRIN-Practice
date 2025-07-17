package endoscope;

import java.util.*;

import static utils.Reader.readInt;

public class SOl {
    public static void main(String[] args) {
        int numTest = readInt();
        for (int test = 0; test < numTest; test++) {
            int rows = readInt();
            int cols = readInt();
            int startx = readInt();
            int starty = readInt();
            int step = readInt();
            Pipe[][] data = new Pipe[rows][cols];
            Map<Integer, Pipe> pipeMap = new HashMap<>();
            pipeMap.put(1, new Pipe(1, Up.instance, Down.instance, Left.instance, Right.instance));
            pipeMap.put(2, new Pipe(1, Up.instance, Down.instance));
            pipeMap.put(3, new Pipe(1, Left.instance, Right.instance));
            pipeMap.put(4, new Pipe(1, Up.instance, Right.instance));
            pipeMap.put(5, new Pipe(1, Down.instance, Right.instance));
            pipeMap.put(6, new Pipe(1, Down.instance, Left.instance));
            pipeMap.put(7, new Pipe(1, Up.instance, Left.instance));
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    Pipe pipe = pipeMap.get(readInt());
                    data[row][col] = pipe;
                }
            }
            new StreamLine(data, startx, starty, step, pipeMap).simulate(new Point(startx,starty),Arrays.asList(
                    Up.instance,
                    Down.instance,
                    Left.instance,
                    Right.instance
            ));
        }
    }

    static class Pipe {
        List<Move> allowedMoves = new ArrayList<>();
        int code;

        public Pipe(int code, Move... allMoves) {
            this.code = code;
            this.allowedMoves = new ArrayList<>(List.of(allMoves));
        }

    }


    static abstract  class Move {
        int x;
        abstract  Move getOpposite();

        public Move(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int y;

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Move)) return false;
            Move move = (Move) o;
            return x == move.x && y == move.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    static class Up extends Move {
        static Up instance = new Up();

        public Up() {
            super(0, -1);
        }

        @Override
        Move getOpposite() {
            return  Down.instance;
        }
    }

    static class Down extends Move {
        static Down instance = new Down();

        public Down() {
            super(0, 1);
        }
        @Override
        Move getOpposite() {
            return  Up.instance;
        }
    }

    static class Left extends Move {

        static Left instance = new Left();

        public Left() {
            super(-1, 0);
        }
        @Override
        Move getOpposite() {
            return  Right.instance;
        }
    }

    static class Right extends Move {
        static Right instance = new Right();

        public Right() {
            super(1, 0);
        }
        @Override
        Move getOpposite() {
            return  Left.instance;
        }
    }

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Point move(Move move) {
            return new Point(x + move.x, y + move.y);
        }
    }

    static class StreamLine {
        Pipe[][] data;
        Point point;
        int maxPipe;
        Map<Integer, Pipe> pipeMap;
        List<Move> moves = Arrays.asList(
                Up.instance,
                Down.instance,
                Left.instance,
                Right.instance
        );

        public StreamLine(Pipe[][] data, int startx, int startY, int maxPipe, Map<Integer, Pipe> pipeMap) {
            this.data = data;
            this.point = new Point(startx, startY);
            this.maxPipe = maxPipe;
            this.pipeMap = pipeMap;
        }

        void simulate(Point current,List<Move> moves) {
            for (Move move : moves) {
                Point movedPoint = current.move(move);
                if (!validPoint(movedPoint)) {
                    continue;
                }
                Pipe pipe = getPipe(movedPoint);
                if (!pipe.allowedMoves.contains(move.getOpposite())) {
                    continue;
                }
                simulate(movedPoint, pipe.allowedMoves);
            }
        }
        class Stream{
            Point point;
            Move move;
        }
        Pipe getPipe(Point point){
            return  data[point.x][point.y];
        }

        boolean validPoint(Point movedPoint) {
            return movedPoint.x > 0 && movedPoint.x < data.length && movedPoint.y > 0 && movedPoint.y < data[0].length && data[movedPoint.x] != null && data[movedPoint.y] != null;
        }


    }
}
