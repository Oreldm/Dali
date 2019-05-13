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

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Helpers.ArtistHelper;
import Helpers.QueryHelper;
import Helpers.TableNames;
import Objects.Artist;
import dal_layer.DALService;

@RestController
@RequestMapping("/web/")
public class WebController implements TableNames, QueryHelper {

	@RequestMapping("/login")
	public boolean login() {
		return true;
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
