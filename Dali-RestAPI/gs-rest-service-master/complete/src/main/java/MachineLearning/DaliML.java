package MachineLearning;

import java.sql.SQLException;
import java.util.List;

import Helpers.TagHelper;
import Objects.Tag;

public class DaliML {
	
	private List<Tag>generes;

	public void start() throws Exception {
		initializeGeneresList();
		
		for(int i=0;i<generes.size();i++) {
			for(int j=0;j<generes.size();j++) {
				if(i==j) {
					continue;
				}
				Tag firstTag=generes.get(i);
				Tag secondTag=generes.get(j);
				GraphPair graph=new GraphPair(firstTag,secondTag);
			}
		}
	
	}
	
	private void initializeGeneresList() throws SQLException {
		this.generes=TagHelper.getTagsMethod();
	}
	
}
