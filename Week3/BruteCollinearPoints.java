import edu.princeton.cs.algs4.*;
import java.util.*;

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        // Corner Cases
        if (isNull(points)) throw new IllegalArgumentException("Null");
        ArrayList<LineSegment> allSegments = new ArrayList<>();

        for (int i = 0; i < points.length; i++) {
            Point head = points[i];

            for (int j = i+1; j < points.length; j++) {
                Point tail = points[j];

                if (head.compareTo(tail) < 0) {
                    tail = head;
                    head = points[j];
                }

                for (int k = j+1; k < points.length; k++) {
                    if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[k])) {
                        if (head.compareTo(points[k]) < 0)
                            head = points[k];
                        else if (tail.compareTo(points[k]) > 0)
                            tail = points[k];

                        for (int l = k+1; l < points.length; l++) {
                            if (points[j].slopeTo(points[k]) == points[k].slopeTo(points[l])) {    // all four points in a segment
                                if (head.compareTo(points[l]) < 0)
                                    head = points[l];
                                else if (tail.compareTo(points[l]) > 0)
                                    tail = points[l];

                                if (!allSegments.contains(new LineSegment(head, tail)))
                                    allSegments.add(new LineSegment(head, tail));
                            }
                        }
                    }
                }
            }
        }
        lineSegments = allSegments.toArray(new LineSegment[allSegments.size()]);
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
        StdDraw.setXscale(0, 32767);
        StdDraw.setYscale(0, 32767);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
