import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.TreeSet;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
public class PointSETTest {
    private PointSET set;

    @BeforeMethod
    protected void setUp() {
        set = new PointSET();
    }

    public void shouldCreateEmptySet() {
        validateSet(null);
    }

    public void shouldInsertPoints() {
        Point2D p1 = new Point2D(0.1, 0.1);
        set.insert(p1);
        validateSet(p1);

        Point2D p2 = new Point2D(0.1, 0.2);
        set.insert(p2);
        validateSet(p1, p2);
    }

    public void shouldReturnNullForNearestPoint() {
        assertEquals(set.nearest(new Point2D(1.5, 1.5)), null);
    }

    public void shouldReturnNearestPoint() {
        Point2D p1 = new Point2D(0.1, 0.1);
        Point2D p2 = new Point2D(0.1, 0.2);
        Point2D p3 = new Point2D(0.1, 0.17);
        Point2D p4 = new Point2D(0.17, 0.1);
        set.insert(p1);
        set.insert(p2);
        set.insert(p3);
        set.insert(p4);
        validateSet(p1, p2, p3, p4);
        assertEquals(set.nearest(new Point2D(0.15, 0.15)), p4);
    }

    public void shouldReturnEmptyIterableForRange() {
        Set<Point2D> range = (TreeSet<Point2D>) set.range(new RectHV(0.0, 0.0, 1.0, 1.0));
        assertTrue(range.isEmpty());
    }

    public void shouldReturnIterableForRange() {
        Point2D p1 = new Point2D(0.1, 0.1);
        Point2D p2 = new Point2D(0.1, 0.2);
        Point2D p3 = new Point2D(0.1, 0.17);
        Point2D p4 = new Point2D(0.17, 0.1);
        Point2D p5 = new Point2D(0.17, 0.17);
        set.insert(p1);
        set.insert(p2);
        set.insert(p3);
        set.insert(p4);
        set.insert(p5);

        TreeSet<Point2D> range = (TreeSet<Point2D>) set.range(new RectHV(0.1, 0.1, 0.17, 0.17));
        validateSet(p1, p2, p3, p4, p5);
        assertEquals(range.size(), 4);
        assertTrue(range.contains(p1));
        assertTrue(range.contains(p3));
        assertTrue(range.contains(p4));
        assertTrue(range.contains(p5));
    }

    public void shouldDrawTheSet() {
        for(int i = 0 ; i < 10000 ; i++) {
            set.insert(new Point2D(Math.random(), Math.random()));
        }
        set.draw();
    }

    private void validateSet(Point2D... points) {
        if (points == null) {
            Assert.assertTrue(set.isEmpty());
            assertEquals(set.size(), 0);
        } else {
            Assert.assertFalse(set.isEmpty());
            assertEquals(set.size(), points.length);
            for (Point2D point : points) {
                Assert.assertTrue(set.contains(point));
            }
        }
    }
}