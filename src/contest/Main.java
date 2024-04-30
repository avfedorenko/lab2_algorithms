package contest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            rectangles.add(new Rectangle(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
        }
        Solution task = new PersistentSegmentTreeSolution();
        task.prepareData(rectangles);
        n = scanner.nextInt();
        for (int i = 0; i < n; ++i) {
            System.out.print(task.getAnswer(new Point(scanner.nextInt(), scanner.nextInt())) + " ");
        }
        scanner.close();
    }
}

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Rectangle {
    Point LB;
    Point RT;

    public Rectangle(int x1, int y1, int x2, int y2) {
        this.LB = new Point(x1, y1);
        this.RT = new Point(x2, y2);
    }
}

interface Solution {
    void prepareData(List<Rectangle> rectangles);

    int getAnswer(Point query);
}

class Event {
    int x;
    int start;
    int end;
    int align;

    Event(int x, int start, int end, int align) {
        this.x = x;
        this.start = start;
        this.end = end;
        this.align = align;
    }
}

class Node {
    Node left;
    Node right;
    int l;
    int r;
    int sum;

    Node(Node left, Node right, int l, int r, int sum) {
        this.left = left;
        this.right = right;
        this.l = l;
        this.r = r;
        this.sum = sum;
    }
}

class PersistentSegmentTree {
    List<Node> nodes = new ArrayList<>();

    PersistentSegmentTree(List<Event> events, int size) {
        Node root = buildEmptyTree(0, size);
        int x = events.get(0).x;
        for (Event event : events) {
            if (event.x != x) {
                nodes.add(root);
                x = event.x;
            }
            root = add(root, event.start, event.end, event.align);
        }
    }

    private Node buildEmptyTree(int l, int r) {
        if (l + 1 == r) {
            return new Node(null, null, l, r, 0);
        }
        int middle = (l + r) / 2;
        Node left = buildEmptyTree(l, middle);
        Node right = buildEmptyTree(middle, r);
        return new Node(left, right, left.l, right.r, left.sum + right.sum);
    }

    private Node add(Node root, int l, int r, int val) {
        if (l <= root.l && r >= root.r) {
            return new Node(root.left, root.right, root.l, root.r, root.sum + val);
        }
        if (root.l >= r || root.r <= l) {
            return root;
        }
        Node newNode = new Node(root.left, root.right, root.l, root.r, root.sum);
        newNode.left = add(newNode.left, l, r, val);
        newNode.right = add(newNode.right, l, r, val);
        return newNode;
    }

    public int search(int x, int q) {
        return searchInTree(nodes.get(x), q);
    }

    private int searchInTree(Node root, int q) {
        if (root == null) return 0;
        int m = (root.l + root.r) / 2;
        if (q < m) {
            return root.sum + searchInTree(root.left, q);
        } else return root.sum + searchInTree(root.right, q);
    }
}

class BinarySearch {
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

class PersistentSegmentTreeSolution implements Solution {
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

