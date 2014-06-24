import java.util.Comparator;

public class Point implements Comparable<Point> {
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(x, y, that.x, that.y);
    }

    public String toString() {
        return String.format("(%s, %s)", x, y);
    }

    @Override
    public int compareTo(Point point) {
        if (y < point.y || (y == point.y && x < point.x)) {
            return -1;
        } else if (y == point.y && x == point.x) {
            return 0;
        } else {
            return 1;
        }
    }

    public double slopeTo(Point that) {
        if (this.compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (that.x == x) {
            return Double.POSITIVE_INFINITY;
        } else if (that.y == y) {
            return 0.0;
        } else {
            return (double) (that.y - y) / (that.x - x);
        }
    }

    private class SlopeOrder implements Comparator<Point> {
        @Override
        public int compare(Point point1, Point point2) {
            double slope1 = Point.this.slopeTo(point1);
            double slope2 = Point.this.slopeTo(point2);
            return ((Double) (slope1)).compareTo(slope2);
        }
    }
}
