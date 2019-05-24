package Helpers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import Objects.Artwork;
import dal_layer.DALService;

public interface QueryHelper  {
	
	public static String selectIdFromTable(String table, int id) {
		String ret="Select * from "+table+" where id="+id;
		return ret;
	}
	
	public static String selectIdFromTable(String table, String id) {
		String ret="Select * from "+table+" where id='"+id+"'";
		return ret;
	}
	
	public static String selectAllByIdFromTable(String table, String valueName, int valueId) {
		String ret="Select * from "+table+" where "+valueName+"="+valueId;
		return ret;
	}
	
	public static String selectAllByIdFromTable(String table, String valueName, String valueId) {
		String ret="Select * from "+table+" where "+valueName+"='"+valueId+"'";
		return ret;
	}
	
	
	public static Artwork requestToArtworkCasting(ResultSet rs) throws SQLException {
		Artwork artwork= new Artwork();
		int artId=rs.getInt("id");
		String path= rs.getString("path");
		String artName=rs.getString("name");
		String artistId=rs.getString("artistId");
		artwork.setId(artId);
		artwork.setPath(path);
		artwork.setName(artName);
		artwork.setArtistId(artistId);
		JsonObject location= ArtworkHelper.getLocationForArtwork(artwork);
		artwork.setLat(location.get("lat").getAsFloat());
		artwork.setLng(location.get("lng").getAsFloat());
		return artwork;
	}
	
	public static int getHighestIdFromTable(String tableName) throws Exception {
		String command="SELECT id FROM "+tableName+" WHERE id=(SELECT max(id) FROM "+tableName+")";
		ResultSet rs = DALService.sendCommand(command);
		int ret=-1;
		while(rs.next()) {
			ret=rs.getInt("id");
		}
		return ret;
	}
	
	
}
