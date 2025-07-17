package bombing3;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static utils.Reader.readInt;

public class Area {
    private Map<Coordinate,Integer> coordinates = new HashMap<>();
    int numCols = 0;
    int numRows = 0;
    int maximumCoinCollected = 0;

    public void readArea(int cols, int rows) {
        this.numCols = cols;
        this.numRows = rows;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Coordinate coordinate = new Coordinate(col,row);
                coordinates.put(coordinate,readInt());
            }
        }
    }

    public Terrain getValueOfCoordinate(Coordinate coordinate){
        return Terrain.parseInt(coordinates.get(coordinate),coordinate);
    }

    public Coordinate getCenterBottomCoordinate(){
        return  new Coordinate(numCols/2+1,numRows-1);
    }

    public Optional<Coordinate> getPreviousSiblings(Coordinate coordinate){
        if(coordinate.x-1 > 0 && coordinate.x-1 < numCols && coordinate.y > 0 && coordinate.y < numRows){
            return  Optional.of(new Coordinate(coordinate.x-1,coordinate.y));
        }
        return  Optional.empty();
    }

    public Optional<Coordinate> getNextSiblings(Coordinate coordinate){
        if(coordinate.x+1 > 0 && coordinate.x+1 < numCols && coordinate.y > 0 && coordinate.y < numRows){
            return  Optional.of(new Coordinate(coordinate.x+1,coordinate.y));
        }
        return  Optional.empty();
    }

    public Optional<Coordinate> getUpperLevel(Coordinate coordinate){
        if(coordinate.x > 0 && coordinate.x < numCols && coordinate.y-1 > 0 && coordinate.y-1 < numRows){
            return  Optional.of(new Coordinate(coordinate.x,coordinate.y-1));
        }
        return  Optional.empty();
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;
        Coordinate pair = (Coordinate) o;
        return Objects.equals(x, pair.x) && Objects.equals(y, pair.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }
}
