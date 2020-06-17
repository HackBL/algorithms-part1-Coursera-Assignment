import edu.princeton.cs.algs4.*;
import java.util.*;

public class FastCollinearPoints {
    LineSegment[] lineSegments;

    private class PointsAttr implements Comparable<PointsAttr>
    {
        private final Point origin;
        private final Point newPoint;

        public PointsAttr(Point origin, Point newPoint) {
            this.origin = origin;
            this.newPoint = newPoint;
        }

        public Point getPoint() {
            return this.newPoint;
        }

        public double getSlope() {
            return this.origin.slopeTo(this.newPoint);
        }

        @Override
        public int compareTo(PointsAttr o)
        {
            if (this.getSlope() < o.getSlope()) return -1;
            else if (this.getSlope() > o.getSlope()) return 1;

            return 0;
        }

    }


    public FastCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (isNull(points)) throw new IllegalArgumentException("Null");

        ArrayList<LineSegment> segmentArrayList = new ArrayList<>();

        for (int i = 0; i < points.length; i++) {   // set origin point
            Point curOrigin = points[i];
            Point head = curOrigin;
            Point tail = curOrigin;
            int cnt = 2; // both points count
            int idx = 0;

            PointsAttr[] compare = new PointsAttr[points.length-1];

            for (int j = 0; j < points.length; j++) {
                if (i != j)
                    compare[idx++] = new PointsAttr(curOrigin, points[j]);
            }

            Arrays.sort(compare);   // sort object array by its slope

            for(int j = 0; j < compare.length; j++) {       // find consecutive slopes >= 4
                Point newPnt = compare[j].getPoint();

                if (j > 0) {
                    if (compare[j].getSlope() == compare[j - 1].getSlope()) {
                        cnt++;
                    }
                    else {
                        if (cnt >= 4) { // number of the same slope >= 4
                            LineSegment obj = new LineSegment(head, tail);

                            if (segmentArrayList.contains(obj))
                                StdOut.println("exist");
                            if (!segmentArrayList.contains(obj))
                                segmentArrayList.add(obj);
                        }

                        // reset
                        head = curOrigin;
                        tail = curOrigin;
                        cnt = 2;
                    }
                }

                if (head.compareTo(newPnt) < 0)
                    head = newPnt;

                if (tail.compareTo(newPnt) > 0)
                    tail = newPnt;
            }
        }

        lineSegments = segmentArrayList.toArray(new LineSegment[segmentArrayList.size()]);
    }

    private boolean isNull(Point[] points)
    {
        if (points == null) return true;
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) return true;

            for (int j = i+1; j < points.length; j++) {
                if (points[j] == null) return true;
                if (points[i].compareTo(points[j]) == 0) return true;
            }
        }

        return false;
    }

    public int numberOfSegments()        // the number of line segments
    {
        return lineSegments.length;
    }

    public LineSegment[] segments()                // the line segments
    {
        for (int i = 0; i < lineSegments.length; i++) {
            StdOut.println(lineSegments[i].toString());
        }
        return lineSegments;
    }

    public static void main(String [] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }


        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
