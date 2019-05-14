package Helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Objects.Tag;
import dal_layer.DALService;

public class TagHelper {

	public static List<Tag> getTagsMethod() throws SQLException {
		List<Tag>generes=new ArrayList<Tag>();
		String command = "select * from Tags";
		ResultSet rs = DALService.sendCommand(command);
		while(rs.next()) {
			generes.add(new Tag(rs.getString("name"),rs.getInt("id")));
		}
		return generes;
	}
}
