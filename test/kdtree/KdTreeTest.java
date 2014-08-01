import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.TreeSet;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
public class KdTreeTest {
    private KdTree tree;

    @BeforeMethod
    protected void setUp() {
        tree = new KdTree();
    }

    public void shouldCreateEmptyTree() {
        validateTree(null);
    }

    public void shouldNotInsertDuplicatePoints() {
        Point2D p1 = new Point2D(0.5, 0.5);
        tree.insert(p1);
        validateTree(p1);

        Point2D p2 = new Point2D(0.5, 0.5);
        tree.insert(p2);
        validateTree(p1);
    }

    public void shouldInsertPoints() {
        Point2D p1 = new Point2D(0.7, 0.2);
        Point2D p2 = new Point2D(0.5, 0.4);
        Point2D p3 = new Point2D(0.2, 0.3);
        Point2D p4 = new Point2D(0.4, 0.7);
        Point2D p5 = new Point2D(0.9, 0.6);

        tree.insert(p1);
        validateTree(p1);

        tree.insert(p2);
        validateTree(p1, p2);

        tree.insert(p3);
        validateTree(p1, p2, p3);

        tree.insert(p4);
        validateTree(p1, p2, p3, p4);

        tree.insert(p5);
        validateTree(p1, p2, p3, p4, p5);
    }

    public void shouldReturnNullForNearestPoint() {
        assertEquals(tree.nearest(new Point2D(1.5, 1.5)), null);
    }

    public void shouldReturnNearestPoint() {
        Point2D p1 = new Point2D(0.1, 0.1);
        Point2D p2 = new Point2D(0.1, 0.2);
        Point2D p3 = new Point2D(0.1, 0.17);
        Point2D p4 = new Point2D(0.17, 0.1);
        tree.insert(p1);
        tree.insert(p2);
        tree.insert(p3);
        tree.insert(p4);
        validateTree(p1, p2, p3, p4);
        assertEquals(tree.nearest(new Point2D(0.15, 0.15)), p4);
    }

    public void shouldReturnEmptyIterableForRange() {
        Set<Point2D> range = (TreeSet<Point2D>) tree.range(new RectHV(0.0, 0.0, 1.0, 1.0));
        assertTrue(range.isEmpty());
    }

    public void shouldReturnIterableForRange() {
        Point2D p1 = new Point2D(0.1, 0.1);
        Point2D p2 = new Point2D(0.1, 0.2);
        Point2D p3 = new Point2D(0.1, 0.17);
        Point2D p4 = new Point2D(0.17, 0.1);
        Point2D p5 = new Point2D(0.17, 0.17);
        tree.insert(p1);
        tree.insert(p2);
        tree.insert(p3);
        tree.insert(p4);
        tree.insert(p5);

        TreeSet<Point2D> range = (TreeSet<Point2D>) tree.range(new RectHV(0.1, 0.1, 0.17, 0.17));
        validateTree(p1, p2, p3, p4, p5);
        assertEquals(range.size(), 4);
        assertTrue(range.contains(p1));
        assertTrue(range.contains(p3));
        assertTrue(range.contains(p4));
        assertTrue(range.contains(p5));
    }

    public void shouldReturnIterableWithRootAndLeftRight() {
        Point2D p1 = new Point2D(0.7, 0.2);
        Point2D p2 = new Point2D(0.5, 0.4);
        Point2D p3 = new Point2D(0.2, 0.3);
        Point2D p4 = new Point2D(0.4, 0.7);
        Point2D p5 = new Point2D(0.9, 0.6);

        tree.insert(p1);
        tree.insert(p2);
        tree.insert(p3);
        tree.insert(p4);
        tree.insert(p5);

        TreeSet<Point2D> range = (TreeSet<Point2D>) tree.range(new RectHV(0.45, 0.1, 0.95, 0.7));
        validateTree(p1, p2, p3, p4, p5);
        assertEquals(range.size(), 3);
        assertTrue(range.contains(p1));
        assertTrue(range.contains(p2));
        assertTrue(range.contains(p5));
    }

    public void shouldDrawTheTree() {
        for(int i = 0 ; i < 20 ; i++) {
            tree.insert(new Point2D(Math.random(), Math.random()));
        }
        tree.draw();
    }

    private void validateTree(Point2D... points) {
        if (points == null) {
            Assert.assertTrue(tree.isEmpty());
            assertEquals(tree.size(), 0);
        } else {
            Assert.assertFalse(tree.isEmpty());
            assertEquals(tree.size(), points.length);
            for (Point2D point : points) {
                Assert.assertTrue(tree.contains(point));
            }
        }
    }
}