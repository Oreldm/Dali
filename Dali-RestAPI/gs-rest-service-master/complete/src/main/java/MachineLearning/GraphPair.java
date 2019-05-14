package MachineLearning;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Objects.Tag;
import dal_layer.DALService;

public class GraphPair {

	private Tag firstTag; //X value
	private Tag secondTag; //Y value
	private Map<Integer, Point> viewerToPointMap;
	private List<Point>points;
	private List<Cluster>clusters;
	
	public GraphPair(Tag firstTag, Tag secondTag) throws Exception {
		this.firstTag = firstTag;
		this.secondTag = secondTag;
		getDataFromDB();
		initializeClusters();
		findMinCluster();
	}

	private boolean getDataFromDB() throws Exception {
		String command = "SELECT * FROM ML_Viewer_Tag_Score WHERE tagId="+firstTag.getId()+" OR tagId="+secondTag.getId();
		ResultSet rs = DALService.sendCommand(command);

		viewerToPointMapInitialization(rs);
		initializePoints();
		
		return true;
	}

	public void findMinCluster() {
		//Find the cluster with the smallest radious
		//TODO: should add the ability to add Cluster radious score  (Cluster-distance / Cluster radious)
		Cluster c=null;
		for(Cluster tempCluster:clusters) {
			if(c==null) {
				c=tempCluster;
				continue;
			}
			if(tempCluster.getClusterRadious()<c.getClusterRadious()) {
				c=tempCluster;
			}
		}
	}
	
	
    void findAllClustersCombination(List<Point> cluster, int start, 
                                int end, int index, int pointsInCluster) 
    { 
        if (index == pointsInCluster) 
        { 
        	//it found a cluster
        	clusters.add(new Cluster(cluster));
            return; 
        } 
  
        for (int i=start; i<=end && end-i+1 >= pointsInCluster-index; i++) 
        { 
            cluster.add(points.get(i)); 
            findAllClustersCombination(cluster, i+1, end, index+1, pointsInCluster); 
        } 
    } 
  
    void initializeClusters() throws Exception 
    { 
		//create clusters with at list %50 of the points
		int pointsInCluster=points.size()/2;
		if(pointsInCluster<=1) {
			throw new Exception("Not enough points in cluster");
		}
        List<Point>cluster=new ArrayList<Point>();
        findAllClustersCombination(cluster, 0, points.size()-1, 0, pointsInCluster); 
    } 

	public void viewerToPointMapInitialization(ResultSet rs) throws SQLException {
		viewerToPointMap = new HashMap<Integer, Point>();

		while (rs.next()) {
			int viewerId = rs.getInt("viewerId");
			int tagId = rs.getInt("tagId");
			int score = rs.getInt("score");

			Point point = viewerToPointMap.get(viewerId);
			if (point == null) {
				point = new Point();
				point = setValue(tagId,score,point);
				viewerToPointMap.put(viewerId, point);
			} else {
				point = setValue(tagId,score,point);
				viewerToPointMap.put(viewerId, point);
			}
		}
	}

	public void initializePoints() {
		points=new ArrayList<Point>();
	    Iterator<?> it = viewerToPointMap.entrySet().iterator();
	    while (it.hasNext()) {
	        @SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry)it.next();
	        points.add((Point) pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}

	private Point setValue(int tagId, int score, Point point) {
		if (isXValue(tagId)) {
			point.setX(score);
		} else {
			point.setY(score);
		}

		return point;
	}

	private boolean isXValue(int tagId) {
		if (tagId == firstTag.getId()) {
			return true;
		}
		return false;
	}

}
