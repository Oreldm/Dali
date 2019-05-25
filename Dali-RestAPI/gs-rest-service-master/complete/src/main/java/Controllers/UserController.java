package Controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Helpers.ArtistHelper;
import Helpers.ArtworkHelper;
import Helpers.QueryHelper;
import Helpers.TableNames;
import Helpers.ViewerHelper;
import Objects.Artist;
import Objects.Artwork;
import Objects.User;
import Objects.Viewer;
import dal_layer.DALService;

@RestController
@RequestMapping("/user/")
public class UserController {
	

	@RequestMapping("/login")
	public boolean login(@RequestParam(value = "id") String id) throws SQLException {
		String command = "Select * from User where id='" + id+"'";
		ResultSet rs = DALService.sendCommand(command);
		if (!rs.next()) {
			return false;
		}
		return true;
	}

	@RequestMapping("/registerApp")
	public boolean registerApp(@RequestParam(value = "id") String id, @RequestParam(value = "name") String name,
			@RequestParam(value = "picture") String picture) throws SQLException {
		String command = "INSERT INTO User (id,name, picture) VALUES ('" + id + "','" + name + "','" + picture + "')";
		if (login(id) || DALService.sendCommandDataManipulation(command) == -1) {
			return false;
		}
		return true;
	}
	
	@RequestMapping("/registerWeb")
	public boolean registerWeb(@RequestParam(value = "id") String id, @RequestParam(value = "name") String name,
			@RequestParam(value = "picture") String picture) throws SQLException {
		String command = "INSERT INTO User (id,name, picture) VALUES ('" + id + "','" + name + "','" + picture + "')";
		if (login(id) || DALService.sendCommandDataManipulation(command) == -1) {
			return false;
		}
		setAsArtist(id);
		return true;
	}
	
	@RequestMapping("/signInApp")
	public boolean signInApp(@RequestParam(value = "id") String id, @RequestParam(value = "name") String name,
			@RequestParam(value = "picture") String picture) throws SQLException {
		if(login(id)) {
			return true;
		}
		return registerApp(id,name,picture);
	}
	
	@RequestMapping("/signInWeb")
	public boolean signInWeb(@RequestParam(value = "id") String id, @RequestParam(value = "name") String name,
			@RequestParam(value = "picture") String picture) throws SQLException {
		if(login(id)) {
			setAsArtist(id);
			return true;
		}
		return registerWeb(id,name,picture);
	}

	@RequestMapping("/setAsArtist")
	public boolean setAsArtist(@RequestParam(value = "id") String id) {
		String command = "UPDATE User SET role='artist' WHERE id = '" + id+"'";
		if (DALService.sendCommandDataManipulation(command) == -1) {
			return false;
		}
		return true;
	}

	@RequestMapping("/updateBio")
	public boolean updateBio(@RequestParam(value = "bio") String bio, @RequestParam(value = "id") String id) {
		String command = "UPDATE User SET bio='" + bio + "' WHERE id='" + id+"'";
		DALService.sendCommandDataManipulation(command);

		return true;
	}

	@RequestMapping("/search")
	public List<Artist> search(@RequestParam(value = "str") String str) {
		List<Artist> ret = new ArrayList<Artist>();
		String command = "SELECT * FROM User WHERE User.name LIKE '%" + str + "%'";
		ResultSet rs = DALService.sendCommand(command);
		try {
			while (rs.next()) {
				ret.add(ArtistHelper.requestToArtistCasting(rs));
			}
		} catch (Exception e) {
		}
		return ret;
	}

	@RequestMapping("/followArtist")
	public boolean follow(@RequestParam(value = "artistId") String artistId,
			@RequestParam(value = "userId") String viewerId) {
		if (isFollowing(artistId, viewerId)) {
			return false;
		}
		String command = "INSERT INTO User_Artist (UserId,ArtistId) VALUES ('" + viewerId + "','" + artistId + "')";
		DALService.sendCommandDataManipulation(command);
		return true;
	}

	@RequestMapping("/unfollowArtist")
	public boolean unfollow(@RequestParam(value = "artistId") String artistId,
			@RequestParam(value = "userId") String viewerId) {
		if (!isFollowing(artistId, viewerId)) {
			return false;
		}
		String command = "DELETE from User_Artist WHERE UserId='" + viewerId + "' AND ArtistId='" + artistId+"'";

		DALService.sendCommandDataManipulation(command);
		return true;
	}

