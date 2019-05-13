package Helpers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import Objects.Artwork;
import dal_layer.DALService;

public class ArtworkHelper implements QueryHelper,TableNames {

	public static JsonObject getLocationForArtwork(Artwork artwork) {
		String command=QueryHelper.selectAllByIdFromTable(GEOLOCATION_TABLE,"artworkId", artwork.getId());
		ResultSet artGeolocation = DALService.sendCommand(command);
		
		float positionX=(float) 0.0;
		float positionY=(float) 0.0;
		try {
			while(artGeolocation.next()) {
				positionX=artGeolocation.getFloat("xPosition");
				positionY=artGeolocation.getFloat("yPosition");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JsonObject ret=new JsonObject();
		ret.addProperty("x", positionX+"");
		ret.addProperty("y", positionY+"");
		
		return ret;
	}
	
	public static Artwork requestToArtworkCasting(ResultSet rs) throws SQLException {
		Artwork artwork = new Artwork();
		int artId = rs.getInt("id");
		String path = rs.getString("path");
		String artName = rs.getString("name");
		int artistId = rs.getInt("artistId");
		artwork.setId(artId);
		artwork.setPath(path);
		artwork.setName(artName);
		artwork.setArtistId(artistId);
		artwork.setDt_created(rs.getString("dt_created"));
		JsonObject location = ArtworkHelper.getLocationForArtwork(artwork);
		artwork.setPositionX(location.get("x").getAsFloat());
		artwork.setPositionY(location.get("y").getAsFloat());
		return artwork;
	}
	
}
