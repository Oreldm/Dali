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

	@RequestMapping("/getTags")
	public List<Tag> getTags() throws Exception{
		
		List<Tag> generes = TagHelper.getTagsMethod();
		
		return generes;
	}


	@RequestMapping("/deleteArt")
	public boolean deleteArtById(@RequestParam(value = "id") int id) {
		//TODO: make sure artist is owner of the artwork
		
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
		
		command = "Delete from Geolocation where artworkId=" + id;
		DALService.sendCommandDataManipulation(command);
		command = "Delete from Artwork_Tag where artworkId=" + id;
		DALService.sendCommandDataManipulation(command);
		command = "Delete from Artwork where id=" + id;
		DALService.sendCommandDataManipulation(command);
		File file = new File(path);
		return file.delete();
	}

	@PostMapping("/uploadArtwork")
	public boolean upload(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("artistId") String artistId, @RequestParam("tagId") int tagId,
			@RequestParam("info") String info,
			@RequestParam("lat") float lat,
			@RequestParam("lng") float lng) {
		try {
			int id = QueryHelper.getHighestIdFromTable("Artwork") + 1;
			String pathToFile = "/var/www/html/data/files/" + artistId + "/";

			String command = "INSERT INTO Artwork (id, path, name, artistId,info) VALUES (" 
			+ id + ",'" + pathToFile + file.getOriginalFilename() + "','" + name + "','" + artistId + "','"+
					info+"');";
			if(DALService.sendCommandDataManipulation(command)== -1) {
				return false;
			}
			createDirectoryIfNotExists(pathToFile);
			writeFileToServer(file, pathToFile + file.getOriginalFilename());
			
			command = "INSERT INTO Artwork_Tag (artworkId,tagId) VALUES ("+id+","+tagId+")";
			if(DALService.sendCommandDataManipulation(command)== -1) {
				return false;
			}
			
			int id2 = QueryHelper.getHighestIdFromTable("Geolocation") + 1;
			command = "INSERT INTO Geolocation (id,artworkId,lat,lng) VALUES ("+id2+","+id+
					","+lat+","+lng+")";
			if(DALService.sendCommandDataManipulation(command)== -1) {
				return false;
			}
			
			//get artist name
			command = "Select name from User where id='"+artistId+"'";
			ResultSet rs = DALService.sendCommand(command);
			String artistName="";
			while(rs.next()) {
				artistName=rs.getString("name");
			}
			
			int id3 = QueryHelper.getHighestIdFromTable("Notification") + 1;
			command = "INSERT INTO Notification (id,message) VALUES ("+id3+","+"'Artist "+artistName+" Uploaded new artwork!')";
			if(DALService.sendCommandDataManipulation(command)== -1) {
				return false;
			}
			
			//update all users notification
			command= "Select UserId from User_Artist where ArtistId='"+artistId+"'";
			rs = DALService.sendCommand(command);
			while(rs.next()) {
				command = "INSERT INTO User_Notification (User,Notification) VALUES ('"+rs.getString("UserId")+"',"+id3+")";
				if(DALService.sendCommandDataManipulation(command)== -1) {
					return false;
				}
			}
			
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
