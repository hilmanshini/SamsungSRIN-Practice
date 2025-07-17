package test2;

import java.util.List;

public class FishingSpot extends  Spot{

    public FishingSpot(int index) {
        super(index);
    }

    public static void purge(List<Spot> spots){
        for (Spot spot : spots) {
            if(spot instanceof  FishingSpot){
                spot.occupied = false;
            }
        }
    }


}
