package test3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Permute {

    public static List<int[]> generate(int size) {
        var data = IntStream.range(0,size).toArray();
        List<int[]> result = new ArrayList<>();
        permute(0,data,result);
        return  result;
    }

    private static void permute(int i, int[] data, List<int[]> result) {
        if(i == data.length){
            result.add(data.clone());
        } else {
            for(int j = i ; j < data.length;j++){
                swap(data,i,j);
                permute(i+1,data,result);
                swap(data,i,j);
            }
        }
    }

    private static void swap(int[] data, int i, int j) {
        if(i == j)return;
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    public static void main(String[] args) {
        Permute.generate(4);
    }
}

