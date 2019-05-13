package Helpers;

import java.sql.ResultSet;
import java.sql.SQLException;

import Objects.Viewer;

public class ViewerHelper {

	public static Viewer castRequestToSimpleViewer(ResultSet rs) throws SQLException {
		Viewer ret=new Viewer();

		ret.setBio(rs.getString("bio"));
		ret.setPictureUrl(rs.getString("picture"));
		ret.setName(rs.getString("name"));
		ret.setId(rs.getInt("id"));

		return ret;
	}
	
}
