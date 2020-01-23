import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util.println;

public class SplitInversions {

    public static void main(String[] args) {
        SplitInversions app = new SplitInversions();

        try {
            Pair<int[], Long> result = app.mergeAndCount(getInversions());
            println(String.valueOf(result.getValue()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Pair<int[], Long> mergeAndCount(int[] input) {
        // base case
        if (input.length <= 1) return new Pair<>(input, 0L);

        // divide
        Pair<int[], Long> left = mergeAndCount(subArray(input, 0, input.length / 2));
        Pair<int[], Long> right = mergeAndCount(subArray(input, input.length / 2, input.length));
        int splitInversions = 0;

        // conquer
        int[] result = new int[left.getKey().length + right.getKey().length];
        int i = 0, k = 0, j = 0;
        while (i < result.length) {
            if (k == left.getKey().length) {
                result[i] = right.getKey()[j++];
            } else if (j == right.getKey().length) {
                result[i] = left.getKey()[k++];
            } else if (left.getKey()[k] < right.getKey()[j]) {
                result[i] = left.getKey()[k++];
            } else if (right.getKey()[j] < left.getKey()[k]) {
                splitInversions++;
                splitInversions += (left.getKey().length-1) - k;
                result[i] = right.getKey()[j++];
            } else {
                result[i] = left.getKey()[k++];
            }
            i++;
        }

        return new Pair<>(result, splitInversions + left.getValue() + right.getValue());
    }

    private int[] subArray(int[] arr, int start, int end) {
        if (start < 0 || end <= 0 || end == start) return new int[0];
        if (end < start) return subArray(arr, end, start);

        int[] x = new int[end - start];
        int k = 0;
        for (int i = start; i < end; i++) {
            x[k] = arr[i];
            k++;
        }
        return x;
    }

    static private int[] getInversions() throws IOException {
        List<Integer> inversions = new ArrayList<>();

        // Open the file
        FileInputStream fs = new FileInputStream("inversions.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));

        String strLine;

        while ((strLine = br.readLine()) != null) {
            inversions.add(Integer.parseInt(strLine));
        }

        fs.close();
        return inversions.stream().mapToInt(i -> i).toArray();
    }

    // 3 inversions
    static private int[] getSmallTest() {
        int[] x = new int[6];
        x[0] = 1;
        x[1] = 3;
        x[2] = 5;
        x[3] = 2;
        x[4] = 4;
        x[5] = 6;
        return x;
    }

    // 6 inversions
    static private int[] getBigTest() {
        int[] x = new int[10];
        x[0] = 1;
        x[1] = 3;
        x[2] = 5;
        x[3] = 2;
        x[4] = 4;
        x[5] = 6;
        x[6] = 10;
        x[7] = 7;
        x[8] = 8;
        x[9] = 9;
        return x;
    }
}
