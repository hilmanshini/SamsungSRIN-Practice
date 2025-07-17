package bombing4;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static utils.Reader.readInt;

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
 */

@SuppressWarnings("ClassEscapesDefinedScope")
public class Area {

    private static final int NUM_COLS = 5;
    Map<Coordinate, Terrain> terrainMap = new HashMap<>();
    int numRows = -1;

    public void readArea() {
        numRows = readInt();
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                int value = readInt();
                Terrain terrain;
                Coordinate coordinate = new Coordinate(col, row);
                if (value == 0) {
                    terrain = new Terrain.EmptyTerrain(coordinate);
                } else if (value == 1) {
                    terrain = new Terrain.CoinTerrain(coordinate);
                } else if (value == 2) {
                    terrain = new Terrain.EnemyTerrain(coordinate);
                } else {
                    terrain = new Terrain.UnknownTerrain(coordinate);
                }
                terrainMap.put(coordinate, terrain);
            }
        }
        for (int i = 0; i < NUM_COLS; i++) {
            terrainMap.put(new Coordinate(i, numRows), new Terrain.UnknownTerrain(new Coordinate(i, numRows)));
        }
    }

    public Terrain getStartingTerrain() {
        int x = NUM_COLS / 2;
        int y = numRows;
        return terrainMap.get(new Coordinate(x, y));
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isInBounds(Coordinate coordinate) {
        return coordinate.x >= 0 && coordinate.x < NUM_COLS && coordinate.y >= 0 && coordinate.y < numRows;
    }

    public Optional<Terrain> getTerrain(int x, int y) {
        if (!isInBounds(new Coordinate(x, y))) {
            return Optional.empty();
        }
        return Optional.ofNullable(terrainMap.get(new Coordinate(x, y)));
    }

    public Optional<Terrain> getTerrain(Coordinate coordinate) {
        if (!isInBounds(coordinate)) {
            return Optional.empty();
        }
        return Optional.ofNullable(terrainMap.get(coordinate));
    }

    public int getMaxCoin() {
        return maxCoin;
    }

    private int maxCoin = 0;

    public void checkMaxCoin(int coinCount) {
        if (coinCount > maxCoin) {
            maxCoin = coinCount;
        }
    }

}

abstract class Terrain {
    public Terrain(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public boolean isStarting() {
        return this instanceof UnknownTerrain;
    }

    public boolean isCoin() {
        return this instanceof CoinTerrain;
    }

    public boolean isEnemy() {
        return this instanceof EnemyTerrain;
    }

    public boolean isEmpty() {
        return this instanceof EmptyTerrain;
    }

    protected final Coordinate coordinate;

    public Coordinate getCoordinate() {
        return coordinate;
    }

    static class EnemyTerrain extends Terrain {
        public EnemyTerrain(Coordinate coordinate) {
            super(coordinate);
        }
    }

    static class EmptyTerrain extends Terrain {
        public EmptyTerrain(Coordinate coordinate) {
            super(coordinate);
        }
    }

    static class CoinTerrain extends Terrain {
        public CoinTerrain(Coordinate coordinate) {
            super(coordinate);
        }

    }

    static class UnknownTerrain extends Terrain {
        public UnknownTerrain(Coordinate coordinate) {
            super(coordinate);
        }

    }

    @Override
    public String toString() {
        return String.format("x:%d y:%d  class %s", coordinate.x, coordinate.y, this.getClass().getSimpleName());
    }
}

class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinate) {
            Coordinate coordinate = (Coordinate) obj;
            return coordinate.x == x && coordinate.y == y;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}