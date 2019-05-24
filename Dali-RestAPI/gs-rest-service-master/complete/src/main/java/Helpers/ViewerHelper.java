package Helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Objects.Artist;
import Objects.Artwork;
import Objects.Viewer;
import dal_layer.DALService;

public class ViewerHelper {

	public static Viewer castRequestToSimpleViewer(ResultSet rs) throws SQLException {
		Viewer ret=new Viewer();

		ret.setBio(rs.getString("bio"));
		ret.setPictureUrl(rs.getString("picture"));
		ret.setName(rs.getString("name"));
		ret.setId(rs.getString("id"));

		return ret;
	}
	
	
	public static Viewer requestToUserCasting(ResultSet rs) throws Exception {
		Viewer viewer = new Viewer();
		String id = rs.getString("id");
		String name = rs.getString("name");
		String pictureUrl = rs.getString("picture");
		String bio = rs.getString("bio");

		viewer.setId(id);
		viewer.setPictureUrl(pictureUrl);
		viewer.setName(name);
		viewer.setBio(bio);

		addGeneresToViewer(viewer);
		addFollowingToViewer(viewer);
		addLikedArtworkForViewer(viewer);

		return viewer;
	}
	
	private static Viewer addFollowingToViewer(Viewer viewer) throws Exception {
		viewer.setFollowing(getFollowingToViewer(viewer));
		return viewer;
	}
	
	private static Viewer addGeneresToViewer(Viewer viewer) throws Exception {
		viewer.setGeneres(getGeneresToViewer(viewer));
		return viewer;
	}
	
	
	private static List<Artist> getFollowingToViewer(Viewer viewer) throws Exception {
		String command = "SELECT * from User where id IN (SELECT artistId FROM User_Artist WHERE UserId='"
				+ viewer.getId() + "')";
		ResultSet rs = DALService.sendCommand(command);

		List<Artist> artists = new ArrayList<Artist>();
		while (rs.next()) {
			artists.add(ArtistHelper.requestToArtistCasting(rs));
		}

		return artists;
	}


	private static List<String> getGeneresToViewer(Viewer viewer) throws Exception {
		String command = "SELECT name from Tags where id IN " + "(Select tagId FROM Artwork_Tag where artworkId IN "
				+ "(SELECT artworkId FROM UserLikedArtwork WHERE UserId='" + viewer.getId() + "'))";
		ResultSet rs = DALService.sendCommand(command);

		List<String> ret = new ArrayList<String>();

		while (rs.next()) {
			ret.add(rs.getString("name"));
		}

		return ret;
	}
	
	
	private static Viewer addLikedArtworkForViewer(Viewer viewer) throws Exception {
		viewer.setLikedArtwork(getLikedArtworkForViewer(viewer));
		return viewer;
	}

	private static List<Artwork> getLikedArtworkForViewer(Viewer viewer) throws Exception {
		String command = QueryHelper.selectAllByIdFromTable("UserLikedArtwork", "UserId", viewer.getId());
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
	
	
	
}
