import java.util.Arrays;

public class Fast {
    private Point[] points;

    public Fast(Point... points) {
        this.points = points;
        Arrays.sort(this.points);
    }

    public void processPoints(StringBuffer buffer) {
        if (points.length < 4) {
            return;
        }
        Point origin;
        for (int i = 0; i < points.length; i++) {
            origin = points[i];
            Arrays.sort(points, origin.SLOPE_ORDER);
            int startIndex = 1;
            double baseSlope = origin.slopeTo(points[startIndex]);
            for (int j = 1; j < points.length; j++) {
                if (origin.slopeTo(points[j]) != baseSlope) {
                    if (j - startIndex >= 3) {
                        handleOutputForLine(buffer, startIndex, j - 1);
                    }
                    startIndex = j;
                    baseSlope = origin.slopeTo(points[startIndex]);
                }
            }
            if (points.length - startIndex >= 3) {
                handleOutputForLine(buffer, startIndex, points.length - 1);
            }
            Arrays.sort(this.points);
        }
    }

    private void handleOutputForLine(StringBuffer buffer,
                                     int startIndex, int endIndex) {
        if (points[0].compareTo(points[startIndex]) == -1) {
            drawCombination(startIndex, endIndex);
            String outputString = getOutputString(startIndex, endIndex)
                    + "\n";
            System.out.print(outputString);
            if (buffer != null) {
                buffer.append(outputString);
            }
        }
    }

    private void drawCombination(int startIndex, int endIndex) {
        double penRadius = StdDraw.getPenRadius();
        StdDraw.setPenRadius(2 * penRadius);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        points[0].draw();
        for (int i = startIndex; i <= endIndex; i++) {
            points[i].draw();
        }
        StdDraw.setPenRadius(penRadius);
        points[0].drawTo(points[endIndex]);
        StdDraw.show();
    }

    private String getOutputString(int startIndex, int endIndex) {
        StringBuffer buffer = new StringBuffer(points[0].toString());
        for (int i = startIndex; i <= endIndex; i++) {
            buffer.append(" -> " + points[i].toString());
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
        }
        Fast fast = new Fast(points);
        fast.processPoints(null);
    }
}
