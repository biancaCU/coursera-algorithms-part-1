import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by biancarhodes on 2/12/17.
 */
public class FastCollinearPoints {
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        ArrayList<LineSegment> segs = new ArrayList<LineSegment>();
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        for (Point point : sortedPoints) {
            Point[] slopeSortedPoints = sortedPoints.clone();
            Arrays.sort(slopeSortedPoints, point.slopeOrder());
            int p1 = 1;
            int p2 = 2;
            while (p1 < slopeSortedPoints.length) {
                while (p2 < slopeSortedPoints.length &&
                        slopeSortedPoints[0].slopeTo(slopeSortedPoints[p1]) == slopeSortedPoints[0].slopeTo(slopeSortedPoints[p2])) {
                    if (p1 != p2 && slopeSortedPoints[p2].compareTo(slopeSortedPoints[p1]) == 0)
                        throw new IllegalArgumentException();
                    p2++;
                }
                if (p2 - p1 >= 3 && point.compareTo(slopeSortedPoints[p1]) < 0) {
                    LineSegment ls = new LineSegment(point, slopeSortedPoints[p2 - 1]);
                    segs.add(ls);
                }
                p1 = p2;
            }
        }

        lineSegments = new LineSegment[segs.size()];
        segs.toArray(lineSegments);
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }
}
