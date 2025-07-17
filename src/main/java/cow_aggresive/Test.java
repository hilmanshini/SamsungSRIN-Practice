package cow_aggresive;

/***
 Farmer John has built a new long barn, with N (2 <= N <= 100,000) stalls. The stalls are located along a straight line at positions x1....,XN (0 <= xi <= 1,000,000,000).
 His C (2 <= C <= N) cows don't like this barn layout and become aggressive towards each other once put into a stall. To prevent the cows from hurting each other, Fj wants to assign the cows to the stalls, such that the minimum distance between any two of them is as large as possible. What is the largest minimum distance?
 Input
 t- the number of test cases, then t test cases follows.
 * Line 1: Two space-separated integers: N and C
 * Lines 2..N+1: Line i+1 contains an integer stall location, xi
 Output
 For each test case output one integer: the largest minimum distance.
 Example
 1
 5 3
 1
 2
 8
 4
 9

 output = 3
 */

import utils.Reader;
import utils.Reader.*;

import java.util.Arrays;

/**
 2
 10 5
 1 2 3 4 5 6 7 8 9 10
 5 3
 1 3 5 8 10
 */
public class Test {
    public static void main(String[] args) {
        int numOfTest = Reader.readInt();
        for (int test = 0; test < numOfTest; test++) {
            int numOfStalls = Reader.readInt();
            int numOfCows = Reader.readInt();
            int[] stalls =  new int[numOfStalls];
            for (int i = 0; i < numOfStalls; i++) {
                stalls[i] = Reader.readInt();
            }
            Arrays.sort(stalls);
            int result = solve(stalls,numOfCows);
            System.out.printf("%d %d \n",(test+1),result);
        }
    }

    private static int solve(int[] stalls, int numOfCows) {
        int left = stalls[0];
        int right = stalls[stalls.length-1];
        int result = 0;
        int mid = left + (right-left)/2;
        while(mid > 0 && mid <= (right-left)){
            mid = canPlace(stalls,numOfCows,mid)? mid+1:mid-1;
            System.out.println(mid);
        }
        return  result;

    }

    private static boolean canPlace(int[] stalls, int numOfCows, int mid) {
//        int lastIndex = 1;
        int lastIndexValue = stalls[0];
        int totalPlaced = 1;
        for (int i =1; i < stalls.length; i++) {
//            int dist = stalls[i] = stalls[i-1];
            if(stalls[i]-lastIndexValue >= mid){
                lastIndexValue = stalls[i];
                totalPlaced++;
                if(totalPlaced >= numOfCows){
                    return  true;
                }
            }
        }
        return totalPlaced >= numOfCows;
    }

}
