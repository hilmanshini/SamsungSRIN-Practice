package cow_aggresive;

import java.util.Arrays;

import static utils.Reader.readInt;

/***
 1
 5 3
 1 3 5 8 10
 */
public class Test4 {

    public static void main(String[] args) {
        int numTest = readInt();
        for (int test = 0; test < numTest; test++) {
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
        int right = stalls[stalls.length-1] - stalls[0];
        int result = 0;
        while(left <= right){
            int mid = left + (right-left)/2;
            if(canPlace(stalls,numCows,mid)){
                left = mid+1;
                result = mid;
            } else {
                right = mid-1;
            }
        }
        return result;
    }

    private static boolean canPlace(int[] stalls, int numCows, int mid) {
        int lastPlaced = stalls[0];
        int count = 1;
        int[] copied = Arrays.copyOfRange(stalls,1,stalls.length);
        for (int stall : copied) {
            if(stall - lastPlaced >= mid){
                lastPlaced = stall;
                count++;
                if(count >= numCows){
                    return  true;
                }
            }
        }
        return count >= numCows;
    }
}
