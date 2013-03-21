public class Percolation {
    private static final int OPEN = 1;

    private int N;
    private int[] siteState;
    private WeightedQuickUnionUF quickFindUF;

    //create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        this.N = N;
        siteState = new int[N * N];
        quickFindUF = new WeightedQuickUnionUF(N * N);
    }

    //open site (row i, column j) if it is not already
    public void open(int i, int j) {
        int p = getLinearIndex(i, j);
        siteState[p] = OPEN;
        if (i > 1 && checkOpen(p - N)) { //union with above
            quickFindUF.union(p, p - N);
        }
        if (i < N && checkOpen(p + N)) { //union with below
            quickFindUF.union(p, p + N);
        }
        if (j > 1 && checkOpen(p - 1)) { //union with left
            quickFindUF.union(p, p - 1);
        }
        if (j < N && checkOpen(p + 1)) { //union with right
            quickFindUF.union(p, p + 1);
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        return checkOpen(getLinearIndex(i, j));
    }

    private boolean checkOpen(int linearIndex) {
        return siteState[linearIndex] == OPEN;
    }

    //is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        return checkFull(getLinearIndex(i, j));
    }

    private boolean checkFull(int linearIndex) {
        if (checkOpen(linearIndex)) {
            return isConnectedToTop(linearIndex);
        }
        return false;
    }

    private boolean isConnectedToTop(int linearIndex) {
        for (int q = 0; q < N; q++) {
            if (siteState[q] == OPEN && quickFindUF.connected(linearIndex, q)) {
                return true;
            }
        }
        return false;
    }

    // does the system percolate?
    public boolean percolates() {
        int maxIndex = N * N - 1;
        for (int p = maxIndex; p > maxIndex - N; p--) {
            if (checkFull(p)) {
                return true;
            }
        }
        return false;
    }

    private int getLinearIndex(int i, int j) {
        if (i < 1 || i > N || j < 0 || j > N) {
            String errorMsg = "Rows and column indices must be between 1 and "
                    + N;
            throw new IndexOutOfBoundsException(errorMsg);
        }
        return (i - 1) * N + j - 1;
    }
}
