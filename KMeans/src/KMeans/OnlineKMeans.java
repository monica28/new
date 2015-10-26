package KMeans;

import java.io.*;
import java.util.*;

import KMeans.Cluster;

public class OnlineKMeans {

	public final List<Point> allPoints;
	public final int k;
	private Clusters pointClusters;	//the k Clusters

	
	public OnlineKMeans(String pointsFile, int k) {
		this.k = k;
		List<Point> points = new ArrayList<Point>();
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(pointsFile));
			String line;
			while ((line = reader.readLine()) != null) 
			points.add(getPointByLine(line));
			reader.close();
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		this.allPoints = Collections.unmodifiableList(points);
	}

	private Point getPointByLine(String line) {
		String[] xy = line.split(",");
		return new Point(Double.parseDouble(xy[0]),
				Double.parseDouble(xy[1]));
	}

	//set initial centroids of the k clusters
	
	private void getInitialClusters(){
		
		pointClusters = new Clusters(allPoints);
		List<Point> kInitialPoints = new ArrayList<Point>();
		for (int i = 0; i < k; i++){
			kInitialPoints.add(i, allPoints.get(i));
			pointClusters.add(new Cluster(kInitialPoints.get(i)));
		}	
	}
	

	// update the k Clusters
	 
	private void updateInitialClusters(){
		
		double a = 0.05; //learning rate
		double minDistance = Double.MAX_VALUE;
		int itsIndex = -1;
		
		for(int i=0; i<allPoints.size(); i++){
			for(int j=0; j<pointClusters.size(); j++){
				
				double	dist = (Math.pow((allPoints.get(i).x) - (pointClusters.get(j).getCentroid().x), 2))
						+ (Math.pow((allPoints.get(i).y) - (pointClusters.get(j).getCentroid().y), 2));
						
					if (dist < minDistance){
					minDistance = dist;
					itsIndex = j;
					}
					
		//update centroid with learning rate (a)
		// C = C + a(x-C)
		pointClusters.get(itsIndex).getCentroid().x += ((pointClusters.get(itsIndex).getCentroid().x) + (a* minDistance));
		pointClusters.get(itsIndex).getCentroid().y += ((pointClusters.get(itsIndex).getCentroid().y) + (a* minDistance));
		
		
		}
		}
		
	}
	
	//KMeans clustering
	
	public List<Cluster> getPointsClusters() {
		if (pointClusters == null) {
			getInitialClusters();
			updateInitialClusters();
			
		}
		return pointClusters;
	}
	
	public static void main(String[] args) {
		
		OnlineKMeans kMeans = new OnlineKMeans(args[0], Integer.parseInt(args[1]));
			
		List<Cluster> FinalClusters = kMeans.getPointsClusters();
		
			for (int i = 0 ; i < kMeans.k; i++)
			System.out.println("Cluster " + i + ": " + FinalClusters.get(i));
	
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("centroids.csv")));
			for (int i = 0; i < FinalClusters.size(); i++) 
			{
				out.write(FinalClusters.get(i) + "\n");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