	@RequestMapping("/likeArtwork")
	public boolean likeArtwork(@RequestParam(value = "artworkId") int artworkId,
			@RequestParam(value = "userId") String userId) {
		if (isLikedArtwork(artworkId, userId)) {
			return false;
		}

		try {
			String command = "INSERT INTO UserLikedArtwork (ArtworkId,UserId) VALUES (" + artworkId + ",'" + userId
					+ "')";
			DALService.sendCommandDataManipulation(command);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@RequestMapping("/unlikeArtwork")
	public boolean unLikeArtwork(@RequestParam(value = "artworkId") int artworkId,
			@RequestParam(value = "userId") String userId) {
		if (!isLikedArtwork(artworkId, userId)) {
			return false;
		}

		String command = "DELETE from UserLikedArtwork WHERE ArtworkId=" + artworkId + " AND UserId='" + userId+"'";
		DALService.sendCommandDataManipulation(command);
		return true;
	}

	@RequestMapping("/isLikeArtwork")
	public boolean isLikedArtwork(@RequestParam(value = "artworkId") int artworkId,
			@RequestParam(value = "userId") String userId) {
		String command = "SELECT * from UserLikedArtwork where ArtworkId=" + artworkId + " AND UserId='" + userId+"'";
		ResultSet st = DALService.sendCommand(command);
		try {
			if (st.next()) {
				return true;
			}
		} catch (Exception e) {
		}

		return false;
	}

	@RequestMapping("/isFollowing")
	public boolean isFollowing(@RequestParam(value = "artistId") String artistId,
			@RequestParam(value = "userId") String userId) {
		String command = "SELECT * from User_Artist where ArtistId='" + artistId + "' AND UserId='" + userId+"'";
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
	public User getProfileById(@RequestParam(value = "id") String id) {
		String command = QueryHelper.selectIdFromTable("User", id);
		ResultSet rs = DALService.sendCommand(command);
		try {
			User user = null;
			user = (rs.next() && rs.getString("role").toLowerCase().contains("artist"))
					? ArtistHelper.requestToArtistCasting(rs)
					: ViewerHelper.requestToUserCasting(rs);

			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping("/getArt")
	public Artwork getArtById(@RequestParam(value = "id") String id) {
		String command = QueryHelper.selectIdFromTable("Artwork", id);
		ResultSet rs = DALService.sendCommand(command);

		try {
			Artwork artwork = new Artwork();
			while (rs.next()) {
				artwork = ArtworkHelper.requestToArtworkCasting(rs);
			}
			return artwork;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@RequestMapping("/getAllArtsUserLiked")
	public List<Artwork> getAllArtsUserLiked(@RequestParam(value = "id") String userId) {
		String command = "SELECT * from Artwork where id IN (SELECT ArtworkId FROM UserLikedArtwork WHERE userid='"+userId+"')";
		ResultSet rs = DALService.sendCommand(command);
		List<Artwork>artworkList=new ArrayList<Artwork>();
		try {
			while (rs.next()) {
				artworkList.add(ArtworkHelper.requestToArtworkCasting(rs));
			}
			return artworkList;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping("/getArtsByLocation")
	public List<Artwork> getArtByLocation(@RequestParam String lat, @RequestParam String lng) {
		Double xMinValue = Double.parseDouble(lat) - 0.006;
		Double xMaxValue = Double.parseDouble(lat) + 0.006;
		Double yMinValue = Double.parseDouble(lng) - 0.006;
		Double yMaxValue = Double.parseDouble(lng) + 0.006;
		String command = "SELECT * from Artwork WHERE Artwork.id IN " + "(SELECT artworkId FROM Geolocation WHERE "
				+ xMinValue + "<=lat AND lat<=" + xMaxValue + " AND lng<=" + yMaxValue + " AND "
				+ yMinValue + "<=lng) ";

		ResultSet rs = DALService.sendCommand(command);
		try {
			List<Artwork> artworkArrList = new ArrayList<Artwork>();
			while (rs.next()) {
				Artwork artwork = ArtworkHelper.requestToArtworkCasting(rs);

				artworkArrList.add(artwork);
			}
			return artworkArrList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
