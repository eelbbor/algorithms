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
        Point[] points = {new Point(0, 0)};
        StringBuffer buffer = new StringBuffer();
//        Brute.processPoints(points, buffer);
        assertEquals(buffer.length(), 0);
    }

    public void testWithTwoPointGetsNoResult() {
        Point[] points = {new Point(0, 0)
                , new Point(1, 1)};
        StringBuffer buffer = new StringBuffer();
//        Brute.processPoints(points, buffer);
        assertEquals(buffer.length(), 0);
    }

    public void testWithThreePointGetsNoResult() {
        Point[] points = {new Point(0, 0)
                , new Point(1, 1)
                , new Point(2, 2)};
        StringBuffer buffer = new StringBuffer();
//        Brute.processPoints(points, buffer);
        assertEquals(buffer.length(), 0);
    }

    public void testWithoutCollinearPointGetsNoResult() {
        Point[] points = {new Point(0, 0)
                , new Point(1, 1)
                , new Point(2, 2)
                , new Point(2, 3)};
        StringBuffer buffer = new StringBuffer();
//        Brute.processPoints(points, buffer);
        assertEquals(buffer.length(), 0);
    }

    public void testWithFourCollinearPointGetsAResult() {
        Point[] points = {new Point(0, 0)
                , new Point(1, 1)
                , new Point(2, 2)
                , new Point(3, 3)};
        StringBuffer buffer = new StringBuffer();
//        Brute.processPoints(points, buffer);
        assertTrue(buffer.length() > 0);
        String[] output = buffer.toString().split("\n");
        assertEquals(output.length, 1);
        assertEquals(output[0], "(0, 0) -> (1, 1) -> (2, 2) -> (3, 3)");
    }

    public void testWithFiveCollinearPointGetsAResult() {
        Point[] points = {new Point(3333, 3333)
                , new Point(1, 1)
                , new Point(99, 99)
                , new Point(34, 34)
                , new Point(27, 27)};
        StringBuffer buffer = new StringBuffer();
//        Brute.processPoints(points, buffer);
        assertTrue(buffer.length() > 0);
        String[] output = buffer.toString().split("\n");
        assertEquals(output.length, 5);
        assertEquals(output[0], "(1, 1) -> (27, 27) -> (34, 34) -> (99, 99)");
        assertEquals(output[1], "(1, 1) -> (27, 27) -> (34, 34) -> (3333, 3333)");
        assertEquals(output[2], "(1, 1) -> (27, 27) -> (99, 99) -> (3333, 3333)");
        assertEquals(output[3], "(1, 1) -> (34, 34) -> (99, 99) -> (3333, 3333)");
        assertEquals(output[4], "(27, 27) -> (34, 34) -> (99, 99) -> (3333, 3333)");
    }

    public void testBruteWith6Points() {
        Point[] points = {new Point(19000, 10000)
                , new Point(18000, 10000)
                , new Point(32000, 10000)
                , new Point(21000, 10000)
                , new Point(1234, 5678)
                , new Point(14000, 10000)};
        StringBuffer buffer = new StringBuffer();
//        Brute.processPoints(points, buffer);
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
        Point[] points = {new Point(10000, 0)
                , new Point(0, 10000)
                , new Point(3000, 7000)
                , new Point(7000, 3000)
                , new Point(20000, 21000)
                , new Point(3000, 4000)
                , new Point(14000, 15000)
                , new Point(6000, 7000)};
        StringBuffer buffer = new StringBuffer();
//        Brute.processPoints(points, buffer);
        assertTrue(buffer.length() > 0);
        String[] output = buffer.toString().split("\n");
        assertEquals(output.length, 2);
        assertEquals(output[0], "(10000, 0) -> (7000, 3000) -> (3000, 7000) -> (0, 10000)");
        assertEquals(output[1], "(3000, 4000) -> (6000, 7000) -> (14000, 15000) -> (20000, 21000)");
    }
}
