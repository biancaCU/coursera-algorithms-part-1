import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;
import java.util.ArrayDeque;
import java.util.Deque;

public class Solver {

    private MinPQ<Node> minPq;
    private MinPQ<Node> minPqTwin;
    private Node lastNode;
    private boolean solvable;
    private Comparator<Node> nodeComparator = new Comparator<Node>() {
        @Override
        public int compare(Node a, Node b) {
            return a.board.manhattan() + a.moves - b.board.manhattan() - b.moves;
        }
    };

    private class Node {
        private Board board;
        private Node prevNode;
        private int moves;

        public Node (Board board, Node prevNode) {
            this.board = board;
            this.prevNode = prevNode;
            this.moves = prevNode == null ? 0 : prevNode.moves+1;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        solvable = false;
        lastNode = null;

        minPq = new MinPQ<Node>(4, nodeComparator);
        minPqTwin = new MinPQ<Node>(4, nodeComparator);

        minPq.insert(new Node(initial, null));
        minPqTwin.insert(new Node(initial.twin(), null));

        Node node = minPq.delMin();
        Node nodeTwin = minPqTwin.delMin();

        while (!node.board.isGoal() && !nodeTwin.board.isGoal()) {
            for (Board b : node.board.neighbors()) {
                if (node.prevNode == null || (!b.equals(node.prevNode.board) && !b.equals(initial))) {
                    Node neighbor = new Node(b, node);
                    minPq.insert(neighbor);
                }
            }
            for (Board bTwin : nodeTwin.board.neighbors()) {
                if (nodeTwin.prevNode == null || (!bTwin.equals(nodeTwin.prevNode.board) && !bTwin.equals(initial))) {
                    Node neighbor = new Node(bTwin, nodeTwin);
                    minPqTwin.insert(neighbor);
                }
            }

            node = minPq.delMin();
            nodeTwin = minPqTwin.delMin();
        }

        if (node.board.isGoal()) {
            solvable = true;
            lastNode = node;
        }

    }
    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable() || lastNode == null) return -1;

        return lastNode.moves;
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Deque<Board> d = new ArrayDeque<Board>();
        Node node = lastNode;
        d.push(node.board);

        while (node.prevNode != null) {
            d.push(node.prevNode.board);
            node = node.prevNode;
        }
        return d;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}