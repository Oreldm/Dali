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
	
	@RequestMapping("/getNotification")
	public List<String> getNotification(@RequestParam(value = "id") String userId) {
		String command = "SELECT * from Notification where id in (Select Notification from User_Notification where User='"+userId+"')";
		ResultSet rs = DALService.sendCommand(command);
		List<String>notifications=new ArrayList<String>();
		try {
			while (rs.next()) {
				notifications.add(rs.getString("message"));
			}
			
			//find recommended artist
			//get list of artists
			List<Artist>artists=new ArrayList<Artist>();
			command="Select * from User_Artist where UserId='"+userId+"'";
			rs=DALService.sendCommand(command);
			while(rs.next()) {
				Artist artist= (Artist) this.getProfileById(rs.getString("ArtistId"));
				artists.add(artist);
			}
			
			//get list of genres out of artists
			List<Integer>generesId=new ArrayList<Integer>();
			for(Artist artist: artists) {
				for(String genere:artist.getGeneres()) {
					command="Select id from Tags where name='"+genere+"'";
					rs=DALService.sendCommand(command);
					while(rs.next()) {
						generesId.add(rs.getInt("id"));
					}
				}
			}
			
			//find which genre most times
			int maxRepetingGenere=maxRepeating(generesId);
			
			//find closer genre
			command="SELECT * FROM ML_Tags_Connection WHERE tag1Id="+maxRepetingGenere+ " ORDER BY Score DESC LIMIT 1";
			rs=DALService.sendCommand(command);
			int closerGenereId=0;
			while(rs.next()) {
				closerGenereId=rs.getInt("tag2Id");
			}
			
			//find artist with closest genre that the user not following
			command="select artistId FROM Artwork WHERE id IN (SELECT artworkId FROM Artwork_Tag WHERE tagId="+closerGenereId+")";
			rs=DALService.sendCommand(command);
			Artist artistToRecommend=null;
			while(rs.next()) {
				String artistId=rs.getString("artistId");
				if(this.isFollowing(artistId, "id")) {
					//do nothing
				}else {
					artistToRecommend=(Artist) this.getProfileById(artistId);
					break;
				}
			}
			
			//recommend him
			if(artistToRecommend!=null) {
				notifications.add(artistToRecommend.toString());
			}
			
			return notifications;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	// Returns maximum repeating element in arr[0..n-1]. 
    // The array elements are in range from 0 to k-1 
    private static int maxRepeating(List<Integer>arr) 
    { 
    	int n=arr.size();
    	int k=arr.size();
        // Iterate though input array, for every element 
        // arr[i], increment arr[arr[i]%k] by k 
        for (int i = 0; i< n; i++) {
        	arr.set(arr.get(i)%k, arr.get(arr.get(i)%k)+k);
        }
  
        // Find index of the maximum repeating element 
        int max = arr.get(0), result = 0; 
        for (int i = 1; i < n; i++) 
        { 
            if (arr.get(i) > max) 
            { 
                max = arr.get(i); 
                result = i; 
            } 
        } 
  
        // Return index of the maximum element 
        return result; 
    } 
	
	
	@RequestMapping("/deleteNotification")
	public boolean deleteNotification(@RequestParam(value = "id") String userId) {
		String command = "DELETE FROM User_Notification where User='"+userId+"'"; 
		if(DALService.sendCommandDataManipulation(command)==-1)
			return false;
		return true;
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
