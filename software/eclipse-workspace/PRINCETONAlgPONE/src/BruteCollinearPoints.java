import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	private LineSegment[] lineSegs;
	private int segNum;
	private Point[] startPoints;
	private Point[] endPoints;
	
   public BruteCollinearPoints(Point[] points) {
       if (points == null) {
           throw new IllegalArgumentException();
       }
       
       for(int i = 0;i < points.length;i++) {
		   if (points[i] == null) {
			   throw new IllegalArgumentException();
		   }
       }
       
       Point[] sortedPts = new Point[points.length];
       System.arraycopy(points, 0, sortedPts, 0, points.length);
	   
	   Arrays.sort(sortedPts);
	   Point currPoint = sortedPts[0];
	   for (int i = 1; i < sortedPts.length; i++) {
		   if(currPoint.compareTo(sortedPts[i]) == 0) {
			   throw new IllegalArgumentException();
		   }
		   else {
			   currPoint = sortedPts[i];
		   }
	   }
	   
	   segNum = 0;
	   startPoints = new Point[sortedPts.length];
	   endPoints = new Point[sortedPts.length];
	   for (Point p1 : sortedPts) {
		   for (Point p2 : sortedPts) {
			   if(p2.compareTo(p1) <= 0) continue;
			   for (Point p3 : sortedPts) {
				   if(p3.compareTo(p2) <= 0) continue;
				   for (Point p4 : sortedPts) {
					   if(p4.compareTo(p3) <= 0) continue;
					   double s1 = p1.slopeTo(p2);
					   double s2 = p1.slopeTo(p3);
					   double s3 = p1.slopeTo(p4);
					   if (s1 == s2 && s2 == s3 ) {
						   startPoints[segNum] = p1;
						   endPoints[segNum] = p4;
						   segNum += 1;
					   }
				   }   
			   }   
		   }
	   }
	   
	   lineSegs = new LineSegment[segNum];
	   for(int k = 0; k < segNum; k++) {
		   lineSegs[k] = new LineSegment(startPoints[k],endPoints[k]);
	   }
   }
   
   public int numberOfSegments() {
	   return segNum;
   }
   
   public LineSegment[] segments() {
	   return lineSegs.clone();
   }
   
   public static void main(String[] args) {
       /* YOUR CODE HERE */
       In in = new In("C:\\Users\\Arthur Lance\\Downloads\\collinear-testing\\collinear\\input8.txt");
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
       BruteCollinearPoints collinear = new BruteCollinearPoints(points);
       for (LineSegment segment : collinear.segments()) {
           StdOut.println(segment);
           segment.draw();
       }
       StdDraw.show();
   }
}
