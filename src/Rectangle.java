public class Rectangle {
    Point LB;
    Point RT;

    public Rectangle(int x1, int y1, int x2, int y2) {
        this.LB = new Point(x1, y1);
        this.RT = new Point(x2, y2);
    }
}