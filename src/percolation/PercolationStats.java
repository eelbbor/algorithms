public class PercolationStats {
    private int N;
    private int T;
    private int[] openSites;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T must be greater than 0");
        }

        this.N = N;
        this.T = T;
        openSites = new int[T];

        Percolation grid;
        for (int test = 0; test < T; test++) {
            grid = new Percolation(N);
            openSites[test] = 0;
            while (!grid.percolates()) {
                openSite(grid);
                openSites[test]++;
            }
            grid = null;
        }
    }

    private void openSite(Percolation grid) {
        int i;
        int j;
        do {
            i = StdRandom.uniform(N) + 1;
            j = StdRandom.uniform(N) + 1;
        } while (grid.isOpen(i, j));
        grid.open(i, j);
    }

    public double mean() {
        return StdStats.mean(openSites) / (N * N);
    }

    public double stddev() {
        if (N > 1) {
            return StdStats.stddev(openSites) / (N * N);
        }
        return Double.NaN;
    }

    public double confidenceLo() {
        if (N > 1) {
            return mean() - 1.96 * stddev() / Math.sqrt(T);
        }
        return Double.NaN;
    }

    public double confidenceHi() {
        if (N > 1) {
            return mean() + 1.96 * stddev() / Math.sqrt(T);
        }
        return Double.NaN;
    }

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