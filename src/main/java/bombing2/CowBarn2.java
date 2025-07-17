package bombing2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CowBarn2 {
    private static final Scanner sc = new Scanner(System.in);
    private static int readInt(){
        return sc.nextInt();
    }

    public static void main(String[] args) {
        int numTest = readInt();
        for (int test = 0; test < numTest; test++) {
            int numOfStalls = readInt();
            int numOfCows = readInt();
            int[] stalls = new int[numOfStalls];
            for (int stallIndex = 0; stallIndex < numOfStalls; stallIndex++) {
                stalls[stallIndex] = readInt();
            }
            Arrays.sort(stalls);
            solve(stalls,numOfCows);
        }
    }



    private static void solve(int[] stalls, int numOfCows) {
        int largestDistance =stalls[stalls.length-1]- stalls[0] ;
        int mid = largestDistance / 2;
        int addition = 0;
        int result = 0;
        while(addition >= mid*-1 && addition <= mid) {
            int placed = 1;
            boolean allCowPlaced = false;
            for (int i = 1; i + 1 < stalls.length; i++) {
                int prev = stalls[i - 1];
                int current = stalls[i];
                int dist = current - prev;
                if (prev + mid + addition >= current) {
                    placed++;
                }
                if (placed == numOfCows) {
                    allCowPlaced = true;
                    System.out.printf("prev= %d curr= %d placed = %d mid = %d  %d  startt=%d \n ",prev,current,placed,mid,addition,(prev + mid + addition));
                    result = mid + addition;
                    break;
                }
            }
            System.out.println(allCowPlaced);

            addition = allCowPlaced ? addition+1:addition-1;
        }
        System.out.println(result);
    }



}

/***
 2
 10 5
 1 2 3 4 5 6 7 8 9 10
 5 3
 1 3 5 8 10
 */