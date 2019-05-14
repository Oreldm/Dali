package MachineLearning;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Helpers.TagHelper;
import Objects.Tag;
import dal_layer.DALService;

public class DaliML {
	
	private List<Tag>generes;
	private List<GraphPair>graphs;

	
	public void start() throws Exception {
		initializeGeneresList();
		initializeGraphs();
		writeGraphsToDB();
	}
	
	private void writeGraphsToDB() throws Exception{
		for(GraphPair g:graphs) {
			double score=g.getScore();
			double radious=g.getRadious();
			int tag1Id=g.getFirstTag().getId();
			int tag2Id=g.getSecondTag().getId();
			String command="Select * from ML_Tags_Connection where tagId1="+tag1Id+" AND tag2Id="+tag2Id;
			
			ResultSet rs=DALService.sendCommand(command);
			double MSE;
			if (rs.next()) {
				MSE=rs.getDouble("Radious")/radious;
				command="UPDATE ML_Tags_Connection SET MSE="+MSE+", Radious="+radious+" ,Score="+score
						+" WHERE tag1Id="+tag1Id+" AND tag2Id="+tag2Id; 
			} else {
				MSE=0;
				command= "INSERT INTO ML_Tags_Connection (tag1Id,tag2Id,MSE,Radious,Score) "
						+ "VALUES ("+tag1Id+","+tag2Id+","+MSE+","+radious+","+score+")";
			}
			
			DALService.sendCommandDataManipulation(command);
		}
	}

	private void initializeGraphs() throws Exception {
		graphs=new ArrayList<GraphPair>();
		for(int i=0;i<generes.size();i++) {
			for(int j=0;j<generes.size();j++) {
				if(i==j) {
					continue;
				}
				Tag firstTag=generes.get(i);
				Tag secondTag=generes.get(j);
				GraphPair graph=new GraphPair(firstTag,secondTag);
				graphs.add(graph);
			}
		}
	}
	
	private void initializeGeneresList() throws SQLException {
		this.generes=TagHelper.getTagsMethod();
	}
	
}
