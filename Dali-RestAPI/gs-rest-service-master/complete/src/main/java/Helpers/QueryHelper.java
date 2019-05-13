package Helpers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import Objects.Artwork;

public interface QueryHelper  {
	
	public static String selectIdFromTable(String table, int id) {
		String ret="Select * from "+table+" where id="+id;
		return ret;
	}
	
	public static String selectAllByIdFromTable(String table, String valueName, int valueId) {
		String ret="Select * from "+table+" where "+valueName+"="+valueId;
		return ret;
	}
	
	public static Artwork requestToArtworkCasting(ResultSet rs) throws SQLException {
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

}
