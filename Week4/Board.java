import edu.princeton.cs.algs4.*;

public class Board {
    private final int[][] tiles;
    private final int n;


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        int n = tiles.length;
        assert n < 2 || n >= 128: " Out of bound ";

        this.n = n;

        this.tiles = new int[n][n];

        for (int i = 0; i < n; i++) {
            assert tiles[i].length != n : " Out of bound ";
            for (int j = 0; j < n; j++) {
                assert tiles[i][j] < 0 || tiles[i][j] > n*n-1: "Out of integers ";
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }


    // string representation of this board
    public String toString()
    {
        StringBuilder result = new StringBuilder(Integer.toString(n) + "\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                result.append(tiles[i][j]).append(" ");
            result.append("\n");
        }

        return result.toString();
    }


    // board dimension n
    public int dimension()
    { return n; }


    // number of tiles out of place
    public int hamming()
    {
        int total = 0;
        int pos = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != pos)
                        total++;
                pos++;
            }
        }

        return total;
    }


    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {
        // Solution: abs(x2-x1) + abs(y2-y1) where x: row, y: col
        int total = 0;
        int pos = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != pos) {
                    int actual = tiles[i][j] - 1;
                    int x = actual / n;
                    int y = actual % n;

                    total += Math.abs(x-i) + Math.abs(y-j);
                }
                pos++;
            }
        }

        return total;
    }


    // is this board the goal board?
    public boolean isGoal()
    {
        return hamming() == 0;
    }


    // does this board equal y?
    public boolean equals(Object y)
    {
        if (this == y) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass())
            return false;

        Board that = (Board) y;
        if (this.n != that.n) return false;

        for (int i = 0; i < n; i++) {
            if (this.tiles[i].length != that.tiles[i].length) return false;
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j])
                    return false;
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        Queue<Board> neighbors = new Queue<>();

        int row = 0, col = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    row = i; col = j;
                }
            }
        }

        if (row > 0)
            neighbors.enqueue(findNeighbors(row, col, row-1, col));

        if (row < n-1)
            neighbors.enqueue(findNeighbors(row, col, row+1, col));

        if (col > 0)
            neighbors.enqueue(findNeighbors(row, col, row, col-1));

        if (col < n-1)
            neighbors.enqueue(findNeighbors(row, col, row, col+1));

        return neighbors;
    }


    private Board findNeighbors(int bRow, int bCol, int nRow, int nCol)
    {
        Board neighbor = new Board(tiles);
        neighbor.tiles[nRow][nCol] = tiles[bRow][bCol];
        neighbor.tiles[bRow][bCol] = tiles[nRow][nCol];

        return neighbor;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin()     // Randomly pick two blocks to swap
    {
        int origin, twin, bRow, bCol, nRow, nCol;

        do {
            origin = StdRandom.uniform(0, n*n-1);
            bRow = origin/n;
            bCol = origin%n;

            twin = StdRandom.uniform(0, n*n-1);
            nRow = twin/n;
            nCol = twin%n;

        } while (tiles[bRow][bCol] == 0 || tiles[nRow][nCol] == 0 || origin == twin);

        return findNeighbors(bRow, bCol, nRow, nCol);
    }


    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();

        int[][] tiles = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();

        Board initial = new Board(tiles);
        StdOut.println(initial.toString());
        StdOut.println("hamming: " + initial.hamming());
        StdOut.println("manhattan: " + initial.manhattan());
        StdOut.println("Is Goal: " + initial.isGoal());

        Board that = new Board(tiles);
        StdOut.println("Equal: " + initial.equals(that));

        for (Board item: initial.neighbors())
            StdOut.println(item.toString());

        StdOut.println("Twin: " + initial.twin());
    }
}