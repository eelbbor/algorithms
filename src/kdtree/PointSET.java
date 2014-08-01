import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    private Set<Point2D> pointSet;

    public PointSET() {
        pointSet = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    public int size() {
        return pointSet.size();
    }

    public void insert(Point2D p) {
        pointSet.add(p);
    }

    public boolean contains(Point2D p) {
        return pointSet.contains(p);
    }

    public void draw() {
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        for (Point2D point : pointSet) {
            StdDraw.point(point.x(), point.y());
        }
        StdDraw.show(0);
    }

    public Iterable<Point2D> range(RectHV rect) {
        Set<Point2D> range = new TreeSet<Point2D>();
        for (Point2D point : pointSet) {
            if (point.x() >= rect.xmin() && point.y() >= rect.ymin()
                    && point.x() <= rect.xmax() && point.y() <= rect.ymax()) {
                range.add(point);

            }
        }
        return range;
    }

    public Point2D nearest(Point2D p) {
        Point2D nearestPoint = null;
        double distance = Double.POSITIVE_INFINITY;
        for (Point2D point : pointSet) {
            double distanceSquaredTo = p.distanceSquaredTo(point);
            if (distanceSquaredTo < distance) {
                distance = distanceSquaredTo;
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }
}
