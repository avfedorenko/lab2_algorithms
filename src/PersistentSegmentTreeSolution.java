import java.util.*;
import java.util.List;

public class PersistentSegmentTreeSolution implements Solution {
    PersistentSegmentTree tree;
    List<Integer> arrayX;
    List<Integer> arrayY;

    public PersistentSegmentTreeSolution() {
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
            List<Event> events = new ArrayList<>();
            for (Rectangle r : rectangles) {
                events.add(new Event(BinarySearch.search(r.LB.x, arrayX), BinarySearch.search(r.LB.y, arrayY), BinarySearch.search(r.RT.y, arrayY), 1));
                events.add(new Event(BinarySearch.search(r.RT.x, arrayX), BinarySearch.search(r.LB.y, arrayY), BinarySearch.search(r.RT.y, arrayY), -1));
            }
            events.sort(Comparator.comparingInt(e -> e.x));
            tree = new PersistentSegmentTree(events, arrayY.size());
        }
    }

    public int getAnswer(Point point) {
        if (arrayX.isEmpty()) {
            return 0;
        }
        int compX = BinarySearch.search(point.x, arrayX);
        int compY = BinarySearch.search(point.y, arrayY);
        if (compX == -1 || compY == -1 || tree.nodes.size() <= compX) {
            return 0;
        }
        return tree.search(compX, compY);
    }
}