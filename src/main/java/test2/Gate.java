package test2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Gate extends  Spot{
    int fishermen;

    public Gate(int index, int fishermen) {
        super(index);
        this.fishermen = fishermen;
        occupied = true;
    }


    public static List<List<Gate>> generateByIndex(List<int[]> input, List<Gate> reference) {
        return input.stream().map(ints ->
                Arrays.stream(ints).mapToObj(reference::get).collect(Collectors.toList())
        ).collect(Collectors.toList());
    }

    public int getDistanceToSpot(Spot spot) {
        return Math.abs(index-spot.index)+1;
    }

    public boolean isLeftDistanceValid(int dist, List<Spot> spots) {
        int left = index - dist;
        return  (left >= 0 && !spots.get(left).occupied);
    }

    public boolean isRightDistanceValid(int dist, List<Spot> spots) {
        int right = index + dist;
        return  (right < spots.size() && dist != 0 && !spots.get(right).occupied);
    }
}
