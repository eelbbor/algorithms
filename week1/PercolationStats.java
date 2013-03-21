public class PercolationStats {
    private Percolation percolation;
    private int N = -1;
    private int[] openSiteCount = null;
    private double confidenceMultiplier = -1.0;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T parameters must"
                    + " be greater than 0.");
        }
        this.N = N;
        openSiteCount = new int[T];
        confidenceMultiplier = 1.96 / Math.sqrt(T);
        int openCount = 0;
        System.out.print("Executing Test: ");
        for (int test = 0; test < T; test++) {
            System.out.print(".");
            percolation = new Percolation(N);
            while (!percolation.percolates()) {
                openNewSite();
                openCount++;
            }
            openSiteCount[test] = openCount;
            openCount = 0;
        }
        System.out.println();
    }

    private void openNewSite() {
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
            return mean() - confidenceMultiplier * stddev();
        }
        return Double.NaN;
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        if (this.N > 1) {
            return mean() + confidenceMultiplier * stddev();
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

