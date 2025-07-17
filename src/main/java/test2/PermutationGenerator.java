package test2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PermutationGenerator {
    private PermutationGenerator() {
    }

    public static List<int[]> generate(int n) {
        int[] data = IntStream.range(0, n).toArray();
        List<int[]> result = new ArrayList<>();
        permute(0, data, result);
        return result;
    }

    private static void permute(int index, int[] data, List<int[]> result) {
        if (index == data.length) {
            result.add(data.clone());
        } else {
            for (int i = index; i < data.length; i++) {
                swap(data, i, index);
                permute(index + 1, data, result);
                swap(data, i, index);
            }
        }
    }

    private static void swap(int[] data, int idx1, int idx2) {
        if (idx1 == idx2) return;
        int tmp = data[idx1];
        data[idx1] = data[idx2];
        data[idx2] = tmp;
    }

    public static void main(String[] args) {
        var e = generate(3);
        System.out.println();
    }
}
