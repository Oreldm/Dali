package Objects;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class Artist extends User {
	private List<Artwork> artworks= new ArrayList<Artwork>();
	private List<User> followers= new ArrayList<User>();
	
	public List<Artwork> getArtworks() {
		return artworks;
	}
	public void setArtworks(List<Artwork> artworks) {
		this.artworks = artworks;
	}
	public List<User> getFollowers() {
		return followers;
	}
	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		String jsonInString = gson.toJson(this);
		return jsonInString;
	}
}
