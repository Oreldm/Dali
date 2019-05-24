package Objects;

import java.util.List;

public class Artwork {
	private int id;
	private String path;
	private String name;
	private int artistId;
	private float positionX;
	private float positionY;
	private String dt_created;
	private List<String>generes;
	private List<Integer>generesIds;
	private String info;
	private String artistName;
	private String artistPicture;
	
	
	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getArtistPicture() {
		return artistPicture;
	}

	public void setArtistPicture(String artistPicture) {
		this.artistPicture = artistPicture;
	}
	
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<Integer> getGeneresIds() {
		return generesIds;
	}

	public void setGeneresIds(List<Integer> generesIds) {
		this.generesIds = generesIds;
	}

	public List<String> getGeneres() {
		return generes;
	}

	public void setGeneres(List<String> generes) {
		this.generes = generes;
	}

	public Artwork() {}
	
	public Artwork(int id, String path, String name, int artistId, float positionX, float positionY) {
		super();
		this.id = id;
		this.path = path;
		this.name = name;
		this.artistId = artistId;
		this.positionX = positionX;
		this.positionY = positionY;
	}
	public float getPositionX() {
		return positionX;
	}
	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}
	public float getPositionY() {
		return positionY;
	}
	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPath() {
		if(path.contains("/var/www/")){
			path = path.replace("/var/www/", "www.project-dali.com/");
		}
		return path;
	}
	public void setPath(String path) {
		if(path.contains("/var/www/html/")){
			path = path.replace("/var/www/html/", "project-dali.com/");
		}
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getArtistId() {
		return artistId;
	}
	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}

	public String getDt_created() {
		return dt_created;
	}

	public void setDt_created(String dt_created) {
		this.dt_created = dt_created;
	}
}
