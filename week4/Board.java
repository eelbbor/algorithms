public class Board {
    private int emptyIndex;
    private int[] blocks;
    private int N;

    public Board(int[][] blocks) {
        N = blocks.length;
        this.blocks = new int[N * N];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                this.blocks[getLinearIndex(i, j)] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    emptyIndex = getLinearIndex(i, j);
                }
            }
        }
    }

    private Board(int[] blocks, int N, int emptyIndex) {
        this.blocks = blocks;
        this.N = N;
        this.emptyIndex = emptyIndex;
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        int priority = 0;
        for (int i = 1; i < blocks.length; i++) {
            if (blocks[i - 1] != i) {
                priority++;
            }
        }
        return priority;
    }

    public int manhattan() {
        int priorty = 0;
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] != i + 1 && blocks[i] > 0) {
                priorty += distanceFromHome(i, blocks[i]);
            }
        }
        return priorty;
    }

    private int distanceFromHome(int index, int value) {
        int[] currentPos = getRowColFromIndex(index);
        int[] expectedPos = getRowColFromIndex(value - 1);
        return Math.abs(currentPos[0] - expectedPos[0])
                + Math.abs(currentPos[1] - expectedPos[1]);
    }

    private int[] getRowColFromIndex(int index) {
        int row = index / dimension();
        int col = index - (dimension() * row);
        return new int[]{row, col};
    }

    public boolean isGoal() {
        if (emptyIndex != blocks.length - 1) {
            return false;
        }
        for (int i = 1; i < blocks.length; i++) {
            if (blocks[i - 1] != i) {
                return false;
            }
        }
        return true;
    }

    public Board twin() {
        int startIndex = blocks.length / 2;
        if (blocks[startIndex] == 0 || blocks[startIndex + 1] == 0) {
            startIndex -= dimension();
        }
        return new Board(getNewBlocks(startIndex, startIndex + 1),
                dimension(), emptyIndex);
    }

    public boolean equals(Object y) {
        if (y instanceof Board) {
            for (int i = 0; i < blocks.length; i++) {
                if (blocks[i] != ((Board) y).blocks[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private int getLinearIndex(int i, int j) {
        return N * i + j;
    }

    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<Board>();

        //move above
        if (emptyIndex >= dimension()) {
            neighbors.enqueue(new Board(getNewBlocks(emptyIndex - dimension()),
                    dimension(), emptyIndex - dimension()));
        }
        //move below
        if (emptyIndex + dimension() < blocks.length) {
            neighbors.enqueue(new Board(getNewBlocks(emptyIndex + dimension()),
                    dimension(), emptyIndex + dimension()));
        }
        //move left
        if (emptyIndex > 0
                && (emptyIndex - 1) / dimension() == emptyIndex / dimension()) {
            neighbors.enqueue(new Board(getNewBlocks(emptyIndex - 1),
                    dimension(), emptyIndex - 1));
        }
        //move right
        if ((emptyIndex + 1) / dimension() == emptyIndex / dimension()) {
            neighbors.enqueue(new Board(getNewBlocks(emptyIndex + 1),
                    dimension(), emptyIndex + 1));
        }
        return neighbors;
    }

    private int[] getNewBlocks(int newEmptyIndex) {
        return getNewBlocks(emptyIndex, newEmptyIndex);
    }

    private int[] getNewBlocks(int index1, int index2) {
        int[] newBlocks = new int[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            newBlocks[i] = blocks[i];
        }
        newBlocks[index1] = blocks[index2];
        newBlocks[index2] = blocks[index1];
        return newBlocks;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int padding = ((Integer) (blocks.length - 1)).toString().length() + 1;
        String formatString = "%" + padding + "d ";
        s.append(dimension() + "\n");
        for (int i = 0; i < blocks.length; i++) {
            s.append(String.format(formatString, blocks[i]));
            if ((i + 1) % dimension() == 0) {
                s.append("\n");
            }
        }
        return s.toString();
    }
}
