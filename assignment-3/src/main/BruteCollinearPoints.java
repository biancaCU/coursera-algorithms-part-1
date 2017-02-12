import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by biancarhodes on 2/11/17.
 */
public class BruteCollinearPoints {
	private int numSegments;
	private LineSegment[] lineSegments;

	// finds all line segments containing 4 points
	public BruteCollinearPoints(Point[] points) {
		if (points == null) throw new NullPointerException();
		ArrayList<LineSegment> segs = new ArrayList<LineSegment>();
		Arrays.sort(points);
		//System.out.println(Arrays.toString(points));
		for(int i=0; i < points.length-3; i++){
			for(int j=i+1; j < points.length-2; j++){
				if(points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
				for(int k=j+1; k < points.length-1; k++){
					for(int l=k+1; l < points.length; l++){
						double slope = points[i].slopeTo(points[j]);
						if(points[i].slopeTo(points[k]) == slope && points[i].slopeTo(points[l]) == slope){
							segs.add(new LineSegment(points[i], points[l]));
						}
					}
				}
			}
		}
		lineSegments = new LineSegment[segs.size()];
		segs.toArray(lineSegments);
	}

	private boolean checkDuplicate(Point a, Point b){
		if(a.compareTo(b) == 0) throw new IllegalArgumentException();
		return false;
	}

	// the number of line segments
	public int numberOfSegments() {
		return lineSegments.length;
	}

	// the line segments
	public LineSegment[] segments() {
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
		BruteCollinearPoints collinear = new BruteCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			//segment.draw();
		}
		//StdDraw.show();
	}
}
