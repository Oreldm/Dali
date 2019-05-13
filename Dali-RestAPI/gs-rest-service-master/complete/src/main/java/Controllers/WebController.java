package Controllers;

import java.sql.ResultSet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Helpers.QueryHelper;
import Helpers.TableNames;
import Objects.Artist;
import Objects.Artwork;
import dal_layer.DALService;

@RestController
@RequestMapping("/artist/")
public class WebController implements TableNames, QueryHelper{
	
	@RequestMapping("/login")
	public boolean login() {
			return true;
	}
	
	@RequestMapping("/getProfileById")
	public Artist getProfileById(@RequestParam(value = "id") int id) {
		String command = QueryHelper.selectIdFromTable(ARTIST_TABLE, id);
		ResultSet rs = DALService.sendCommand(command);
		
		try {
			Artist artist=new Artist();
			while (rs.next()) {
//				artist = requestToArtworkCasting(rs);
			}
			DALService.closeConnection();
			return artist;
		} catch (Exception e) {
			e.printStackTrace();
		}
		DALService.closeConnection();
		
		return null;
	}

	
}
