public class Solver {
    private SearchNode solutionNode;

    public Solver(Board initial) {
        solutionNode = null;
        MinPQ<SearchNode> bq = new MinPQ<SearchNode>(1);
        bq.insert(new SearchNode(null, initial));

        MinPQ<SearchNode> tq = new MinPQ<SearchNode>(1);
        tq.insert(new SearchNode(null, initial.twin()));

        while (!bq.isEmpty() && !tq.isEmpty()) {
            //process bq
            solutionNode = processNextNode(bq);
            if (solutionNode != null) {
                break;
            }

            //process tq
            solutionNode = processNextNode(tq);
            if (solutionNode != null) {
                solutionNode = null;
                break;
            }
        }
    }

    private SearchNode processNextNode(MinPQ<SearchNode> searchNodes) {
        SearchNode node = searchNodes.delMin();
        if (node.board.isGoal()) {
            return node;
        }
        for (Board board : node.board.neighbors()) {
            if (node.parent == null || !node.parent.board.equals(board)) {
                searchNodes.insert(new SearchNode(node, board));
            }
        }
        return null;
    }

    public boolean isSolvable() {
        return solutionNode != null;
    }

    public int moves() {
        if (isSolvable()) {
            return solutionNode.moves;
        }
        return -1;
    }

    public Iterable<Board> solution() {
        Stack<Board> solution = null;
        if (isSolvable()) {
            solution = new Stack<Board>();
            SearchNode node = solutionNode;
            while (node != null) {
                solution.push(node.board);
                node = node.parent;
            }
        }
        return solution;
    }

    public static void main(String[] args) {
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

    private class SearchNode implements Comparable<SearchNode> {
        private SearchNode parent;
        private Board board;
        private int moves;

        private SearchNode(SearchNode parent, Board board) {
            this.parent = parent;
            this.board = board;
            if (parent != null) {
                moves = 1 + parent.moves;
            } else {
                moves = 0;
            }
        }

        protected int getPriority() {
            return board.manhattan() + moves;
        }

        @Override
        public int compareTo(SearchNode o) {
            return getPriority() - o.getPriority();
        }
    }
}
