public class Percolation {
    private int N;
    private boolean percolates = false;
    private boolean[] isSiteOpenArray;
    private boolean[] isRowOpenArray;
    private WeightedQuickUnionUF quickFindUF;

    //create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        this.N = N;
        isSiteOpenArray = new boolean[N * N];
        isRowOpenArray = new boolean[N * N];
        quickFindUF = new WeightedQuickUnionUF(N * N);
    }

    //open site (row i, column j) if it is not already
    public void open(int i, int j) {
        int p = getLinearIndex(i, j);
        isSiteOpenArray[p] = true;
        isRowOpenArray[i - 1] = true;
        if (i > 1 && isSiteOpenArray[p - N]) { //union with above
            quickFindUF.union(p, p - N);
        }
        if (j > 1 && isSiteOpenArray[p - 1]) { //union with left
            quickFindUF.union(p, p - 1);
        }
        if (j < N && isSiteOpenArray[p + 1]) { //union with right
            quickFindUF.union(p, p + 1);
        }
        if (i < N && isSiteOpenArray[p + N]) { //union with below
            quickFindUF.union(p, p + N);
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        return isSiteOpenArray[getLinearIndex(i, j)];
    }

    //is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        int linearIndex = getLinearIndex(i, j);
        if (isAllRowsOpenAbove(i)) {
            if (isSiteOpenArray[linearIndex]) {
                return isConnectedToTop(linearIndex);
            }
        }
        return false;
    }

    private boolean isConnectedToTop(int linearIndex) {
        for (int q = 0; q < N; q++) {
            if (isSiteOpenArray[q] && quickFindUF.connected(linearIndex, q)) {
                return true;
            }
        }
        return false;
    }

    // does the system percolate?
    public boolean percolates() {
        if (percolates) {
            return true;
        } else if (isAllRowsOpenAbove(N)) {
            int maxIndex = N * N - 1;
            for (int p = maxIndex; p > maxIndex - N; p--) {
                if (isSiteOpenArray[p] && isConnectedToTop(p)) {
                    percolates = true;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isAllRowsOpenAbove(int rowNum) {
        for (int row = 0; row < rowNum; row++) {
            if (!isRowOpenArray[row]) {
                return false;
            }
        }
        return true;
    }

    private int getLinearIndex(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            String errorMsg = "Rows and column indices must be between 1 and "
                    + N;
            throw new IndexOutOfBoundsException(errorMsg);
        }
        return (i - 1) * N + j - 1;
    }
}
