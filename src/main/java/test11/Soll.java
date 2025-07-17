package test11;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Soll {
    private static final Scanner scanner = new Scanner(System.in);
    private static int minSoFar = Integer.MAX_VALUE;
    private static int readInt(){
        return scanner.nextInt();
    }

    public static void main(String[] args) {
        int numTest = readInt();
        IntStream.range(0,numTest).forEach(value -> {
            int numSpot = readInt();
            var gates = IntStream.range(0,3).mapToObj(value1 -> new Gate(
                    readInt()-1,readInt()
            )).collect(Collectors.toList());
            List<List<Gate>> permutatedGates = new ArrayList<>();
            assignPermuation(gates,permutatedGates,0,1,2);
            assignPermuation(gates,permutatedGates,0,2,1);
            assignPermuation(gates,permutatedGates,1,0,2);
            assignPermuation(gates,permutatedGates,1,2,0);
            assignPermuation(gates,permutatedGates,2,1,0);
            assignPermuation(gates,permutatedGates,2,0,1);
            minSoFar = Integer.MAX_VALUE;
            permutatedGates.forEach(gates1 -> {
                var spots = IntStream.range(0,numSpot).mapToObj(FishingSpot::new).collect(Collectors.toList());
                solve(spots,gates1,0,0);
            });
            System.out.println(minSoFar);
        });
    }

    private static void solve(List<FishingSpot> spots, List<Gate> gates1, int distance, int gateIndex) {
        if(distance > minSoFar)return;
        if(gates1.size() == gateIndex){
            minSoFar = Math.min(distance,minSoFar);
            return;
        }
        Gate gate = gates1.get(gateIndex);
        if(gate.fishermanAssigned == gate.fishermanCapacity){
            solve(spots,gates1,distance,gateIndex+1);
            return;
        }
        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < spots.size(); i++) {
            int left = gate.index-i;
            int right = gate.index+i;
            boolean found = false;
            if(left >= 0 && !spots.get(left).occupied){
                candidates.add(left);
                found = true;
            }
            if(right < spots.size() && left != right && !spots.get(right).occupied){
                candidates.add(right);
                found = true;
            }
            if(found){
                break;
            }
        }
        for (Integer candidate : candidates) {
            gate.fishermanAssigned++;
            spots.get(candidate).occupied = true;
            solve(spots, gates1, distance+Math.abs(gate.index-candidate)+1, gateIndex);
            spots.get(candidate).occupied = false;
            gate.fishermanAssigned--;
        }
    }

    private static void assignPermuation(List<Gate> gates, List<List<Gate>> permutatedGates, int i, int i1, int i2) {
        List<Gate> result = new ArrayList<>();
        result.add(new Gate(gates.get(i).index,gates.get(i).fishermanCapacity));
        result.add(new Gate(gates.get(i1).index,gates.get(i1).fishermanCapacity));
        result.add(new Gate(gates.get(i2).index,gates.get(i2).fishermanCapacity));
        permutatedGates.add(result);
    }
}


class Gate {
    int fishermanCapacity;
    int fishermanAssigned;
    int index;

    public Gate( int index,int fishermanCapacity) {
        this.fishermanCapacity = fishermanCapacity;
        this.index = index;
    }

}

class FishingSpot{
    public FishingSpot(int index) {
        this.index = index;
    }

    int index;
    boolean occupied;

}