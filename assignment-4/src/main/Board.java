import java.util.Stack;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private int[][] blocks;
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        int N = blocks.length;
        this.blocks = new int[N][N];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }
    // board dimension n
    public int dimension() {
        return blocks[0].length;
    }
    // number of blocks out of place
    public int hamming() {
        int wrong = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                int expected;
                if (i == blocks.length-1 && j == i) {
                    expected = 0;
                } else {
                    expected = i*dimension()+(j+1);
                }
                if (expected != 0 && blocks[i][j] != expected) wrong++;
            }
        }
        return wrong;
    }
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                int val = blocks[i][j];
                int row; int col;
                if (val != 0) {
                    row = (val - 1) / dimension();
                    col = val - (row * dimension()) - 1;
                    // System.out.println("i=" + i + " j=" + j + " val=" + val + " ," + row + " " + col);
                    sum += Math.abs(i - row); // vertical
                    sum += Math.abs(j - col); // horizontal
                }
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                int expected;
                if (i == blocks.length-1 && j == i) {
                    expected = 0;
                } else {
                    expected = i*dimension()+(j+1);
                }
                if  (blocks[i][j] != expected) return false;
            }
        }
        return true;
    }
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        // find two random coordinates that don't map to 0, or empty block
        int i1 = StdRandom.uniform(dimension());
        int j1 = StdRandom.uniform(dimension());
        while (blocks[i1][j1] == 0) {
            i1 = StdRandom.uniform(dimension());
            j1 = StdRandom.uniform(dimension());
        }
        int i2 = StdRandom.uniform(dimension());
        int j2 = StdRandom.uniform(dimension());
        while (blocks[i2][j2] == 0 || blocks[i2][j2] == blocks[i1][j1]) {
            i2 = StdRandom.uniform(dimension());
            j2 = StdRandom.uniform(dimension());
        }
        int[][] twin = copyBlocks();
        // swap
        int temp = twin[i1][j1];
        twin[i1][j1] = twin[i2][j2];
        twin[i2][j2] = temp;
        return new Board(twin);
    }
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension()) return false;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (that.blocks[i][j] != this.blocks[i][j]) return false;
            }
        }
        return true;
    }
    // all neighboring boards
    public Iterable<Board> neighbors() {
        int[] empty = findEmptyBlock();
        Stack<Board> stack = new Stack<Board>();
        // top
        if (empty[0] > 0) {
            int[][] top = copyBlocks();
            top[empty[0]][empty[1]] = blocks[empty[0]-1][empty[1]];
            top[empty[0]-1][empty[1]] = 0;
            stack.push(new Board(top));
        }
        // bottom
        if (empty[0] < dimension()-1) {
            int[][] bottom = copyBlocks();
            bottom[empty[0]][empty[1]] = blocks[empty[0]+1][empty[1]];
            bottom[empty[0]+1][empty[1]] = 0;
            stack.push(new Board(bottom));
        }
        // left
        if (empty[1] > 0) {
            int[][] left = copyBlocks();
            left[empty[0]][empty[1]] = blocks[empty[0]][empty[1]-1];
            left[empty[0]][empty[1]-1] = 0;
            stack.push(new Board(left));
        }
        // right
        if (empty[1] < dimension()-1) {
            int[][] right = copyBlocks();
            right[empty[0]][empty[1]] = blocks[empty[0]][empty[1]+1];
            right[empty[0]][empty[1]+1] = 0;
            stack.push(new Board(right));
        }
        return stack;
    }

    private int[][] copyBlocks() {
        int[][] copy = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                copy[i][j] = blocks[i][j];
            }
        }
        return copy;
    }

    private int[] findEmptyBlock() {
        int[] pos = new int[2];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] == 0) {
                    pos[0] = i;
                    pos[1] = j;
                }
            }
        }
        return pos;
    }
    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    // unit tests (not graded)
    public static void main(String[] args) {

    }
}