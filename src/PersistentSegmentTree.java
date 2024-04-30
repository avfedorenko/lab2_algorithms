import java.util.ArrayList;
import java.util.List;

public class PersistentSegmentTree {
    List<Node> nodes = new ArrayList<>();

    public PersistentSegmentTree(List<Event> events, int size) {
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
        if (root == null){
            return 0;
        }
        int m = (root.l + root.r) / 2;
        if (q < m) {
            return root.sum + searchInTree(root.left, q);
        } else return root.sum + searchInTree(root.right, q);
    }
}