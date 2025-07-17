package bombing3;

import java.util.Optional;

import static utils.Reader.readInt;

public class AeroPlaneBombing {

    public static void main(String[] args) {
        new Test().run(readInt(), (index) -> {
            int numCols = 5;
            int numRows = readInt();

            Area area = new Area();
            area.readArea(numCols, numRows);
            solve(area, area.getCenterBottomCoordinate(), 0, 0, false);
            System.out.println(area.maximumCoinCollected);
        });
    }

    private static void solve(Area area, Coordinate coordinate, int coinFound, int bombCount, boolean bombUsed) {
        if(coinFound > area.maximumCoinCollected){
            area.maximumCoinCollected = coinFound;
        }
        Optional<Coordinate> previous = area.getPreviousSiblings(coordinate);
        Optional<Coordinate> next = area.getNextSiblings(coordinate);
        var currentTerrain = area.getValueOfCoordinate(coordinate);
        previous.ifPresent(prevCoordinate -> {
            Terrain terrain = area.getValueOfCoordinate(prevCoordinate);
            processDecisionOfTerrain(terrain, area, prevCoordinate, coinFound, bombCount, bombUsed);
        });
        next.ifPresent(nextCoordinate -> {
            Terrain terrain = area.getValueOfCoordinate(nextCoordinate);
            processDecisionOfTerrain(terrain, area, nextCoordinate, coinFound, bombCount, bombUsed);
        });
        processDecisionOfTerrain(currentTerrain, area, coordinate, coinFound, bombCount, bombUsed);
//        var nextTerrain = next.map(area::getValueOfCoordinate);
//        prevTerrain.ifPresent(terrain -> processDecisionOfTerrain(terrain,area, coinFound, bombCount, bombUsed));
//        processDecisionOfTerrain(currentTerrain,area,coordinate,coinFound,bombCount,bombUsed);
//        nextTerrain.ifPresent(terrain -> processDecisionOfTerrain(terrain,area, coinFound, bombCount, bombUsed));
    }

    private static void processDecisionOfTerrain(Terrain terrain, Area area, Coordinate coordinate, int coinFound, int bombCount, boolean bombUsed) {
        var upper = area.getUpperLevel(coordinate);
        if (terrain.isEnemyTerrain()) {
            if (!bombUsed && upper.isPresent()) {
                solve(area, upper.get(), coinFound, 1, true);
            } else if (bombUsed && upper.isPresent()) {
                if (bombCount < 5) {
                    solve(area, upper.get(), coinFound, bombCount + 1, true);
                }
            }
        } else if (terrain.isCoinTerrain()) {
            upper.ifPresent(value -> solve(area, value, coinFound + 1, bombUsed ? bombCount + 1 : bombCount, bombUsed));
        } else if (terrain.isEmptyTerrain()) {
            upper.ifPresent(value -> solve(area, value, coinFound, bombUsed ? bombCount + 1 : bombCount, bombUsed));
        }
    }

}


class Test {
    public void run(int times, RunCase runnable) {
        for (int i = 0; i < times; i++) {
            runnable.run(i);
        }
    }
}

interface RunCase {
    void run(int index);
}

