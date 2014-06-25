public class Percolation {
    private int N;
    //private int virtualBottom;
    private boolean[] sites;
    private WeightedQuickUnionUF siteUnion;

    public Percolation(int N) {
        if (N < 1) {
            String errorMsg = "N must be greater than or equal to 1";
            throw new IllegalArgumentException(errorMsg);
        }
        this.N = N;
        sites = new boolean[N * N + 1];
        //virtualBottom = sites.length;
        //siteUnion = new WeightedQuickUnionUF(virtualBottom + 1);
        siteUnion = new WeightedQuickUnionUF(sites.length);
    }

    public void open(int i, int j) {
        int p = getLinearIndex(i, j);
        sites[p] = true;
        updateUnionSites(i, j, p);
    }

    private void updateUnionSites(int i, int j, int p) {
        //union virtual top
        if (i == 1) {
            siteUnion.union(0, p);
        }

        //union above
        if (i > 1 && sites[p - N]) {
            siteUnion.union(p, p - N);
        }

        //union below
        if (i < N && sites[p + N]) {
            siteUnion.union(p, p + N);
        }

        //union left
        if (j > 1 && sites[p - 1]) {
            siteUnion.union(p, p - 1);
        }

        //union right
        if (j < N && sites[p + 1]) {
            siteUnion.union(p, p + 1);
        }

        //union virtual bottom
        /*if (i == N) {
            siteUnion.union(virtualBottom, p);
        }*/
    }

    public boolean isOpen(int i, int j) {
        return sites[getLinearIndex(i, j)];
    }

    public boolean isFull(int i, int j) {
        return siteUnion.connected(0, getLinearIndex(i, j));
    }

    private int getLinearIndex(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            String errorMsg = "Rows and column indices must be between 1 and "
                    + N;
            throw new IndexOutOfBoundsException(errorMsg);
        }
        return (i - 1) * N + j;
    }

    public boolean percolates() {
        //return siteUnion.connected(0, virtualBottom);
        int length = sites.length;
        for (int i = 1; i <= N; i++) {
            if (sites[length - i] && siteUnion.connected(0, length - i)) {
                return true;
            }
        }
        return false;
    }
}
