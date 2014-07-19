public class Board {
    private int N;
    private int hamming = 0;
    private int manhattan = 0;
    private int emptyRow;
    private int emptyCol;
    private char[] blocks;

    public Board(int[][] blocks) {
        N = blocks.length;
        this.blocks = new char[N * N];
        int linearIndex = 0;
        int value;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                value = blocks[i][j];
                if (value == 0) {
                    emptyRow = i;
                    emptyCol = j;
                } else if (value != linearIndex + 1) {
                    hamming++;
                    manhattan += getOffset(value, i, j);
                }
                this.blocks[linearIndex++] = (char) value;
            }
        }
    }

    private int getEmptyIndex() {
        return getLinearIndex(emptyRow, emptyCol);
    }

    private int getLinearIndex(int i, int j) {
        return N * i + j;
    }

    private int getOffset(int value, int i, int j) {
        int index = value - 1;
        int expectedRow = index / dimension();
        int expectedCol = index - expectedRow * dimension();
        return Math.abs(i - expectedRow) + Math.abs(j - expectedCol);
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return hamming == 0;
    }

    public Board twin() {
        int row = emptyRow + 1;
        if (row == dimension()) {
            row = emptyRow - 1;
        }
        return new Board(getNewBlocks(row, 0, row, 1));
    }

    public boolean equals(Object y) {
        if (y instanceof Board) {
            if (hamming() != ((Board) y).hamming()
                    || getEmptyIndex() != ((Board) y).getEmptyIndex()) {
                return false;
            }
            for (int i = 0; i < blocks.length; i++) {
                if (blocks[i] != ((Board) y).blocks[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<Board>();

        //move above
        if (emptyRow > 0) {
            neighbors.enqueue(new Board(getNeighborBlocks(emptyRow - 1, emptyCol)));
        }

        //move below
        if (emptyRow < dimension() - 1) {
            neighbors.enqueue(new Board(getNeighborBlocks(emptyRow + 1, emptyCol)));
        }

        //move left
        if (emptyCol > 0) {
            neighbors.enqueue(new Board(getNeighborBlocks(emptyRow, emptyCol - 1)));
        }

        //move right
        if (emptyCol < dimension() - 1) {
            neighbors.enqueue(new Board(getNeighborBlocks(emptyRow, emptyCol + 1)));
        }

        return neighbors;
    }

    private int[][] getNeighborBlocks(int newEmptyRow, int newEmptyCol) {
        return getNewBlocks(newEmptyRow, newEmptyCol, emptyRow, emptyCol);
    }

    private int[][] getNewBlocks(int row1, int col1, int row2, int col2) {
        int[][] newBlocks = new int[dimension()][dimension()];
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                newBlocks[i][j] = blocks[getLinearIndex(i, j)];
            }
        }
        int val = newBlocks[row2][col2];
        newBlocks[row2][col2] = newBlocks[row1][col1];
        newBlocks[row1][col1] = val;
        return newBlocks;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int padding = ((Integer) (blocks.length - 1)).toString().length() + 1;
        String formatString = "%" + padding + "d ";
        s.append(dimension() + "\n");
        for (int i = 0; i < blocks.length; i++) {
            s.append(String.format(formatString, (int) blocks[i]));
            if ((i + 1) % dimension() == 0) {
                s.append("\n");
            }
        }
        return s.toString();
    }
}
