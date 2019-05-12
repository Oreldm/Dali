package Helpers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import Objects.Artwork;
import dal_layer.DALService;

public class ArtworkHelper implements QueryHelper,TableNames {
	public static JsonObject getLocationForArtwork(Artwork artwork) {
		String command=QueryHelper.selectIdFromTable(GEOLOCATION,"artworkId", artwork.getId());
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
	
}
