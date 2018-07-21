import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
	private LineSegment[] lineSegs;
	private int segNum;
//	private Point[] startPoints;
//	private Point[] endPoints;
	
   public FastCollinearPoints(Point[] points) {
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
       
	   Point[] pointsCopy = new Point[points.length];
	   int k = 0;
	   for(Point p : points) {
		   pointsCopy[k] = p;
		   k += 1;
	   }
       
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
	   
//	   startPoints = new Point[sortedPts.length];
//	   endPoints = new Point[sortedPts.length];
	   ArrayList<Point> startPoints = new ArrayList<Point>();
	   ArrayList<Point> endPoints = new ArrayList<Point>();
	   segNum = 0;
	   for(Point p : sortedPts) {
		   Arrays.sort(pointsCopy);
		   Arrays.sort(pointsCopy,p.slopeOrder());
		   k = 0;
		   double lastSlope = 0;
		   double currSlope = 0;
		   int collinearNum = 0;
		   for(Point q : pointsCopy) {
			   if(k == 0) {
				   lastSlope = p.slopeTo(q);
			   }
			   else {
				   currSlope = p.slopeTo(q);
				   if(currSlope == lastSlope) {
					   collinearNum += 1;
				   }
				   else {
					   if(collinearNum >= 2){
						   int flag = 0;
						   if(p.compareTo(pointsCopy[k - collinearNum - 1]) > 0) {
							   flag = 1;
						   }
						   if(flag == 0) {
//							   startPoints[segNum] = p;
//							   endPoints[segNum] = pointsCopy[k - 1];
							   startPoints.add(p);
							   endPoints.add(pointsCopy[k - 1]);
							   segNum += 1;
						   }
					   }
					   collinearNum = 0;
				   }
				   lastSlope = currSlope;
			   }
			   k += 1;
		   }
		   if(collinearNum >= 2){
			   int flag = 0;
			   if(p.compareTo(pointsCopy[k - collinearNum - 1]) > 0) {
				   flag = 1;
			   }
			   if(flag == 0) {
				   startPoints.add(p);
				   endPoints.add(pointsCopy[k - 1]);
				   segNum += 1;
			   }
		   }
	   }
	   
	   lineSegs = new LineSegment[segNum];
	   for(k = 0; k < segNum; k++) {
		   lineSegs[k] = new LineSegment(startPoints.get(k),endPoints.get(k));
	   }
   }
   
   public int numberOfSegments() {
	   return segNum;
   }
   
   public LineSegment[] segments() {
//	   LineSegment[] res = new LineSegment[segNum];
//	   for(int k = 0; k < segNum; k++) {
//		   res[k] = new LineSegment(startPoints[k],endPoints[k]);
//	   }
//	   return res;
	   return lineSegs.clone();
   }
   
   public static void main(String[] args) {
       /* YOUR CODE HERE */
       In in = new In("C:\\Users\\Arthur Lance\\Downloads\\collinear-testing\\collinear\\input40.txt");
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