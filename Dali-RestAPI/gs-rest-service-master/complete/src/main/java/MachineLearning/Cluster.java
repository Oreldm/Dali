package MachineLearning;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
	private double clusterRadious;
	private double distanceFromOrigin;
	
	private List<Point>points;
	private int minX;
	private int maxX; 
	private int maxY;
	private int minY;
	private boolean isFirstSetup=true;
	private boolean isRadiousFirstSetup=true;
	private Point pointOne;
	private Point pointTwo;

	
	public Cluster(List<Point> points) {
		this.points=points;
		distanceFromOrigin=-1;
		setUpEdgeValues();
		calculate();
	}
	
	public double getClusterRadious() {
		return this.clusterRadious;
	}
	
	public double getDistanceFromOrigin() {
		return distanceFromOrigin;
	}
	
	private void setUpEdgeValues() {
		for(Point point:points) {
			int x=point.getX();
			int y=point.getY();
			
			updateValue(x,y);
			
		}
		
	}
	
	private void updateValue(int x, int y) {
		if(isFirstSetup) {
			minX=x;
			maxX=x;
			minY=y;
			maxY=y;
			isFirstSetup=false;
			return;
		}
		minX = (x<minX) ? x : minX;
		if(minX!=x)
			maxX = (maxX<x) ? x : maxX;
		minY = (y<minY) ? y : minY;
		if(minY!=y)
			maxY = (maxY<y) ? y : minY;
		
	}
	
	private void calculate() {
		calculate(minX,true);
		calculate(maxX,true);
		calculate(minY,false);
		calculate(maxY,false);
		distanceFromOrigin(); //should always come after all calculations
	}
	
	public double getClusterFinalScore() {
		double score=distanceFromOrigin/clusterRadious;
		return score;
	}

	public void distanceFromOrigin() {
		double xValue=(pointOne.getX()+pointTwo.getX())/2;
		double yValue=(pointOne.getY()+pointTwo.getY())/2;
		distanceFromOrigin= Math.sqrt((yValue) * (yValue) + (xValue) * (xValue));
	}
	
	private void calculate(int point, boolean isX) {
		List<Point>points= findAllPointsWithValue(point,isX);
		calculateRadious(points);
	}
	
	List<Point>findAllPointsWithValue(int value, boolean isX){
		List<Point>ret=new ArrayList<Point>();
		
		for(Point point: points) {
			if(isX && point.getX()==value) {
				ret.add(point);
			}else if(!isX && point.getY()==value) {
				ret.add(point);
			}
		}
		return ret;
	}
	
	private void calculateRadious(List<Point>points) {
		for(Point point:points) {
			calculateRadious(point);
		}
	}
	
	private void calculateRadious(Point point) {
		for(Point secondPoint:points) {
			double x1= (double)point.getX();
			double x2= (double)secondPoint.getX();
			double y1= (double)point.getY();
			double y2= (double)secondPoint.getY();
			
			if(x1==x2 && y1==y2) {
				continue;
			}
			double radious= Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)); //distance calculation
			radious=radious/2; //make it a radious
			if(isRadiousFirstSetup || radious>clusterRadious) {
				isRadiousFirstSetup=false;
				pointOne=point;
				pointTwo=secondPoint;
				clusterRadious=radious;
			}
			
		}
		
	}
	
	

}
