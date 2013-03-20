import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PercolationStats {
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if(N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T parameters must be greater than 0.");
        }
        Percolation percolation = null;
        for(int i = 0; i < T; i++) {
            percolation = new Percolation(N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        throw new NotImplementedException();
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        throw new NotImplementedException();
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        throw new NotImplementedException();
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        throw new NotImplementedException();
    }

    // test client, described below
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }
}

