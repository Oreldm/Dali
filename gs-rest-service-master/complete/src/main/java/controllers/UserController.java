package controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	//get all settings
	//DownloadArt
	//Get list of arts by cordinates
	//upload score for an art
	
	@RequestMapping("/getArt")
	public boolean getArtById(@RequestParam(value = "name", defaultValue = "World") String name) {
		return false;
	}
	
	public boolean updateTags() {
		return false;
	}
	
	public boolean getSuggestedArts() {
		return false;
	}
}
