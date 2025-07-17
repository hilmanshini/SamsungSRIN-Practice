import java.util.*;

class Gate implements Cloneable {
    int location;
    int fishermen;

    public Gate(int location, int fishermen) {
        this.location = location;
        this.fishermen = fishermen;
    }

    @Override
    protected Object clone() {
        return new Gate(this.location, this.fishermen);
    }
}

class FishingSpot {
    private final int index;
    private boolean occupied;

    public FishingSpot(int index) {
        this.index = index;
        this.occupied = false;
    }

    public int getIndex() {
        return index;
    }

    public void occupy() {
        this.occupied = true;
    }

    public void release() {
        this.occupied = false;
    }

    public boolean isOccupied() {
        return occupied;
    }
}

class FishingGround {
    private Gate[] gates;
    private FishingSpot[] fishingSpots;
    private int minDistance;

    public FishingGround(FishingSpot[] fishingSpots, Gate[] gates) {
        this.gates = gates;
        this.fishingSpots = fishingSpots;
        this.minDistance = Integer.MAX_VALUE / 2;
    }

    private FishingSpot findLeft(int index) {
        for (int i = index; i >= 0; i--) {
            if (!fishingSpots[i].isOccupied()) return fishingSpots[i];
        }
        return null;
    }

    private FishingSpot findRight(int index) {
        for (int i = index; i < fishingSpots.length; i++) {
            if (!fishingSpots[i].isOccupied()) return fishingSpots[i];
        }
        return null;
    }

    private void assign(Gate[] gatesState, int[] order, int gateIndex, int currentDistance) {
        if (currentDistance >= minDistance) return;

        if (gateIndex == order.length) {
            minDistance = Math.min(minDistance, currentDistance);
            return;
        }

        int currentGateIndex = order[gateIndex];
        Gate currentGate = gatesState[currentGateIndex];
        if (currentGate.fishermen == 0) {
            assign(gatesState, order, gateIndex + 1, currentDistance);
            return;
        }

        FishingSpot leftSpot = findLeft(currentGate.location);
        FishingSpot rightSpot = findRight(currentGate.location);

        int costLeft = leftSpot == null ? Integer.MAX_VALUE / 2 : Math.abs(currentGate.location - leftSpot.getIndex()) + 1;
        int costRight = rightSpot == null ? Integer.MAX_VALUE / 2 : Math.abs(currentGate.location - rightSpot.getIndex()) + 1;

        boolean canGoLeft = leftSpot != null && costLeft <= costRight;
        boolean canGoRight = rightSpot != null && costRight <= costLeft;

        if (canGoLeft) {
            leftSpot.occupy();
            currentGate.fishermen--;
            assign(gatesState, order, gateIndex, currentDistance + costLeft);
            leftSpot.release();
            currentGate.fishermen++;
        }
        if (canGoRight && rightSpot != leftSpot) { // Avoid double-counting if left==right
            rightSpot.occupy();
            currentGate.fishermen--;
            assign(gatesState, order, gateIndex, currentDistance + costRight);
            rightSpot.release();
            currentGate.fishermen++;
        }
    }

    public int computeMinimumDistance() {
        List<int[]> permutations = new ArrayList<>();
        int[] indices = new int[gates.length];
        for (int i = 0; i < gates.length; i++) indices[i] = i;
        permute(indices, 0, permutations);

        for (int[] perm : permutations) {
            for (FishingSpot spot : fishingSpots) {
                spot.release();
            }
            Gate[] copiedGates = new Gate[gates.length];
            for (int i = 0; i < gates.length; i++) {
                copiedGates[i] = (Gate) gates[i].clone();
            }
            assign(copiedGates, perm, 0, 0);
            System.out.println();
        }

        return minDistance;
    }

    private void permute(int[] arr, int k, List<int[]> result) {
        if (k == arr.length) {
            result.add(arr.clone());
        } else {
            for (int i = k; i < arr.length; i++) {
                swap(arr, i, k);
                permute(arr, k + 1, result);
                swap(arr, i, k);
            }
        }
    }

    private void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while (t-- > 0) {
            int spots = sc.nextInt();
            int numGates = 3;

            FishingSpot[] fishingSpots = new FishingSpot[spots];
            for (int i = 0; i < spots; i++) {
                fishingSpots[i] = new FishingSpot(i);
            }

            Gate[] gates = new Gate[numGates];
            for (int i = 0; i < numGates; i++) {
                int loc = sc.nextInt() - 1; // Convert to 0-based index!
                int men = sc.nextInt();
                gates[i] = new Gate(loc, men);
            }

            FishingGround ground = new FishingGround(fishingSpots, gates);
            int result = ground.computeMinimumDistance();
            System.out.println(result);
        }

        sc.close();
    }
}
/***

 1
 10
 4 5
 6 2
 10 2
 */