import edu.princeton.cs.algs4.*;

import java.util.Comparator;

public class Solver {
    private int move = 0;
    private final Board board;
    private boolean solved = false;
    private Queue<Board> steps = new Queue<>();
    
    private static class Solve {
        private final int moves;
        private final Board board;
        private final Solve previous;

        public Solve(Board board, int moves, Solve previous) {
            this.moves = moves;
            this.board = board;
            this.previous = previous;
        }

        public int getPriority()
        { return board.manhattan() + moves; }

        public Board getBoard()
        { return board; }

        public Solve getPrevious()
        { return previous; }
    }

    private static class Comp implements Comparator<Solve> {

        @Override
        public int compare(Solve o1, Solve o2) {
            return o1.getPriority() - o2.getPriority();
        }
    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
        if (initial == null) throw new IllegalArgumentException("Illegal");
        this.board = initial;

        MinPQ<Solve> pq = new MinPQ<>(new Comp());
        pq.insert(new Solve(initial, 0, null));

        Solve cur;
        while (!pq.isEmpty()) {
            cur = pq.delMin();
            steps.enqueue(cur.getBoard());

            if (cur.getBoard().isGoal()) {
                solved = true;
                break;
            }
            move++;

            for (Board neighbor: cur.getBoard().neighbors()) {
                if (cur.getBoard().equals(board))       // insert neighbors directly on initial board
                    pq.insert(new Solve(neighbor, move, cur));
                else  if (!neighbor.equals(cur.getPrevious().getBoard()))
                    pq.insert(new Solve(neighbor, move, cur));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable()
    { return solved; }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()
    {
        if (!isSolvable()) return -1;
        return move;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()
    {
        if (!isSolvable()) return null;
        return steps;
    }

    // test client (see below)
    public static void main(String[] args)
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        StdOut.println("Moves: " + solver.moves());

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
