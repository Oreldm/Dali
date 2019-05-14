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
	
	public Cluster(List<Point> points) {
		this.points=points;
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
	
	

}
