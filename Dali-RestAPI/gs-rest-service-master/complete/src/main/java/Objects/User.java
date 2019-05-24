package Objects;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
	protected String id;
	protected String pictureUrl;
	protected String name;
	protected List<String>generes=new ArrayList<String>();
	protected String bio;
	protected List<Artwork>likedArtwork=new ArrayList<Artwork>();
	protected List<Artist>following=new ArrayList<Artist>();
	
	//Still not implemented
	protected List<String>recommendedGeneres=new ArrayList<String>();
	protected List<Artist>recommendedArtists=new ArrayList<Artist>();

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getGeneres() {
		return generes;
	}
	public void setGeneres(List<String> generes) {
		this.generes = generes;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public List<Artwork> getLikedArtwork() {
		return likedArtwork;
	}
	public void setLikedArtwork(List<Artwork> likedArtwork) {
		this.likedArtwork = likedArtwork;
	}
	public List<Artist> getFollowing() {
		return following;
	}
	public void setFollowing(List<Artist> following) {
		this.following = following;
	}
	public List<String> getRecommendedGeneres() {
		return recommendedGeneres;
	}
	public void setRecommendedGeneres(List<String> recommendedGeneres) {
		this.recommendedGeneres = recommendedGeneres;
	}
	public List<Artist> getRecommendedArtists() {
		return recommendedArtists;
	}
	public void setRecommendedArtists(List<Artist> recommendedArtists) {
		this.recommendedArtists = recommendedArtists;
	}
}
