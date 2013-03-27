public class PercolationStats {
    private int N = -1;
    private int T = -1;
    private int[] openSiteCount = null;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T parameters must"
                    + " be greater than 0.");
        }
        this.N = N;
        this.T = T;
        openSiteCount = new int[T];
        int openCount = 0;
        Percolation percolation = null;
        for (int test = 0; test < T; test++) {
            percolation = new Percolation(N);
            while (!percolation.percolates()) {
                openNewSite(percolation);
                openCount++;
            }
            percolation = null;
            openSiteCount[test] = openCount;
            openCount = 0;
        }
    }

    private void openNewSite(Percolation percolation) {
        int i = StdRandom.uniform(N) + 1;
        int j = StdRandom.uniform(N) + 1;
        while (percolation.isOpen(i, j)) {
            i = StdRandom.uniform(N) + 1;
            j = StdRandom.uniform(N) + 1;
        }
        percolation.open(i, j);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openSiteCount) / (N * N);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (this.N > 1) {
            return StdStats.stddev(openSiteCount) / (N * N);
        }
        return Double.NaN;
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        if (this.N > 1) {
            return mean() - 1.96 * stddev() / Math.sqrt(T);
        }
        return Double.NaN;
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        if (this.N > 1) {
            return mean() + 1.96 * stddev() / Math.sqrt(T);
        }
        return Double.NaN;
    }

    // test client, described below
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]),
                Integer.parseInt(args[1]));
        System.out.println(String.format("mean                    = %s",
                stats.mean()));
        System.out.println(String.format("stddev                  = %s",
                stats.stddev()));
        System.out.println(String.format("95%% confidence interval = %s, %s",
                stats.confidenceLo(), stats.confidenceHi()));
    }
}

