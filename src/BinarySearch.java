import java.util.List;

public class BinarySearch {
    public static int search(int q, List<Integer> array) {
        int l = 0;
        int h = array.size();
        while (l < h) {
            int m = l + (h - l) / 2;
            if (array.get(m) <= q) {
                l = m + 1;
            } else {
                h = m;
            }
        }
        return l - 1;
    }
}
