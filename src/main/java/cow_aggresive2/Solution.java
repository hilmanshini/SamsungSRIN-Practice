package cow_aggresive2;

import java.util.ArrayList;
import java.util.List;

import static utils.Reader.readInt;

public class Solution {
    /***
     1
     10 5
     1 2 3 4 5 6 7 8 9 10
     * @param args
     */
    public static void main(String[] args) {
        int test = readInt();
        for (int testIndex = 0; testIndex < test; testIndex++) {
            int numStall = readInt();
            int numCows = readInt();
            int[] stalls = new int[numStall];
            for (int stallIndex = 0; stallIndex < numStall; stallIndex++) {
                stalls[stallIndex] = readInt();
            }
//            int result = solve(stalls,numCows);
//            System.out.println(result);
            List<Integer> chosen = new ArrayList<>();
            dfs(stalls,numCows,0,chosen);
            System.out.println(maxMinDist);
        }
    }
    static  int maxMinDist = 0;

    static void dfs(int[] stalls, int cowsLeft, int pos, List<Integer> chosen) {
//        if (cowsLeft == 0) {
//            // Compute min distance for this arrangement
//            int minDist = Integer.MAX_VALUE;
//            for (int i = 1; i < chosen.size(); i++) {
//                minDist = Math.min(minDist, chosen.get(i) - chosen.get(i - 1));
//            }
//            maxMinDist = Math.max(maxMinDist, minDist);
//            return;
//        }
//        System.out.println(chosen);

        for (int i = pos; i < stalls.length; i++) {
            chosen.add(stalls[i]);
            System.out.println("add  "+stalls[i]+" "+chosen);
            dfs(stalls, cowsLeft - 1, i + 1, chosen);
            int toRemote = chosen.get(chosen.size() - 1);
            chosen.remove(chosen.size() - 1);
            System.out.printf("remove "+toRemote+" "+chosen+"");
//            System.out.println(chosen);

            System.out.println();
        }
    }

    private static int solve(int[] stalls, int numCows) {
        int left = 0;
        int right = stalls[stalls.length-1] - stalls[0];
        int result = 0;
        List<Integer> resultList = new ArrayList<>();
        while (left <= right) {
            int mid = left + (right-left)/2;
            List<Integer> placed = canPlace(mid,stalls,numCows);
            if(!placed.isEmpty()){
                result = mid;
                resultList = placed;
                left = mid+1;
            } else {
                right = mid-1;
            }
        }
        for (Integer i : resultList) {
            System.out.printf("%d ",i);
        }
        System.out.println();
        return result;
    }

    private static List<Integer> canPlace(int mid, int[] stalls, int numCows) {
        int lastPlaced = stalls[0];
        int placed = 1;
        List<Integer> result = new ArrayList<>();
        result.add(lastPlaced);
        for (int stallIndex = 1; stallIndex < stalls.length; stallIndex++) {
            if(lastPlaced+mid <= stalls[stallIndex]){
                lastPlaced = stalls[stallIndex];
                placed++;
                result.add(stalls[stallIndex]);
                if(placed >= numCows){
                    return  result;
                }
            }
        }
        return placed >= numCows ? result : new ArrayList<>();
    }

}
