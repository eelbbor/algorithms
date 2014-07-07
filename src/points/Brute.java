public class Brute {
    private static void processPoints(Point[] points) {
        for(int i = 0 ; i < )
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
