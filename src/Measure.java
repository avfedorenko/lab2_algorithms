import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.pow;

public class Measure {
    static int twoMaxPower = 12;
    static int repeatTimes = 10;
    static int powerX = 2767;
    static int powerY = 2789;

    public static void main(String[] args) throws FileNotFoundException {
        List<Solution> solutions = List.of(new BruteForceSolution(), new MapSolution(), new PersistentSegmentTreeSolution());
        Map<Solution, PrintWriter> writers = new HashMap<>();
        for (Solution solution : solutions) {
            writers.put(solution, new PrintWriter("artefacts/" + solution.getClass().getName() + ".csv"));
        }
        for (Solution solution : solutions) {
            for (int power = 0; power < twoMaxPower; ++power) {
                long summaryPreparationTime = 0;
                long summaryAnsweringTime = 0;
                int n = (int) pow(2, power);
                for (int r = 0; r < repeatTimes; ++r) {
                    List<Rectangle> rectangles = new ArrayList<>();
                    for (int i = 0; i < n; ++i) {
                        rectangles.add(new Rectangle(10 * i, 10 * i, 10 * (2 * n - i), 10 * (2 * n - i)));
                    }
                    long startTime = System.nanoTime();
                    solution.prepareData(rectangles);
                    summaryPreparationTime += System.nanoTime() - startTime;
                    startTime = System.nanoTime();
                    for (int i = 0; i < n; ++i) {
                        solution.getAnswer(new Point((int) (pow(powerX * i, 31) % (20 * n)), (int) (pow(powerY * i, 31) % (20 * n))));
                    }
                    summaryAnsweringTime += System.nanoTime() - startTime;
                }
                writers.get(solution).write(n + ";" + summaryPreparationTime / repeatTimes + ";" + summaryAnsweringTime / repeatTimes + "\n");
                System.out.println("n=" + n + " " + solution.getClass().getName());
            }
        }
        for (PrintWriter writer : writers.values()) {
            writer.close();
        }
    }
}
