package Helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import Objects.Artist;
import Objects.Artwork;
import Objects.User;
import dal_layer.DALService;

public class ArtistHelper implements QueryHelper, TableNames {

	public static Artist requestToArtistCasting(ResultSet rs) throws Exception {
		Artist artist = new Artist();

		int id = rs.getInt("id");
		String pictureUrl = rs.getString("picture");
		String name = rs.getString("name");
		String bio = rs.getString("bio");

		artist.setId(id);
		artist.setPictureUrl(pictureUrl);
		artist.setName(name);
		artist.setBio(bio);

		addGeneresToArtist(artist);
		addArtworksForArtist(artist);
		addFollowersForArtist(artist);
		
		//liked artworks
		//following

		return artist;
	}
	
	public static Artist addFollowersForArtist(Artist artist) throws Exception {
		artist.setFollowers(getFollowersForArtist(artist));
		return artist;
	}
	
	public static List<User> getFollowersForArtist(Artist artist) throws Exception {
		
		String command1 = QueryHelper.selectAllByIdFromTable("ARTIST_ARTIST","artistId2", artist.getId());
		String command2 = QueryHelper.selectAllByIdFromTable("VIEWER_ARTIST","artistId", artist.getId());
		ResultSet rs1 = DALService.sendCommand(command1);
		ResultSet rs2 = DALService.sendCommand(command2);
		List<User> followers = new ArrayList<User>();
		while (rs1.next()) {
			followers.add(getArtistById(rs1.getInt("artistId1")));
		}
		while(rs2.next()) {
			followers.add(ViewerHelper.castRequestToSimpleViewer(rs2));
		}

		return followers;
	}
	
	public static Artist getArtistById(int id) throws Exception {
		String command = QueryHelper.selectIdFromTable("Artist", id);
		ResultSet rs = DALService.sendCommand(command);
		Artist artist=new Artist();
		while(rs.next()) {
			artist.setId(id);
			artist.setName(rs.getString("name"));
			artist.setPictureUrl(rs.getString("picture"));
			artist.setBio(rs.getString("Bio"));
		}
		
		return artist;
		
	}

	public static List<String> getGeneresToArtist(Artist artist) throws Exception {
		//TODO FAST : to change it to get everything from the artworks and to delete the tables
		
		String command = "SELECT * from Tags WHERE id IN (SELECT tagId FROM Artist_Tags WHERE artistId="
				+ artist.getId();
		ResultSet rs = DALService.sendCommand(command);
		List<String> generes = new ArrayList<String>();
		while (rs.next()) {
			generes.add(rs.getString("name"));
		}

		return generes;
	}

	public static Artist addGeneresToArtist(Artist artist) throws Exception {
		artist.setGeneres(getGeneresToArtist(artist));
		return artist;
	}

	public static Artist addArtworksForArtist(Artist artist) throws Exception {
		artist.setArtworks(getArtworksForArtist(artist));
		return artist;
	}

	public static List<Artwork> getArtworksForArtist(Artist artist) throws Exception {
		String command = QueryHelper.selectAllByIdFromTable(ARTWORK_TABLE, "artistId", artist.getId());
		ResultSet rs = DALService.sendCommand(command);
		List<Artwork> artworks = new ArrayList<Artwork>();
		while (rs.next()) {
			artworks.add(ArtworkHelper.requestToArtworkCasting(rs));
		}

		return artworks;
	}

}
