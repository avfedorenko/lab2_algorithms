import java.util.List;

public interface Solution {
    void prepareData(List<Rectangle> rectangles);

    int getAnswer(Point query);
}