package Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Helpers.ArtistHelper;
import Helpers.QueryHelper;
import Helpers.TableNames;
import Helpers.TagHelper;
import Objects.Artist;
import Objects.Artwork;
import Objects.Tag;
import dal_layer.DALService;

@RestController
@RequestMapping("/web/")
public class WebController implements TableNames, QueryHelper {

	@RequestMapping("/login")
	public boolean login(@RequestParam(value = "id") int id) throws SQLException {
		String command="Select * from Artist where id="+id;
		ResultSet rs=DALService.sendCommand(command);
		if(!rs.next()) {
			return false;
		}
		return true;
	}
	
	@RequestMapping("/register")
	public boolean register(@RequestParam(value = "id") int id,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "picture") String picture) {
		String command = "INSERT INTO Artist (id,name, picture) VALUES ("+id+",'"+name+"','"+picture+"')";
		DALService.sendCommandDataManipulation(command);
		
		return true;
	}
	
	@RequestMapping("/getTags")
	public List<Tag> getTags() throws Exception{
		
		List<Tag> generes = TagHelper.getTagsMethod();
		
		return generes;
	}

	@RequestMapping("/recommendArtwork")
	public Artwork recommendArtwork() throws Exception{
		
		//TODO: to finish after the recommendation system
		
		Artwork artwork = new Artwork();
		//for highest score (2/3 of the time)
		String selectHighestScore = "SELECT tagId,score FROM ML_Viewer_Tag_Score WHERE ViewerId=1 AND score=(SELECT MAX(score) "
				+ "FROM (SELECT * FROM ML_Viewer_Tag_Score WHERE ViewerId=1) AS T)";
		
		//for closest genere- 1/3 of the time
		String command="SELECT tagId,score FROM ML_Viewer_Tag_Score WHERE ViewerId=1";
		
		
		
		return artwork;
	}
	
	
	
	@RequestMapping("/getProfileById")
	public Artist getProfileById(@RequestParam(value = "id") int id) {
		String command = QueryHelper.selectIdFromTable(ARTIST_TABLE, id);
		ResultSet rs = DALService.sendCommand(command);

		try {
			Artist artist = new Artist();
			while (rs.next()) {
				artist = ArtistHelper.requestToArtistCasting(rs);
			}
			DALService.closeConnection();
			return artist;
		} catch (Exception e) {
			e.printStackTrace();
		}
		DALService.closeConnection();

		return null;
	}
	
	@RequestMapping("/updateBio")
	public boolean updateBio(@RequestParam(value = "bio") String bio,
			@RequestParam(value = "artistId") int id){
		String command = "UPDATE Artist SET bio='"+bio+"' WHERE id="+id;
		DALService.sendCommandDataManipulation(command);
		
		return true;
	}

	@RequestMapping("/deleteArt")
	public boolean deleteArtById(@RequestParam(value = "id") int id) {
		String command = QueryHelper.selectIdFromTable("Artwork", id);
		ResultSet st = DALService.sendCommand(command);
		String path = "";
		try {
			while (st.next()) {
				path = st.getString("path");
			}
		} catch (SQLException e) {
			return false;
		}

		command = "Delete from Artwork where id=" + id;
		DALService.sendCommandDataManipulation(command);
		File file = new File(path);
		return file.delete();
	}

	@PostMapping("/uploadArtwork")
	public boolean upload(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("artistId") int artistId) {
		try {
			int id = QueryHelper.getHighestIdFromTable("Artwork") + 1;
			String pathToFile = "/var/www/html/data/files/" + artistId + "/";

			String command = "INSERT INTO Artwork (id, path, name, artistId) VALUES (" + id + ",'" + pathToFile
					+ file.getOriginalFilename() + "','" + name + "'," + artistId + ");";
			DALService.sendCommandDataManipulation(command);
			createDirectoryIfNotExists(pathToFile);
			writeFileToServer(file, pathToFile + file.getOriginalFilename());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private static boolean createDirectoryIfNotExists(String directory) {
		File dir = new File(directory);
		if (!dir.exists())
			dir.mkdirs();
		return true;
	}

	private boolean writeFileToServer(MultipartFile file, String pathToFile) {
		try {
			byte data[] = convertFileToByte(convertMultiPartToFile(file));
			Path path = Paths.get(pathToFile);
			Files.write(path, data);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private byte[] convertFileToByte(File file) throws Exception {
		byte[] bytesArray = new byte[(int) file.length()];

		FileInputStream fis = new FileInputStream(file);
		fis.read(bytesArray); // read file into bytes[]
		fis.close();

		return bytesArray;
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

}
