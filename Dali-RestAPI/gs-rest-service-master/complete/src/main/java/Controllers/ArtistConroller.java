package Controllers;

import java.sql.ResultSet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Helpers.QueryHelper;
import Helpers.TableNames;
import Objects.Artwork;
import dal_layer.DALService;

@RestController
@RequestMapping("/artist/")
public class ArtistConroller implements QueryHelper, TableNames {
	
	@RequestMapping("/getArt")
	public Artwork getArtById(@RequestParam(value = "id") int id) {
		String command = QueryHelper.selectIdFromTable(ARTWORK_TABLE, id);
		ResultSet rs = DALService.sendCommand(command);
		
		try {
			Artwork artwork=new Artwork();
			while (rs.next()) {
//				artwork = requestToArtworkCasting(rs);
			}
			DALService.closeConnection();
			return artwork;
		} catch (Exception e) {
			e.printStackTrace();
		}
		DALService.closeConnection();
		
		return null;
	}
	

}