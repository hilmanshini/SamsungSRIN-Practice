package test10;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solu {
    static  int minSoFar = 0;
    public static void main(String[] args) {
        int numOfTest = readInt();

        IntStream.range(0,numOfTest).forEach(value -> {
            int numOfSpots = readInt();
            var gates = IntStream.range(0,3).mapToObj(value1 -> new Gate(
                    readInt()-1,
                    readInt()
            )).collect(Collectors.toList());
            List<List<Gate>> permutated = new ArrayList<>();
            assignPermutated(gates,permutated,0,1,2);
            assignPermutated(gates,permutated,0,2,1);
            assignPermutated(gates,permutated,1,0,2);
            assignPermutated(gates,permutated,1,2,0);
            assignPermutated(gates,permutated,2,1,0);
            assignPermutated(gates,permutated,2,0,1);
            minSoFar = Integer.MAX_VALUE;
            permutated.forEach(gates1 -> {
                var spots = IntStream.range(0,numOfSpots).mapToObj(Spot::new).collect(Collectors.toList());
                solve(spots,gates1,0,0);
            });
            System.out.println(minSoFar);
        });
    }

    private static void solve(List<Spot> spots, List<Gate> gates1, int distance, int gateIndex) {
        if(distance > minSoFar)return;
        if(gates1.size() == gateIndex){
            minSoFar = Math.min(distance,minSoFar);
            return;
        }
        Gate gate = gates1.get(gateIndex);
        if(gate.fishermenAssigned == gate.fishermenCapacity){
            solve(spots,gates1,distance,gateIndex+1);
            return;
        }

        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < spots.size(); i++) {
            int left = gate.index-i;
            int right = gate.index+i;
            boolean found = false;
            if(left >= 0 && !spots.get(left).occupied){
                found = true;
                candidates.add(left);
            }

            if(right < spots.size() && left != right && !spots.get(right).occupied){
                found = true;
                candidates.add(right);
            }
            if(found){
                break;
            }
        }
        for (Integer candidate : candidates) {
            gate.fishermenAssigned++;
            spots.get(candidate).occupied = true;
            solve(spots,gates1,distance+Math.abs(gate.index-candidate)+1,gateIndex);
            spots.get(candidate).occupied= false;
            gate.fishermenAssigned--;
        }

    }

    private static void assignPermutated(List<Gate> gates, List<List<Gate>> permutated, int i, int i1, int i2) {
        List<Gate> result = new ArrayList<>();
        result.add(new Gate(gates.get(i).index,gates.get(i).fishermenCapacity));
        result.add(new Gate(gates.get(i1).index,gates.get(i1).fishermenCapacity));
        result.add(new Gate(gates.get(i2).index,gates.get(i2).fishermenCapacity));
        permutated.add(result);
    }


    private static Scanner scanner = new Scanner(System.in);
    private static int readInt(){
        return  scanner.nextInt();
    }
}

class Gate{
    public Gate( int index,int fishermenCapacity) {
        this.fishermenCapacity = fishermenCapacity;
        this.index = index;
    }

    int fishermenCapacity;
    int fishermenAssigned;
    int index;

}

class Spot{
    boolean occupied;

    public Spot(int index) {
        this.index = index;
    }

    int index;

}


