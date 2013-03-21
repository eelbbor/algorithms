import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test
public class PercolationStatsTest {
    public void testOneByOnePercolation() {
        PercolationStats percolationStats = new PercolationStats(1, 100);
        assertEquals(percolationStats.mean(), 1.0);
        assertEquals(percolationStats.stddev(), Double.NaN);
        assertEquals(percolationStats.confidenceLo(), Double.NaN);
        assertEquals(percolationStats.confidenceHi(), Double.NaN);
    }

    public void testMeanPercolation() {
        int T = 100;
        PercolationStats percolationStats = new PercolationStats(5, T);
        assertTrue(percolationStats.mean() < 1.0);
        assertTrue(percolationStats.stddev() < 1.0);
        assertTrue(percolationStats.confidenceLo() < 1.0);
        assertTrue(percolationStats.confidenceHi() < 1.0);

        double adjustValue = 1.96*percolationStats.stddev()/Math.sqrt(T);
        assertEquals(percolationStats.confidenceLo(), percolationStats.mean() - adjustValue);
        assertEquals(percolationStats.confidenceHi(), percolationStats.mean() + adjustValue);
    }

    public void testIllegalArgumentExceptionIfTIsLessThan1() {
        validateIllegalArgumentException(1, 0);
        validateIllegalArgumentException(1, -1);
    }

    public void testIllegalArgumentExceptionIfNIsLessThan1() {
        validateIllegalArgumentException(0, 1);
        validateIllegalArgumentException(-1, 1);
    }

    private void validateIllegalArgumentException(int N, int T) {
        try {
            new PercolationStats(N, T);
            fail("Should have thrown illegal argument exception");
        } catch (IllegalArgumentException e) {
        }
    }
}
