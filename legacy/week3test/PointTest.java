import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test
public class PointTest {
    public void testPointWithGreaterYIsGreater() {
        Point pt1 = new Point(1, 10);
        Point pt2 = new Point(1, 5);
        assertEquals(pt1.compareTo(pt2), 1);
        assertEquals(pt2.compareTo(pt1), -1);
    }

    public void testPointWithEqualYAndGreaterXIsGreater() {
        Point pt1 = new Point(5, 5);
        Point pt2 = new Point(1, 5);
        assertEquals(pt1.compareTo(pt2), 1);
        assertEquals(pt2.compareTo(pt1), -1);
    }

    public void testEqualPoints() {
        Point pt1 = new Point(5, 5);
        Point pt2 = new Point(5, 5);
        assertEquals(pt1.compareTo(pt2), 0);
        assertEquals(pt2.compareTo(pt1), 0);
    }

    public void testDraw() {
        new Point(1,1).draw();
    }

    public void testDrawTo() {
        new Point(0,0).drawTo(new Point(2, 5));
    }

    public void testPointSlopeRelativeToItselfIsNegativeInfinity() {
        Point pt = new Point(1, 3);
        assertEquals(pt.slopeTo(pt), Double.NEGATIVE_INFINITY);
    }

    public void testPointSlopeRelativeToPointVerticalIsPositiveInfinity() {
        Point pt1 = new Point(1, 3);
        Point pt2 = new Point(1, 9);
        assertEquals(pt1.slopeTo(pt2), Double.POSITIVE_INFINITY);
        assertEquals(pt2.slopeTo(pt1), Double.POSITIVE_INFINITY);
    }

    public void testPointSlopeRelativeToPointHorizontalIsZero() {
        Point pt1 = new Point(1, 3);
        Point pt2 = new Point(8, 3);
        assertEquals(pt1.slopeTo(pt2), 0.0);
        assertEquals(pt2.slopeTo(pt1), 0.0);
    }

    public void testPointSlopeRelativeToPointIsPositive() {
        Point pt1 = new Point(1, 1);
        Point pt2 = new Point(5, 3);
        assertEquals(pt1.slopeTo(pt2), 0.5);
        assertEquals(pt2.slopeTo(pt1), 0.5);
    }

    public void testPointSlopeRelativeToPointIsNegative() {
        Point pt1 = new Point(4, 10);
        Point pt2 = new Point(8, 3);
        assertEquals(pt1.slopeTo(pt2), -1.75);
        assertEquals(pt2.slopeTo(pt1), -1.75);
    }

    public void testPointToStringIsFormattedCorrectly() {
        assertEquals(new Point(1, 200).toString(), "(1, 200)");
        assertEquals(new Point(-1129, 200).toString(), "(-1129, 200)");
        assertEquals(new Point(33, -214).toString(), "(33, -214)");
        assertEquals(new Point(-9873, -6532).toString(), "(-9873, -6532)");
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
