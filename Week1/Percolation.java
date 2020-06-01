import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Random;

public class Percolation {
    private boolean [] grid;

    private int n;
    private int numOfOpen;
    private WeightedQuickUnionUF model;

    // Creates n-by-n grid, with all sites initial blocked
    public Percolation(int n) {
        // Takes time n^2
        if (n <= 0)
            throw new IllegalArgumentException("n <= 0");
        this.n = n;
        numOfOpen = 0;
        this.model = new WeightedQuickUnionUF(n*n);
        grid = new boolean[n*n];
    }

    // Exception if row & col are out of range
    public boolean outOfRange(int row, int col) {
        return row < 1 || col < 1 || row > n || col > n;
    }

    public boolean connected(int p, int q) {
        return model.find(p) == model.find(q);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (outOfRange(row, col))
            throw new IllegalArgumentException("Out of Range");

        if (isOpen(row,col)) // Already open
            return;

        if (numbeOfOpenSites() > n*n) // max to open
            return;

        int cur = n*(row-1)+col-1;

        int left = cur-1;
        int right = cur+1;
        int above = n*(row-2)+col-1;
        int below = n*row+col-1;

        grid[cur] = true;

        /**
         *      1 2 3 4     n = 4 => (4x4) grid
         *      -------
         *  1 | a b c d
         *  2 | e f g h
         *  3 | i j k l
         *  4 | m n o p
         *
         *  Top (row = 1):
         *      if col = 1, check its right
         *      if col = 4, check its left
         *      if 1 < col < 4, check its left, right
         *
         *  Middle (1 < row < 4):
         *      if col = 1, check its above, right, below
         *      if col = n, check its above, left, below
         *      if 1 < col < 4, check all around of it
         *
         *  Bottom (row = n):
         *      if col = 1, check its above, right
         *      if col = n, check its above, left,
         *      if 1 < col < 4, check its above, left, right
         */

        if (row == 1) { // top row
            if (col == 1) {
                if (isOpen(row, col + 1))   // right
                    model.union(cur, right);
            }
            else if (col == n) {
                if (isOpen(row, col-1))     // left
                    model.union(cur, left);
            }
            else if (col > 1 && col < n) {
                if (isOpen(row, col-1))     // left
                    model.union(cur,left);

                if (isOpen(row, col+1))     // right
                    model.union(cur,right);
            }
        }
        else if (row > 1 && row < n) { // middle row
            if (col == 1) {
                if (isOpen(row-1, col))     // above
                    model.union(cur, above);
                if (isOpen(row, col+1))     // right
                    model.union(cur, right);
                if (isOpen(row+1, col))     // below
                    model.union(cur, below);
            }
            else if (col == n) {
                if (isOpen(row-1, col))     // above
                    model.union(cur, above);
                if (isOpen(row, col-1))     // left
                    model.union(cur, left);
                if (isOpen(row+1, col))     // below
                    model.union(cur, below);
            }
            else if (col > 1 && col < n) {
                if (isOpen(row-1, col)) // above
                    model.union(cur, above);
                if (isOpen(row, col-1)) // left
                    model.union(cur, left);
                if (isOpen(row+1, col)) // below
                    model.union(cur, below);
                if (isOpen(row, col+1)) // right
                    model.union(cur, right);
            }
        }
        else {  // bottom row
            if (col == 1) {
                if (isOpen(row-1, col)) // above
                    model.union(cur, above);
                if (isOpen(row, col+1)) // right
                    model.union(cur, right);
            }
            else if (col == n) {
                if (isOpen(row-1, col)) // above
                    model.union(cur, above);
                if (isOpen(row, col-1)) // left
                    model.union(cur, left);
            }
            else if (col > 1 && col < n) {
                if (isOpen(row-1, col)) // above
                    model.union(cur, above);
                if (isOpen(row, col-1)) // left
                    model.union(cur, left);
                if (isOpen(row, col+1)) // right
                    model.union(cur, right);
            }
        }

        numOfOpen++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (outOfRange(row, col))
            throw new IllegalArgumentException("Out of Range");
        return grid[n*(row-1)+col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (outOfRange(row, col))
            throw new IllegalArgumentException("Out of Range");

        for (int i = 1; i <= n; i++) {
            if (connected(n*(row-1)+col-1, i))
                return true;
        }
        return false;
    }

    //  returns the number of open sites
    public int numbeOfOpenSites() {
        return numOfOpen;
    }

    // does the system percolate
    public boolean percolates() {
        for (int col = 1; col <= n; col++) {
            if (isFull(n, col))
                return true;
        }
        return false;
    }

    public void getGrid() { // print the simulator
        int[][] result = new int[n][n];

        int i = 0;
        for(int r = 0; r < n; r++){
            for( int c = 0; c < n; c++){
                if (grid[i++])
                    result[r][c] = 1;
            }
        }

        for(int r = 0; r < n; r++){
            for( int c = 0; c < n; c++)
                System.out.print(result[r][c] + " ");

            System.out.println();
        }
    }

    public static void main(String args[]) {
        int n = 10;
        Percolation p = new Percolation(n);
        Random rand = new Random();

        while (!p.percolates()) {
            int row = rand.nextInt((n - 1) + 1) + 1;
            int col = rand.nextInt((n - 1) + 1) + 1;
            p.open(row,col);
        }

        p.getGrid();

    }
}
