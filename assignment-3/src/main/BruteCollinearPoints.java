import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by biancarhodes on 2/11/17.
 */
public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        ArrayList<LineSegment> segs = new ArrayList<LineSegment>();
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        for (int i = 0; i < sortedPoints.length - 3; i++) {
            for (int j = i + 1; j < sortedPoints.length - 2; j++) {
                if (sortedPoints[i].compareTo(sortedPoints[j]) == 0) throw new IllegalArgumentException();
                for (int k = j + 1; k < sortedPoints.length - 1; k++) {
                    for (int l = k + 1; l < sortedPoints.length; l++) {
                        double slope = sortedPoints[i].slopeTo(sortedPoints[j]);
                        if (sortedPoints[i].slopeTo(sortedPoints[k]) == slope && sortedPoints[i].slopeTo(sortedPoints[l]) == slope) {
                            segs.add(new LineSegment(sortedPoints[i], sortedPoints[l]));
                        }
                    }
                }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }
}
