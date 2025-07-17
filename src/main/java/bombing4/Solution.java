package bombing4;


import java.util.Optional;

public class Solution {
    public static void main(String[] args) {
        Test test = new Test();
        test.doTest(index -> {
            Area area = new Area();
            area.readArea();
            solve(area.getStartingTerrain().getCoordinate(), area, 0, 0, false, null);
            System.out.println(area.getMaxCoin());
        });
    }

    private static void solve(Coordinate currentCoordinate, Area area, int coinCount, int bombCount, boolean bombUsed, Coordinate previous) {
        area.checkMaxCoin(coinCount);
        for (int i = -1; i <= 1; i++) {
            Coordinate coordinate = new Coordinate(currentCoordinate.x + i, currentCoordinate.y - 1);
            Optional<Terrain> terrainOptional = area.getTerrain(coordinate);
            if (terrainOptional.isPresent()) {
                Terrain terrain = terrainOptional.get();
                if (terrain.isCoin()) {
                    solve(new Coordinate(coordinate.x, coordinate.y), area, coinCount + 1, bombUsed?bombCount+1:bombCount, bombUsed, currentCoordinate);
                } else if (terrain.isEmpty()) {
                    solve(new Coordinate(coordinate.x, coordinate.y), area, coinCount, bombUsed?bombCount+1:bombCount, bombUsed, currentCoordinate);
                } else if (terrain.isEnemy()) {
                    if (bombUsed) {
                        if(bombCount < 5){
                            solve(new Coordinate(coordinate.x, coordinate.y), area, coinCount, bombCount+1, true, currentCoordinate);
                        }
                    } else {
                        solve(new Coordinate(coordinate.x, coordinate.y), area, coinCount, 0, true, currentCoordinate);
                    }
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
 0 2 2 0 0
 2 0 1 2 2
 2 2 1 2 1

 */
/***
 1
 2
 1 2 0 0 1
 2 0 0 1 0
 */