import java.util.Comparator;

public class Solver {
    private Queue<Board> boardQueue;

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        boardQueue = new Queue<Board>();
        boardQueue.enqueue(initial);
        Board prevBoard = initial;
        Board lastBoard = initial;

        Queue<Board> twinList = new Queue<Board>();
        Board prevTwin = initial.twin();
        Board lastTwin = prevTwin;
        twinList.enqueue(prevTwin);

        MinPQ<Board> pq = null;
        while (!lastBoard.isGoal() && !lastTwin.isGoal()) {
            pq = getBoardPQ();
            for (Board board : lastBoard.neighbors()) {
                if (!board.equals(prevBoard)) {
                    pq.insert(board);
                }
            }
            prevBoard = lastBoard;
            lastBoard = pq.min();
            boardQueue.enqueue(lastBoard);

            pq = getBoardPQ();
            for (Board board : lastTwin.neighbors()) {
                if (!board.equals(prevTwin)) {
                    pq.insert(board);
                }
            }
            prevTwin = lastTwin;
            lastTwin = pq.min();
            twinList.enqueue(lastTwin);
        }
        if (!lastBoard.isGoal()) {
            boardQueue = null;
        }
    }

    private MinPQ<Board> getBoardPQ() {
        return new MinPQ<Board>(4, new Comparator<Board>() {
            @Override
            public int compare(Board board, Board board2) {
                return board.manhattan() - board2.manhattan();
            }
        });
    }

    public boolean isSolvable() {
        return boardQueue != null;
    }

    public int moves() {
        if (isSolvable()) {
            return boardQueue.size() - 1;
        }
        return -1;
    }

    public Iterable<Board> solution() {
        if (isSolvable()) {
            return boardQueue;
        }
        return null;
    }

    public static void main(String[] args) {
        // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
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
