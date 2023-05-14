import java.util.*;

class MCTS {
    private Board rootState;
    private Node root;
    long runTime;
    private int nodeCount;
    int numRollouts;

    public MCTS() {
        this.rootState = Board.getInstance();
        this.root = new Node(0, null);
        this.runTime = 0;
        this.nodeCount = 0;
        this.numRollouts = 0;
    }

    public Node selectNode() {
        Node node = root;
        Board state = rootState;

        while (node.children.size() != 0) {
            ArrayList<Node> children = new ArrayList<>((Collection) node.children);
            double maxValue = children.stream()
                    .mapToDouble(Node::value)
                    .max()
                    .orElseThrow(NoSuchElementException::new);
            List<Node> maxNodes = children.stream()
                    .filter(n -> n.value() == maxValue)
                    .toList();

            node = maxNodes.get(new Random().nextInt(maxNodes.size()));
            state.makeMove(node.move, state.getTurn());

            if (node.N == 0) {
                return node;
            }
        }

        if (expand(node, state)) {
            List<Node> childNodes = new ArrayList<>(node.children);
            node = childNodes.get(new Random().nextInt(childNodes.size()));
            state.makeMove(node.move, state.getTurn());
        }

        return node;
    }

    private boolean expand(Node parent, Board state) {
        if (state.gameOver()) {
            return false;
        }

        ArrayList<Integer> legalMoves = state.legalMoves();
        ArrayList<Node> children = new ArrayList<>();
        for (int move : legalMoves) {
            Node childNode = new Node(move, parent);
            children.add(childNode);
        }

        parent.add_children(children);
        return true;
    }

    public int rollOut(Board state) {
        while (!state.gameOver()) {
            ArrayList<Integer> legalMoves = state.legalMoves();
            int randomMove = legalMoves.get(new Random().nextInt(legalMoves.size()));
            state.makeMove(randomMove, state.getTurn());
        }

        return state.evaluate();
    }

    public void backPropagate(Node node, int turn, int outcome) {
        int reward = (outcome == turn)? 0 : 1; // TODO idk what this does lmao

        while (node != null) {
            node.N = node.N + 1;
            node.Q = node.Q + reward;
            node = node.parent;

            if (outcome == 0) {
                reward = 0;
            } else {
                reward = 1 - reward;
            }
        }
    }

    public void search(int timeLimit) {
        long startTime = System.currentTimeMillis();

        int numRollouts = 0;
        while (System.currentTimeMillis() - startTime < timeLimit) {
            Node node = selectNode();
            Board state = rootState; // TODO in original it called state below without making state so i guessed this
            int outcome = rollOut(state);
            int to_play;
            if (state.getTurn()) to_play = 1; else to_play = -1;
            backPropagate(node, to_play, outcome);
            numRollouts++;
        }

        long runTime = System.currentTimeMillis() - startTime;
        this.runTime = runTime;
        this.numRollouts = numRollouts;
    }

    public int bestMove() {
        if (rootState.gameOver()) {
            return -1; // Assuming Move class has a constructor that takes an integer argument
        }

        double maxN = root.children.stream()
                .mapToDouble(Node::getN)
                .max()
                .orElseThrow(NoSuchElementException::new);
        List<Node> maxNodes = root.children.stream()
                .filter(n -> n.N == maxN)
                .toList();

        Node bestChild = maxNodes.get(new Random().nextInt(maxNodes.size()));
        return bestChild.move;
    }

    public void move(int move) {
        rootState.makeMove(move, rootState.getTurn());
        for (Node child: root.children) {
            if (child.move == move) {
                root = child;
                return;
            }
        }
        rootState.makeMove(move, rootState.getTurn());
        root = new Node(0, null);

    }

}