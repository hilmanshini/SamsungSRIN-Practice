package cow_aggresive;

import java.util.Arrays;

import static utils.Reader.readInt;

public class Test5 {
    public static void main(String[] args) {
        int numTest = readInt();
        for (int test = 0; test < numTest; test++) {
            int numStall = readInt();
            int numCows = readInt();
            int[] stallDistances = new int[numStall];
            for (int stallIndex = 0; stallIndex < numStall; stallIndex++) {
                stallDistances[stallIndex] = readInt();
            }
            Arrays.sort(stallDistances);
            int result = solve(stallDistances,numCows);
            System.out.println(result);
        }
    }

    private static int solve(int[] stallDistances, int numCows) {
        int left = 0;
        int right = stallDistances[stallDistances.length-1]-stallDistances[0];
        int result = 0;
        while(left <=  right){
            int mid = left + (right-left)/2;
            if(canPlace(mid,stallDistances,numCows)){
                result = mid;
                left = mid+1;
            }else {
                right = mid-1;
            }
        }
        return result;
    }

    private static boolean canPlace(int mid, int[] stallDistances, int numCows) {
        int lastPlaced = stallDistances[0];
        int count = 1;
        for (int i = 1; i < stallDistances.length; i++) {
            if(stallDistances[i]-lastPlaced >= mid){
                lastPlaced = stallDistances[i];
                count++;
                if(count >= numCows){
                    return true;
                }
            }
        }
        return count >= numCows;
    }
}
