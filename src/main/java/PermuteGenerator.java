import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermuteGenerator {

    void generate(int n ){
        List<int[]> result = new ArrayList<>();
        int[] data = new int[n];
        for(int i = 0 ; i < n ;i++){
            data[i] = i;
        }
        permute(0,data,result);
        System.out.println();


    }

    void permute(int k,int[] data ,List<int[]> result){
        if(k == data.length){
            result.add(data.clone());
        } else {
            for(int i = k ; i < data.length;i++){
                swap(data,i,k);
                permute(k+1,data,result);
                swap(data,i,k);
            }
        }

    }
    void swap(int[] data,int i, int j){
        if(i == j)return;
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }



    public static void main(String[] args) {
        int n = 3;
        new PermuteGenerator().generate(n);;
    }
}
