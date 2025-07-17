package cow_aggresive2;

import java.util.ArrayList;
import java.util.List;

public class Solution2 {
    public static void main(String[] args) {
        int[] data = new int[]{1,3,5,8,10};
        List<Integer> chosen = new ArrayList<>();
        exploreCombinations(data,0,chosen);
        System.out.println(result);
    }
    static int numCows = 3;
    static int result = Integer.MIN_VALUE;

    private static void exploreCombinations(int[] data,int pos, List<Integer> chosen) {
        if(chosen.size()  == numCows){
            System.out.println(chosen);
            int minimum = Integer.MAX_VALUE;
            for (int i = 1; i < chosen.size(); i++) {
                int distance = chosen.get(i)-chosen.get(i-1);
                minimum = Math.min(distance,minimum);
            }
            result = Math.max(result,minimum);
        }
        for (int i = pos; i < data.length; i++) {
            chosen.add(data[i]);
            exploreCombinations(data,i+1,chosen);
            chosen.remove(chosen.size()-1);
        }
    }
}
