import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

/**
 * Created by biancarhodes on 2/12/17.
 */
public class FastCollinearPoints {
	private LineSegment[] lineSegments;
	// finds all line segments containing 4 or more points
	public FastCollinearPoints(Point[] points){
		ArrayList<LineSegment> segs = new ArrayList<LineSegment>();
		Arrays.sort(points);
		for(Point point: points) {
			Point[] sortedPoints = points.clone();
			Arrays.sort(sortedPoints, point.slopeOrder());
			int p1 = 1;
			int p2 = 2;
			double slope = sortedPoints[0].slopeTo(sortedPoints[p1]);
			while (p1 < sortedPoints.length) {
				while (p2 < sortedPoints.length && sortedPoints[0].slopeTo(sortedPoints[p1]) == sortedPoints[0].slopeTo(sortedPoints[p2])) {
					if(sortedPoints[p2].compareTo(sortedPoints[p1]) == 0) throw new IllegalArgumentException();
					p2++;
				}
				if (p2 - p1 >= 3 && point.compareTo(sortedPoints[p1]) < 0) {
					//System.out.println(Arrays.toString(sortedPoints));
					//System.out.println("p1: " + p1 + " p2: " + p2);
					LineSegment ls = new LineSegment(point, sortedPoints[p2-1]);
					//System.out.println(ls.toString());
					if (!segs.contains(ls)) segs.add(ls);
				}
				p1 = p2;
			}
		}

		lineSegments = new LineSegment[segs.size()];
		segs.toArray(lineSegments);
	}
	// the number of line segments
	public int numberOfSegments(){
		return lineSegments.length;
	}
	// the line segments
	public LineSegment[] segments(){
		return lineSegments;
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

		// draw the points
		/*StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();*/

		// print and draw the line segments
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			//segment.draw();
		}
		//StdDraw.show();
	}
}
