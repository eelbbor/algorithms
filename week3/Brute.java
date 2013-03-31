import java.util.Arrays;
import java.util.Comparator;

public class Brute {
    private Point[] points;

    public Brute(Point... points) {
        this.points = points;
        Arrays.sort(this.points);
    }

    public void processPoints(StringBuffer buffer) {
        String outputString;
        for (int i = 0; i < points.length - 3; i++) {
            Comparator<Point> slopeOrder = points[i].SLOPE_ORDER;
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    if (slopeOrder.compare(points[j], points[k]) == 0) {
                        for (int l = k + 1; l < points.length; l++) {
                            if (slopeOrder.compare(points[j], points[l]) == 0) {
                                drawCombination(i, j, k, l);
                                outputString = getOutputString(i, j, k, l) + "\n";
                                System.out.print(outputString);
                                if (buffer != null) {
                                    buffer.append(outputString);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void drawCombination(int... ptIndexes) {
        double penRadius = StdDraw.getPenRadius();
        StdDraw.setPenRadius(2 * penRadius);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (int i = 0; i < ptIndexes.length; i++) {
            points[ptIndexes[i]].draw();
        }
        StdDraw.setPenRadius(penRadius);
        points[ptIndexes[0]].drawTo(points[ptIndexes[ptIndexes.length - 1]]);
        StdDraw.show();
    }

    private String getOutputString(int... ptIndexes) {
        StringBuffer buffer = new StringBuffer(points[ptIndexes[0]].toString());
        for (int i = 1; i < ptIndexes.length; i++) {
            buffer.append(" -> " + points[ptIndexes[i]].toString());
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
        Brute brute = new Brute(points);
        brute.processPoints(null);
    }
}