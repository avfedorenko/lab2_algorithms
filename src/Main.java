import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            rectangles.add(new Rectangle(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
        }
//        Solution task = new BruteForceSolution();
//        Solution task = new MapSolution();
        Solution task = new PersistentSegmentTreeSolution();
        task.prepareData(rectangles);
        n = scanner.nextInt();
        for (int i = 0; i < n; ++i) {
            System.out.print(task.getAnswer(new Point(scanner.nextInt(), scanner.nextInt())) + " ");
        }
        scanner.close();
    }
}