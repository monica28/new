package KMeans;

import KMeans.Point;

public class Point {
	
	private int index = -1;	
	public double x, y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
		
}
