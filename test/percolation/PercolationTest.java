import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class PercolationTest {
    public void shouldThrowExceptionForNLessThanOne() {
        try {
            new Percolation(0);
            Assert.fail("should have thrown exception");
        } catch (IllegalArgumentException e) {

        }
    }

    public void shouldOpenSite() {
        Percolation grid = new Percolation(10);
        grid.open(5, 5);
        Assert.assertTrue(grid.isOpen(5, 5));
    }

    public void shouldSetTopSiteFull() {
        Percolation grid = new Percolation(10);
        grid.open(1, 5);
        Assert.assertTrue(grid.isFull(1, 5));
    }

    public void shouldPercolateForNOfOne() {
        Percolation grid = new Percolation(1);
        grid.open(1, 1);
        Assert.assertTrue(grid.isOpen(1, 1));
        Assert.assertTrue(grid.isFull(1, 1));
        Assert.assertTrue(grid.percolates());
    }

    public void shouldPercolateForNOfTwo() {
        int N = 2;
        Percolation grid = getPercolationWIthPercolationColumn(N, 1);
        Assert.assertTrue(grid.percolates());
    }

    public void shouldNotPercolateForNOfTwo() {
        int N = 2;
        Percolation grid = new Percolation(N);
        for (int i = 1; i <= N; i++) {
            grid.open(i, i);
        }
        Assert.assertFalse(grid.percolates());
    }

    public void shouldPercolateForNOfThree() {
        int N = 3;
        Percolation grid = getPercolationWIthPercolationColumn(N, 1);
        Assert.assertTrue(grid.percolates());
    }

    public void shouldNotBackwashForNOfThree() {
        int N = 3;
        Percolation grid = getPercolationWIthPercolationColumn(N, 1);
        Assert.assertTrue(grid.percolates());
        grid.open(N, N);
        Assert.assertFalse(grid.isFull(N, N));
    }

    private Percolation getPercolationWIthPercolationColumn(int n, int col) {
        Percolation grid = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            grid.open(i, col);
        }
        return grid;
    }
}