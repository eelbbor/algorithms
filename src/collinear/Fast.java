import java.util.Arrays;

public class Fast {

    private static void processPoints(Point[] points) {
        if (points.length < 2) {
            return;
        }
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            Point origin = points[i];
            Arrays.sort(points, origin.SLOPE_ORDER);
            double slope = origin.slopeTo(points[1]);
            int collinearCount = 2;
            for (int j = 2; j < points.length; j++) {
                if (origin.slopeTo(points[j]) != slope) {
                    if (collinearCount >= 4) {
                        outputLineSegment(collinearCount, j - 1, points);
                    }
                    collinearCount = 2;
                    slope = origin.slopeTo(points[j]);
                } else {
                    collinearCount++;
                }
            }
            if (collinearCount >= 4) {
                outputLineSegment(collinearCount, points.length - 1, points);
            }
            Arrays.sort(points);
        }
    }

    private static void outputLineSegment(int count, int endIndex, Point... points) {
        int startIndex = endIndex - count + 2;
        if (points[0].compareTo(points[startIndex]) < 0) {
            points[0].drawTo(points[endIndex]);
            System.out.print(points[0]);
            for (int i = 0; i < count - 1; i++) {
                System.out.print(" -> " + points[(startIndex + i)]);
            }
            System.out.println();
        }
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
