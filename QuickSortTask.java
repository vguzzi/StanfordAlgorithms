import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util.println;

public class QuickSortTask {

    static int totalComparisons = 0;
    // start pivot comparisons = 162085
    // end pivot comparisons = 164123
    // median pivot comparisons = 138382

    public static void main(String[] args) {
        QuickSortTask app = new QuickSortTask();

        try {
            int[] a = getData(Integer.MAX_VALUE);
            app.quickSort(a, 0, a.length - 1);
            print(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void quickSort(int[] a, int s, int e) {
        if (s < e) {
            int pI = partitionMedian(a, s, e);
            quickSort(a, s, pI - 1);
            quickSort(a, pI + 1, e);
        }
    }

    private int partitionMedian(int[] arr, int s, int e) {
        int midIndex = s + (e - s) / 2;
        int a = arr[s];
        int b = arr[midIndex];
        int c = arr[e];
        int p = Math.max(Math.min(a, b), Math.min(Math.max(a, b), c));

        if (p == b) {
            swap(arr, s, midIndex);
        } else if (p == c) {
            swap(arr, e, s);
        }

        return partitionStart(arr, s, e);
    }

    private int partitionEnd(int[] a, int s, int e) {
        swap(a, e, s);
        return partitionStart(a, s, e);
    }

    private int partitionStart(int[] a, int s, int e) {
        countComparisons(s, e);
        int p = a[s];
        int pI = s + 1;

        for (int i = s + 1; i <= e; i++) {
            if (a[i] < p) {
                swap(a, i, pI);
                pI++;
            }
        }

        try {
            swap(a, s, pI - 1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return pI - 1;
    }

    private void countComparisons(int start, int end) {
        final int size = end - start;
        if (size > 0) totalComparisons += size;
    }

    private void swap(int[] arr, int a, int b) {
        final int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

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

    static private int[] getMedTest() {
        int[] x = new int[20];
        x[0] = 1;
        x[1] = 3;
        x[2] = 5;
        x[3] = 2;
        x[4] = 4;
        x[5] = 6;
        x[6] = 12;
        x[7] = 7;
        x[8] = 18;
        x[9] = 200;
        x[10] = 36;
        x[11] = 11;
        x[12] = 9;
        x[13] = 2098;
        x[14] = 233;
        x[15] = 54;
        x[16] = 234;
        x[17] = 58;
        x[18] = 84;
        x[19] = 33;
        return x;
    }

    static private int[] getData(int limit) throws IOException {
        List<Integer> data = new ArrayList<>();

        // Open the file
        FileInputStream fs = new FileInputStream("quicksort_data.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));

        String strLine;

        while ((strLine = br.readLine()) != null && data.size() < limit) {
            data.add(Integer.parseInt(strLine));
        }

        fs.close();
        return data.stream().mapToInt(i -> i).toArray();
    }


    static void print(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            sb.append("\n");
        }
        println(sb.toString());
    }
}
