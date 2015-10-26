package KMeans;

import java.util.*;

import KMeans.Point;

public class Cluster {

	private final List<Point> points;
	private Point centroid;
	
	public Cluster(Point firstPoint) {
		points = new ArrayList<Point>();
		centroid = firstPoint;
	}
	
	public Point getCentroid(){
		return centroid;
	}
	
	public List<Point> getPoints() {
		return points;
	}
	
}
