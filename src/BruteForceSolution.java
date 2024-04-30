import java.util.List;

public class BruteForceSolution implements Solution {
    List<Rectangle> rectangles;

    public BruteForceSolution() {
    }


    public void prepareData(List<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }

    public int getAnswer(Point point) {
        int count = 0;
        for (Rectangle r : rectangles) {
            if (point.x >= r.LB.x && point.x < r.RT.x && point.y >= r.LB.y && point.y < r.RT.y) {
                ++count;
            }
        }
        return count;
    }
}