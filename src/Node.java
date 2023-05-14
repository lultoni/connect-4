import java.util.ArrayList;

public class Node {
    int move;
    Node parent;
    int N;
    int Q;
    ArrayList<Node> children;
    int outcome;
    Node(int move, Node parent) {
        this.move = move;
        this. parent = parent;
        this.N = 0;
        this.Q = 0;
        this.children = new ArrayList<>(0);
    }

    void add_children(ArrayList<Node> children) {
        for (Node child: children) {
            this.children.set(child.move - 1, child);
        }
    }

    double value() {
        double explore = Math.sqrt(2);
        if (this.N == 0) {
            if (explore == 0) return 0; else return Double.POSITIVE_INFINITY;
        } else {
            return (double) this.Q / this.N + explore * Math.sqrt(Math.log(this.parent.N) / this.N);
        }
    }

    int getN() {
        return N;
    }

}
