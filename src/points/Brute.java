import java.util.Arrays;
import java.util.Comparator;

public class Brute {
    private static void processPoints(Point[] points) {
        Arrays.sort(points);
        for (int i = 0; i < points.length - 3; i++) {
            Comparator<Point> slopeOrder = points[i].SLOPE_ORDER;
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    if (slopeOrder.compare(points[j], points[k]) == 0) {
                        for (int l = k + 1; l < points.length; l++) {
                            if (slopeOrder.compare(points[j], points[l]) == 0) {
                                //all four on the same level
                                points[i].drawTo(points[l]);
                                outputLineSegment(points[i], points[j], points[k],
                                        points[l]);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void outputLineSegment(Point... points) {
        for (int i = 0; i < points.length; i++) {
            if (i > 0) {
                System.out.print(" -> ");
            }
            System.out.print(points[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
            points[i].draw();
        }

        processPoints(points);
        // display to screen all at once
        StdDraw.show(0);
    }
}
