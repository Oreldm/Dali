package Objects.ML;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import MachineLearning.Point;
import Objects.Tag;
import dal_layer.DALService;

public class GraphPair {

	private Tag firstTag; //X value
	private Tag secondTag; //Y value
	private Map<Integer, Point> viewerToPointMap;
	List<Point>points;
	
	public GraphPair(Tag firstTag, Tag secondTag) {
		this.firstTag = firstTag;
		this.secondTag = secondTag;
	}

	private boolean getDataFromDB() throws Exception {
		String command = "SELECT * FROM ML_Viewer_Tag_Score WHERE tagId=1 OR tagId=2";
		ResultSet rs = DALService.sendCommand(command);
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
		
		points=new ArrayList<Point>();
	    Iterator<?> it = viewerToPointMap.entrySet().iterator();
	    while (it.hasNext()) {
	        @SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry)it.next();
	        points.add((Point) pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
		return true;
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
