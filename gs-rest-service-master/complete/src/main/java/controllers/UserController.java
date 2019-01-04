package controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class UserController {
	//get all settings
	//DownloadArt
	//Get list of arts by cordinates
	//upload score for an art
	
	@RequestMapping("/getArt")
	public boolean getArtById(@RequestParam(value = "name", defaultValue = "World") String name) {
		return false;
	}
	
	@RequestMapping("/getArtLocation")
	public boolean getArtByLocation(@RequestParam String xPosition, @RequestParam String yPosition) {
		Double xMinValue=Double.parseDouble(xPosition) - 0.006;
		Double xMaxValue=Double.parseDouble(xPosition)+ 0.006;
		Double yMinValue=Double.parseDouble(yPosition) - 0.006;
		Double yMaxValue=Double.parseDouble(yPosition)+ 0.006;
		String command= "select * from art where "+ xMinValue+"<xPosition AND XPOSITION<"+xMaxValue+" AND YPOSITION <"+yMaxValue+" AND "+yMinValue+"<YPOSITION";
		
		return false;
	}
	
	public boolean updateTags() {
		return false;
	}
	
	public boolean getSuggestedArts() {
		return false;
	}
}
