import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {
    private List<Board> boardList;
    private List<Board> twinList;

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        boardList = new ArrayList<Board>();
        boardList.add(initial);

        twinList = new ArrayList<Board>();
        twinList.add(initial.twin());

        MinPQ<Board> pq = null;
        Board prevBoard = initial;
        Board prevTwin = getLastBoard(twinList);
        while (!getLastBoard(boardList).isGoal()
                && !getLastBoard(twinList).isGoal()) {
            pq = getBoardPQ();
            for (Board board : getLastBoard(boardList).neighbors()) {
                if (!board.equals(prevBoard)) {
                    pq.insert(board);
                }
            }
            prevBoard = getLastBoard(boardList);
            boardList.add(pq.min());

            pq = getBoardPQ();
            for (Board board : getLastBoard(twinList).neighbors()) {
                if (!board.equals(prevTwin)) {
                    pq.insert(board);
                }
            }
            prevTwin = getLastBoard(twinList);
            twinList.add(pq.min());
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

    private Board getLastBoard(List<Board> list) {
        return list.get(list.size() - 1);
    }

    public boolean isSolvable() {
        return getLastBoard(boardList).isGoal();
    }

    public int moves() {
        if (isSolvable()) {
            return boardList.size() - 1;
        }
        return -1;
    }

    public Iterable<Board> solution() {
        if (isSolvable()) {
            return boardList;
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
