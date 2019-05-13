package Controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import Helpers.ArtistHelper;
import Helpers.ArtworkHelper;
import Helpers.QueryHelper;
import Helpers.TableNames;
import Objects.Artist;
import Objects.Artwork;
import Objects.Viewer;
import dal_layer.DALService;

@RestController
@RequestMapping("/android/")
public class AndroidController implements QueryHelper, TableNames {

	@RequestMapping("/login")
	public boolean login() {
		return true;
	}

	@RequestMapping("/likeArtwork")
	public boolean likeArtwork(@RequestParam(value = "artworkId") int artworkId,
			@RequestParam(value = "userId") int userId) {
		if (isLikedArtwork(artworkId, userId)) {
			return false;
		}

		String command = "SELECT id from ViewerLikedArtwork ORDER BY id DESC LIMIT 1";
		ResultSet rs = DALService.sendCommand(command);

		try {
			int id=1;
			if (rs.isBeforeFirst()) {
				rs.next();
				id = rs.getInt("id") + 1;
			}else {
				System.out.println("No data");
			}
			String command2 = "INSERT INTO ViewerLikedArtwork (id,artworkId,viewerId) VALUES (" + id + "," + artworkId
					+ "," + userId + ")";
			DALService.sendCommandDataManipulation(command2);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@RequestMapping("/isLikeArtwork")
	public boolean isLikedArtwork(@RequestParam(value = "artworkId") int artworkId,
			@RequestParam(value = "userId") int userId) {
		String command = "SELECT * from ViewerLikedArtwork where artworkId=" + artworkId + " AND viewerId=" + userId;
		ResultSet st = DALService.sendCommand(command);
		try {
			if (st.next()) {
				return true;
			}
		} catch (Exception e) {
		}

		return false;
	}

	@RequestMapping("/getProfileById")
	public Viewer getProfileById(@RequestParam(value = "id") int id) {
		String command = QueryHelper.selectIdFromTable(VIEWER_TABLE, id);
		ResultSet rs = DALService.sendCommand(command);

		try {
			Viewer viewer = new Viewer();
			while (rs.next()) {
				viewer = requestToUserCasting(rs);
			}
			DALService.closeConnection();
			return viewer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		DALService.closeConnection();

		return null;
	}

	@RequestMapping("/getArt")
	public Artwork getArtById(@RequestParam(value = "id") int id) {
		String command = QueryHelper.selectIdFromTable(ARTWORK_TABLE, id);
		ResultSet rs = DALService.sendCommand(command);

		try {
			Artwork artwork = new Artwork();
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
		String command = "SELECT * from Artwork WHERE Artwork.id IN " + "(SELECT artworkId FROM Geolocation WHERE "
				+ xMinValue + "<=xPosition AND xPosition<=" + xMaxValue + " AND yPosition<=" + yMaxValue + " AND "
				+ yMinValue + "<=yPosition) ";

		ResultSet rs = DALService.sendCommand(command);
		try {
			List<Artwork> artworkArrList = new ArrayList<Artwork>();
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

	private Viewer requestToUserCasting(ResultSet rs) throws Exception {
		Viewer viewer = new Viewer();
		int id = rs.getInt("id");
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

	private static Viewer addLikedArtworkForViewer(Viewer viewer) throws Exception {
		viewer.setLikedArtwork(getLikedArtworkForViewer(viewer));
		return viewer;
	}

	private static List<Artwork> getLikedArtworkForViewer(Viewer viewer) throws Exception {
		String command = QueryHelper.selectAllByIdFromTable("ViewerLikedArtwork", "viewerId", viewer.getId());
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

	private List<Artist> getFollowingToViewer(Viewer viewer) throws Exception {
		String command = "SELECT * from Artist where id IN (SELECT artistId FROM Viewer_Artist WHERE viewerId="
				+ viewer.getId() + ")";
		ResultSet rs = DALService.sendCommand(command);

		List<Artist> artists = new ArrayList<Artist>();
		while (rs.next()) {
			artists.add(ArtistHelper.requestToArtistCasting(rs));
		}

		return artists;
	}

	private Viewer addFollowingToViewer(Viewer viewer) throws Exception {
		viewer.setFollowing(getFollowingToViewer(viewer));
		return viewer;
	}

	private List<String> getGeneresToViewer(Viewer viewer) throws Exception {
		String command = "SELECT name from Tags where id IN " + "(Select tagId FROM Artwork_Tag where artworkId IN "
				+ "(SELECT artworkId FROM ViewerLikedArtwork WHERE viewerId=" + viewer.getId() + "))";
		ResultSet rs = DALService.sendCommand(command);

		List<String> ret = new ArrayList<String>();

		while (rs.next()) {
			ret.add(rs.getString("name"));
		}

		return ret;
	}

	private Viewer addGeneresToViewer(Viewer viewer) throws Exception {
		viewer.setGeneres(getGeneresToViewer(viewer));
		return viewer;
	}

	private Artwork requestToArtworkCasting(ResultSet rs) throws SQLException {
		Artwork artwork = new Artwork();
		int artId = rs.getInt("id");
		String path = rs.getString("path");
		String artName = rs.getString("name");
		int artistId = rs.getInt("artistId");
		artwork.setId(artId);
		artwork.setPath(path);
		artwork.setName(artName);
		artwork.setArtistId(artistId);
		JsonObject location = ArtworkHelper.getLocationForArtwork(artwork);
		artwork.setPositionX(location.get("x").getAsFloat());
		artwork.setPositionY(location.get("y").getAsFloat());
		return artwork;
	}

}
