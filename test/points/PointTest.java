import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test
public class PointTest {
    private Point basePoint;
    private int x;
    private int y;

    @BeforeMethod
    protected void setUp() {
        x = 2;
        y = 5;
        basePoint = new Point(x, y);
    }

    public void shouldReturn0IfEqual() {
        assertEquals(basePoint.compareTo(basePoint), 0);
    }

    public void shouldReturnGreaterThanForPointYCoordinateGreaterThanYOfComparePoint() {
        Point comparePoint = new Point(x + 10, y - 5);
        Assert.assertTrue(basePoint.compareTo(comparePoint) > 0);
    }

    public void shouldReturnGreaterThanForPointXCoordinateGreaterThanXOfComparePointWithIdenticalY() {
        Point comparePoint = new Point(x - 10, y);
        Assert.assertTrue(basePoint.compareTo(comparePoint) > 0);
    }

    public void shouldReturnLessThanForPointYCoordinateLessThanYOfComparePoint() {
        Point comparePoint = new Point(x + 10, y + 5);
        Assert.assertTrue(basePoint.compareTo(comparePoint) < 0);
    }

    public void shouldReturnLessThanForPointXCoordinateLessThanXOfComparePointWithIdenticalY() {
        Point comparePoint = new Point(x + 10, y);
        Assert.assertTrue(basePoint.compareTo(comparePoint) < 0);
    }

    public void shouldReturnNegativeInfinityForIdenticalPoint() {
        assertEquals(basePoint.slopeTo(basePoint), Double.NEGATIVE_INFINITY);
    }

    public void shouldReturnPositiveInfinityForVerticalPointAbove() {
        Point comparePoint = new Point(x, y + 10);
        assertEquals(basePoint.slopeTo(comparePoint), Double.POSITIVE_INFINITY);
    }

    public void shouldReturnPositiveInfinityForVertical() {
        Point comparePoint = new Point(x, y - 10);
        assertEquals(basePoint.slopeTo(comparePoint), Double.POSITIVE_INFINITY);
        assertEquals(comparePoint.slopeTo(basePoint), Double.POSITIVE_INFINITY);
    }

    public void shouldReturnZeroForHorizontal() {
        Point comparePoint = new Point(x - 10, y);
        assertEquals(basePoint.slopeTo(comparePoint), 0.0);
        assertEquals(comparePoint.slopeTo(basePoint), 0.0);
    }

    public void shouldReturnComputedSlope() {
        Point comparePoint = new Point(x + 2, y + 4);
        assertEquals(basePoint.slopeTo(comparePoint), 2.0);
        assertEquals(comparePoint.slopeTo(basePoint), 2.0);
    }

    public void testComparatorForPointNoSpecialCases() {
        Point pt0 = new Point(0, 0);
        Point pt1 = new Point(1, 2);
        Point pt2 = new Point(1, 1);
        assertEquals(pt0.SLOPE_ORDER.compare(pt1, pt2), 1);
        assertEquals(pt0.SLOPE_ORDER.compare(pt2, pt1), -1);
    }

    public void testComparatorForPointEqualSlopes() {
        Point pt0 = new Point(0, 0);
        Point pt1 = new Point(1, 2);
        Point pt2 = new Point(2, 4);
        assertEquals(pt0.SLOPE_ORDER.compare(pt1, pt2), 0);
        assertEquals(pt0.SLOPE_ORDER.compare(pt2, pt1), 0);
    }

    public void testComparatorWhereOnPointVertical() {
        Point pt0 = new Point(0, 0);
        Point pt1 = new Point(0, 2);
        Point pt2 = new Point(2, 4);
        assertEquals(pt0.SLOPE_ORDER.compare(pt1, pt2), 1);
        assertEquals(pt0.SLOPE_ORDER.compare(pt2, pt1), -1);
    }

    public void testComparatorWhereBothPointsVertical() {
        Point pt0 = new Point(0, 0);
        Point pt1 = new Point(0, 2);
        Point pt2 = new Point(0, -4);
        assertEquals(pt0.SLOPE_ORDER.compare(pt1, pt2), 0);
        assertEquals(pt0.SLOPE_ORDER.compare(pt2, pt1), 0);
    }

    public void testComparatorWhereOnePointHorizontal() {
        Point pt0 = new Point(0, 0);
        Point pt1 = new Point(2, 0);
        Point pt2 = new Point(2, 4);
        assertEquals(pt0.SLOPE_ORDER.compare(pt1, pt2), -1);
        assertEquals(pt0.SLOPE_ORDER.compare(pt2, pt1), 1);
    }

    public void testComparatorWhereBothPointsHorizontal() {
        Point pt0 = new Point(0, 0);
        Point pt1 = new Point(2, 0);
        Point pt2 = new Point(-9, 0);
        assertEquals(pt0.SLOPE_ORDER.compare(pt1, pt2), 0);
        assertEquals(pt0.SLOPE_ORDER.compare(pt2, pt1), 0);
    }

    public void testComparatorWhereOneHorizontalAndOneVertical() {
        Point pt0 = new Point(0, 0);
        Point pt1 = new Point(0, 2);
        Point pt2 = new Point(-9, 0);
        assertEquals(pt0.SLOPE_ORDER.compare(pt1, pt2), 1);
        assertEquals(pt0.SLOPE_ORDER.compare(pt2, pt1), -1);
    }

    public void testComparatorWherePointsAreIdentical() {
        Point pt0 = new Point(0, 0);
        Point pt1 = new Point(2, 4);
        Point pt2 = new Point(2, 4);
        assertEquals(pt0.SLOPE_ORDER.compare(pt1, pt2), 0);
        assertEquals(pt0.SLOPE_ORDER.compare(pt2, pt1), 0);
    }

    public void testComparatorWhereOnePointIdenticalToSourcePoint() {
        Point pt0 = new Point(0, 0);
        Point pt2 = new Point(2, 4);
        assertEquals(pt0.SLOPE_ORDER.compare(pt0, pt2), -1);
        assertEquals(pt0.SLOPE_ORDER.compare(pt2, pt0), 1);
    }

    public void testComparatorWhereAllPointsIdentical() {
        Point pt = new Point(12, 93);
        assertEquals(pt.SLOPE_ORDER.compare(pt, pt), 0);
        assertEquals(pt.SLOPE_ORDER.compare(pt, pt), 0);
    }
}