package burst_baloon;

import java.util.ArrayList;
import java.util.List;

import static utils.Reader.readInt;

public class Baloon2 {
    public static void main(String[] args) {
        int size = readInt();
        List<Integer> n = new ArrayList<>();
        List<Integer> travelled = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            n.add(readInt());
        }
        solve(n,travelled,0);
        System.out.println(max);
    }

    private static int max = 0;

    private static void solve(List<Integer> n, List<Integer> travelled,int point) {
        if(n.isEmpty()){
            System.out.printf("%s =>  %d \n",travelled, point);
            max = Math.max(max,point);
            return;
        }
        for (int baloonIndex = 0; baloonIndex < n.size(); baloonIndex++) {
//            int removedValue = n.get(baloonIndex);
            int gain = getGain(baloonIndex,n);
            int removedValue = n.remove(baloonIndex);
            travelled.add(removedValue);
            int index = travelled.size();
            solve(n, travelled,point+gain);
            travelled.remove(index-1);
            n.add(baloonIndex,removedValue);
        }
    }

    private static int getGain(int index, List<Integer> data){
        if(data.size() == 1){
            return  data.get(index);
        } else if( index == 0){
            return  data.get(1);
        } else if( index == data.size()-1){
            return  data.get(data.size()-2);
        } else {
            return  data.get(index-1)*data.get(index+1);
        }
    }
}
