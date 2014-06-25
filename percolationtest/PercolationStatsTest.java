import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class PercolationStatsTest {
    public void testOneByOnePercolation() {
        PercolationStats percolationStats = new PercolationStats(1, 100);
        Assert.assertEquals(percolationStats.mean(), 1.0);
        Assert.assertEquals(percolationStats.stddev(), Double.NaN);
        Assert.assertEquals(percolationStats.confidenceLo(), Double.NaN);
        Assert.assertEquals(percolationStats.confidenceHi(), Double.NaN);
    }

    public void shouldNotThrowIllegalArgumentExceptionForNAndTGreaterThanZero() {
        try {
            new PercolationStats(1, 1);
        } catch (IllegalArgumentException e) {
            Assert.fail("should not have thrown illegal argument exception");
        }
    }

    public void shouldThrowIllegalArgumentExceptionForNEqualZero() {
        try {
            new PercolationStats(0, 1);
            Assert.fail("should have thrown illegal argument exception");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    public void shouldThrowIllegalArgumentExceptionForNLessThanZero() {
        try {
            new PercolationStats(-1, 1);
            Assert.fail("should have thrown illegal argument exception");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    public void shouldThrowIllegalArgumentExceptionForTEqualZero() {
        try {
            new PercolationStats(1, 0);
            Assert.fail("should have thrown illegal argument exception");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    public void shouldThrowIllegalArgumentExceptionForTLessThanZero() {
        try {
            new PercolationStats(1, -1);
            Assert.fail("should have thrown illegal argument exception");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }
}