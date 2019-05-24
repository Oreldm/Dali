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

		String id = rs.getString("id");
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
		addFollowingToArtist(artist);
		addLikedArtworkForArtist(artist);

		return artist;
	}

	public static Artist addFollowingToArtist(Artist artist) throws Exception {
		artist.setFollowing(getFollowing(artist));
		return artist;
	}

	public static List<Artist> getFollowing(Artist artist) throws Exception {
		String command = QueryHelper.selectAllByIdFromTable("User_Artist", "ArtistId", artist.getId());
		ResultSet rs = DALService.sendCommand(command);
		List<Artist> ret = new ArrayList<Artist>();
		while (rs.next()) {
			Artist tempArtist = new Artist();
			String command2 = QueryHelper.selectIdFromTable("User", rs.getString("ArtistId"));
			ResultSet rs2 = DALService.sendCommand(command2);
			while (rs2.next()) {
				tempArtist.setName(rs2.getString("name"));
				tempArtist.setId(rs2.getString("id"));
				tempArtist.setPictureUrl(rs2.getString("picture"));
				tempArtist.setBio(rs2.getString("Bio"));
			}
			ret.add(tempArtist);
		}

		return ret;

	}

	public static Artist addLikedArtworkForArtist(Artist artist) throws Exception {
		artist.setLikedArtwork(getLikedArtworkForArtist(artist));
		return artist;
	}

	public static List<Artwork> getLikedArtworkForArtist(Artist artist) throws Exception {
		String command = QueryHelper.selectAllByIdFromTable("UserLikedArtwork", "UserId", artist.getId());
		ResultSet rs = DALService.sendCommand(command);
		List<Artwork> ret = new ArrayList<Artwork>();
		while (rs.next()) {
			String command2 = QueryHelper.selectIdFromTable(TableNames.ARTWORK_TABLE, rs.getInt("ArtworkId"));
			ResultSet rs2 = DALService.sendCommand(command2);
			while (rs2.next()) {
				ret.add(ArtworkHelper.requestToArtworkCasting(rs2));
			}
		}
		return ret;
	}

	public static Artist addFollowersForArtist(Artist artist) throws Exception {
		artist.setFollowers(getFollowersForArtist(artist));
		return artist;
	}

	public static List<User> getFollowersForArtist(Artist artist) throws Exception {

		String command = QueryHelper.selectAllByIdFromTable("User_Artist", "ArtistId", artist.getId());
		ResultSet rs = DALService.sendCommand(command);
		List<User> followers = new ArrayList<User>();
		while (rs.next()) {
			followers.add(getArtistById(rs.getString("UserId")));
		}

		return followers;
	}

	public static Artist getArtistById(String id) throws Exception {
		String command = QueryHelper.selectIdFromTable("User", id);
		ResultSet rs = DALService.sendCommand(command);
		Artist artist = new Artist();
		while (rs.next()) {
			artist.setId(id);
			artist.setName(rs.getString("name"));
			artist.setPictureUrl(rs.getString("picture"));
			artist.setBio(rs.getString("Bio"));
		}

		return artist;
	}

	public static List<String> getGeneresToArtist(Artist artist) throws Exception {
		String command = QueryHelper.selectAllByIdFromTable("Artwork", "artistId", artist.getId());
		ResultSet rs = DALService.sendCommand(command);
		List<String> generes = new ArrayList<String>();
		while (rs.next()) {
			int artworkid = rs.getInt("id");
			String command2 = QueryHelper.selectAllByIdFromTable("Artwork_Tag", "artworkId", artworkid);
			ResultSet rs2 = DALService.sendCommand(command2);
			while (rs2.next()) {
				int tagId = rs2.getInt("tagId");
				String command3 = QueryHelper.selectIdFromTable("Tags", tagId);
				ResultSet rs3 = DALService.sendCommand(command3);
				while (rs3.next()) {
					generes.add(rs3.getString("name"));
				}
			}
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
