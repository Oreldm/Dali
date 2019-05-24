package Helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonObject;

import Controllers.UserController;
import Objects.Artist;
import Objects.Artwork;
import Objects.User;
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
		String info= rs.getString("info");
		artwork.setInfo(info);
		artwork.setDt_created(rs.getString("dt_created"));
		artwork.setId(artId);
		artwork.setPath(path);
		artwork.setName(artName);
		artwork.setArtistId(artistId);
		JsonObject location = ArtworkHelper.getLocationForArtwork(artwork);
		artwork.setPositionX(location.get("x").getAsFloat());
		artwork.setPositionY(location.get("y").getAsFloat());
		
		String command="SELECT * from Tags WHERE id IN (SELECT tagId FROM Artwork_Tag WHERE artworkId="+artId+")";
		ResultSet rs2=DALService.sendCommand(command);
		List<String>generes=new ArrayList<String>();
		List<Integer>generesIds=new ArrayList<Integer>();
		while(rs2.next()) {
			generes.add(rs2.getString("name"));
			generesIds.add(rs2.getInt("id"));
		}
		artwork.setGeneres(generes);
		artwork.setGeneresIds(generesIds);
		
		command= QueryHelper.selectIdFromTable("User", artistId);
		rs2=DALService.sendCommand(command);
		while(rs2.next()) {
			String name = rs2.getString("name");
			String picture= rs2.getString("picture");
			artwork.setArtistName(name);
			artwork.setArtistPicture(picture);
		}

		return artwork;
	}
	
	

	
}
