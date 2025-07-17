package cow_aggresive;

import java.util.Arrays;

import static utils.Reader.readInt;

public class Test2 {
    public static void main(String[] args) {
        int numTest = readInt();
        for (int i = 0; i < numTest; i++) {
            int numStalls = readInt();
            int numCows = readInt();
            int[] stalls = new int[numStalls];
            for (int stallIndex = 0; stallIndex < numStalls; stallIndex++) {
                stalls[stallIndex] = readInt();
            }
            Arrays.sort(stalls);
            int result = solve(stalls,numCows);
            System.out.println(result);
        }
    }

    private static int solve(int[] stalls, int numCows) {
        int left = 0;
        int right = stalls[stalls.length-1]-stalls[0];
        int result = 0;
        while(left <= right){
            int mid = left + (right-left)/2;
            if(canPlaceCows(stalls,mid,numCows)){
                left = mid + 1;
                result = mid;
            } else {
                right = mid-1;
            }
        }
        return result;
    }

    private static boolean canPlace( int[] stalls, int mid,int numCows) {
        int lastPlaced = stalls[0];
        int numPlaced = 1;
        for (int i = 1; i < stalls.length; i++) {
            if(stalls[i] - lastPlaced >= mid){
                lastPlaced = stalls[i];
                numPlaced++;
                if(numPlaced >= numCows){
                    return  true;
                }
            }

        }
        return numPlaced >= numCows;
    }
    private static boolean canPlaceCows(int[] stalls,  int minDistance,int cows) {
        int count = 1; // place first cow at first stall
        int lastPosition = stalls[0];

        for (int i = 1; i < stalls.length; i++) {
            if (stalls[i] - lastPosition >= minDistance) {
                count++;
                lastPosition = stalls[i];

                if (count >= cows) {
                    return true;
                }
            }
        }

        return count >= cows;
    }
}
