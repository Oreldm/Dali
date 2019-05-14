package MachineLearning;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
	private List<Point>points;
	private int minX;
	private int maxX; 
	private int maxY;
	private int minY;
	private boolean isFirstSetup=true;
	private double maxRadious;
	private boolean isRadiousFirstSetup=true;
	
	public Cluster(List<Point> points) {
		this.points=points;
		setUpEdgeValues();
		calculate();
		
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
	
	private void calculate() {
		calculate(minX,true);
		calculate(maxX,true);
		calculate(minY,false);
		calculate(maxY,false);
	}
	
	private void calculate(int point, boolean isX) {
		List<Point>points= findAllPointsWithValue(point,isX);
		calculateRadious(points);
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
			if(isRadiousFirstSetup || radious>maxRadious) {
				isRadiousFirstSetup=false;
				maxRadious=radious;
			}
			
		}
		
	}
	
	

}
