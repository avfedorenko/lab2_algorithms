import java.util.*;
import java.util.List;

public class MapSolution implements Solution {
    private int[][] map;
    List<Integer> arrayX;
    List<Integer> arrayY;

    public MapSolution() {
    }

    public void prepareData(List<Rectangle> rectangles) {
        if (!rectangles.isEmpty()) {
            Set<Integer> setX = new HashSet<>();
            Set<Integer> setY = new HashSet<>();
            for (Rectangle r : rectangles) {
                setX.add(r.LB.x);
                setY.add(r.LB.y);
                setX.add(r.RT.x);
                setY.add(r.RT.y);
            }
            arrayX = setX.stream().sorted().toList();
            arrayY = setY.stream().sorted().toList();
            Map<Integer, Integer> coordsX = new HashMap<>();
            Map<Integer, Integer> coordsY = new HashMap<>();
            for (int x = 0; x < arrayX.size(); ++x) {
                coordsX.put(arrayX.get(x), x);
            }
            for (int y = 0; y < arrayY.size(); ++y) {
                coordsY.put(arrayY.get(y), y);
            }
            map = new int[arrayX.size()][arrayY.size()];
            for (Rectangle r : rectangles) {
                for (int x = coordsX.get(r.LB.x); x < coordsX.get(r.RT.x); ++x) {
                    for (int y = coordsY.get(r.LB.y); y < coordsY.get(r.RT.y); ++y) {
                        ++map[x][y];
                    }
                }
            }
        }
    }

    public int getAnswer(Point point) {
        int x = BinarySearch.search(point.x, arrayX);
        int y = BinarySearch.search(point.y, arrayY);
        if (x == -1 || y == -1) return 0;
        return map[x][y];
    }
}