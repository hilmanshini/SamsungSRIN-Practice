package bombing3;

public abstract class Terrain {
    public Terrain(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    Coordinate coordinate;

    static int ENEMY_TERRAIN_VALUE = 2;
    static int COIN_TERRAIN_VALUE = 1;
    static int EMPTY_TERRAIN_VALUE = 0;

    public static Terrain  parseInt(int value, Coordinate coordinate){
        if(value == ENEMY_TERRAIN_VALUE){
            return  new EnemyTerrain(coordinate);
        } else if(value == COIN_TERRAIN_VALUE){
            return  new CoinTerrain(coordinate);
        } else if(value == EMPTY_TERRAIN_VALUE){
            return  new EmptyTerrain(coordinate);
        }
        return  new UnknownTerrain(coordinate);
    }

    public boolean isEnemyTerrain(){
        return  this instanceof  EnemyTerrain;
    }

    public boolean isEmptyTerrain(){
        return  this instanceof  EmptyTerrain;
    }

    public boolean isCoinTerrain(){
        return  this instanceof  CoinTerrain;
    }
}

class EnemyTerrain extends   Terrain{

    public EnemyTerrain(Coordinate coordinate) {
        super(coordinate);
    }
}

class EmptyTerrain  extends  Terrain{

    public EmptyTerrain(Coordinate coordinate) {
        super(coordinate);
    }
}

class CoinTerrain extends  Terrain{

    public CoinTerrain(Coordinate coordinate) {
        super(coordinate);
    }
}

class UnknownTerrain extends  Terrain{

    public UnknownTerrain(Coordinate coordinate) {
        super(coordinate);
    }
}
