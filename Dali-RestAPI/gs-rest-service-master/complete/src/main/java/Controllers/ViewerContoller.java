package Controllers;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Helpers.ArtworkHelper;
import Helpers.QueryHelper;
import Helpers.TableNames;
import Objects.Artwork;
import dal_layer.DALService;

@RestController
@RequestMapping("/viewer/")
public class ViewerContoller implements QueryHelper,TableNames  {
	// get all settings
		// DownloadArt
		// Get list of arts by cordinates
		// upload score for an art

		
		@RequestMapping("/getArt")
		public Artwork getArtById(@RequestParam(value = "id") int id) {
			String command = QueryHelper.selectIdFromTable(ARTWORK_TABLE, id);
			ResultSet rs = DALService.sendCommand(command);
			
			try {
				Artwork artwork=new Artwork();
				while (rs.next()) {
					artwork = requestToArtworkCasting(rs);
				}
				DALService.closeConnection();
				return artwork;
			} catch (Exception e) {
				e.printStackTrace();
			}
			DALService.closeConnection();
			
			return null;
		}

		@RequestMapping("/getArtsByLocation")
		public List<Artwork> getArtByLocation(@RequestParam String xPosition, @RequestParam String yPosition) {
			Double xMinValue = Double.parseDouble(xPosition) - 0.006;
			Double xMaxValue = Double.parseDouble(xPosition) + 0.006;
			Double yMinValue = Double.parseDouble(yPosition) - 0.006;
			Double yMaxValue = Double.parseDouble(yPosition) + 0.006;
			String command = "SELECT * from Artwork WHERE Artwork.id IN "
					+ "(SELECT artworkId FROM Geolocation WHERE "+xMinValue+"<=xPosition AND xPosition<="+
					xMaxValue+" AND yPosition<="+yMaxValue+" AND "+yMinValue+"<=yPosition) ";
			
			ResultSet rs = DALService.sendCommand(command);
			try {
				List<Artwork> artworkArrList= new ArrayList<Artwork>();
				while (rs.next()) {
						Artwork artwork = requestToArtworkCasting(rs);
						
						artworkArrList.add(artwork);
				}
				DALService.closeConnection();
				return artworkArrList;
			} catch (Exception e) {
				e.printStackTrace();
			}
			DALService.closeConnection();
			return null;
		}

		private Artwork requestToArtworkCasting(ResultSet rs) throws SQLException {
			Artwork artwork= new Artwork();
			int artId=rs.getInt("id");
			String path= rs.getString("path");
			String artName=rs.getString("name");
			int artistId=rs.getInt("artistId");
			artwork.setId(artId);
			artwork.setPath(path);
			artwork.setName(artName);
			artwork.setArtistId(artistId);
			JsonObject location= ArtworkHelper.getLocationForArtwork(artwork);
			artwork.setPositionX(location.get("x").getAsFloat());
			artwork.setPositionY(location.get("y").getAsFloat());
			return artwork;
		}

		public boolean updateTags() {
			return false;
		}

		public boolean getSuggestedArts() {
			return false;
		}


	

}
