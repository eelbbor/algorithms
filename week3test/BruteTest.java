import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
public class BruteTest {
    public void testBruteMainWith6() {
        String[] args = {"week3test/input6.txt"};
        Brute.main(args);
    }

    public void testBruteMainWith8() {
        String[] args = {"week3test/input8.txt"};
        Brute.main(args);
    }

    public void testWithSinglePointGetsNoResult() {
        Brute brute = new Brute(new Point(0, 0));
        StringBuffer buffer = new StringBuffer();
        brute.processPoints(buffer);
        assertEquals(buffer.length(), 0);
    }

    public void testWithTwoPointGetsNoResult() {
        Brute brute = new Brute(new Point(0, 0)
                , new Point(1, 1));
        StringBuffer buffer = new StringBuffer();
        brute.processPoints(buffer);
        assertEquals(buffer.length(), 0);
    }

    public void testWithThreePointGetsNoResult() {
        Brute brute = new Brute(new Point(0, 0)
                , new Point(1, 1)
                , new Point(2, 2));
        StringBuffer buffer = new StringBuffer();
        brute.processPoints(buffer);
        assertEquals(buffer.length(), 0);
    }

    public void testWithoutCollinearPointGetsNoResult() {
        Brute brute = new Brute(new Point(0, 0)
                , new Point(1, 1)
                , new Point(2, 2)
                , new Point(2, 3));
        StringBuffer buffer = new StringBuffer();
        brute.processPoints(buffer);
        assertEquals(buffer.length(), 0);
    }

    public void testWithFourCollinearPointGetsAResult() {
        Brute brute = new Brute(new Point(0, 0)
                , new Point(1, 1)
                , new Point(2, 2)
                , new Point(3, 3));
        StringBuffer buffer = new StringBuffer();
        brute.processPoints(buffer);
        assertTrue(buffer.length() > 0);
        String[] output = buffer.toString().split("\n");
        assertEquals(output.length, 1);
        assertEquals(output[0], "(0, 0) -> (1, 1) -> (2, 2) -> (3, 3)");
    }

    public void testBruteWith6Points() {
        Brute brute = new Brute(new Point(19000, 10000)
                , new Point(18000, 10000)
                , new Point(32000, 10000)
                , new Point(21000, 10000)
                , new Point(1234, 5678)
                , new Point(14000, 10000));
        StringBuffer buffer = new StringBuffer();
        brute.processPoints(buffer);
        assertTrue(buffer.length() > 0);
        String[] output = buffer.toString().split("\n");
        assertEquals(output.length, 5);
        assertEquals(output[0], "(14000, 10000) -> (18000, 10000) -> (19000, 10000) -> (21000, 10000)");
        assertEquals(output[1], "(14000, 10000) -> (18000, 10000) -> (19000, 10000) -> (32000, 10000)");
        assertEquals(output[2], "(14000, 10000) -> (18000, 10000) -> (21000, 10000) -> (32000, 10000)");
        assertEquals(output[3], "(14000, 10000) -> (19000, 10000) -> (21000, 10000) -> (32000, 10000)");
        assertEquals(output[4], "(18000, 10000) -> (19000, 10000) -> (21000, 10000) -> (32000, 10000)");
    }

    public void testBruteWith8Points() {
        Brute brute = new Brute(new Point(10000, 0)
                , new Point(0, 10000)
                , new Point(3000, 7000)
                , new Point(7000, 3000)
                , new Point(20000, 21000)
                , new Point(3000, 4000)
                , new Point(14000, 15000)
                , new Point(6000, 7000));
        StringBuffer buffer = new StringBuffer();
        brute.processPoints(buffer);
        assertTrue(buffer.length() > 0);
        String[] output = buffer.toString().split("\n");
        assertEquals(output.length, 2);
        assertEquals(output[0], "(10000, 0) -> (7000, 3000) -> (3000, 7000) -> (0, 10000)");
        assertEquals(output[1], "(3000, 4000) -> (6000, 7000) -> (14000, 15000) -> (20000, 21000)");
    }
}
