package controllers;

import java.sql.ResultSet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import dal_layer.DALService;

@RestController
@RequestMapping("/user/")
public class UserController {
	// get all settings
	// DownloadArt
	// Get list of arts by cordinates
	// upload score for an art

	@RequestMapping("/getArt")
	public boolean getArtById(@RequestParam(value = "name", defaultValue = "World") String name) {
		return false;
	}

	@RequestMapping("/getArtLocation")
	public String getArtByLocation(@RequestParam String xPosition, @RequestParam String yPosition) {
		Double xMinValue = Double.parseDouble(xPosition) - 0.006;
		Double xMaxValue = Double.parseDouble(xPosition) + 0.006;
		Double yMinValue = Double.parseDouble(yPosition) - 0.006;
		Double yMaxValue = Double.parseDouble(yPosition) + 0.006;
		String command = "select * from art where " + xMinValue + "<xPosition AND XPOSITION<" + xMaxValue
				+ " AND YPOSITION <" + yMaxValue + " AND " + yMinValue + "<YPOSITION";
		
		System.out.println("COMMDN: "+command+"\n\n");
		ResultSet rs = DALService.sendCommand(command);
		try {
			JsonArray finalArray = new JsonArray();
			while (rs.next()) {
					JsonObject tempObj = new JsonObject();
					String artId=rs.getString(1);
					String url= rs.getString(2);
					String artistId=rs.getString(3);
					String tags=rs.getString(4);
					String x=rs.getDouble(5)+"";
					String y=rs.getDouble(6)+"";
					System.out.println("ARTID="+artId +" url="+url+" artistId="+
					artistId+" tags="+tags+" x="+x+" y="+y
							);
					
					tempObj.addProperty("ARTID", artId);
					tempObj.addProperty("URL", url);
					tempObj.addProperty("ARTISTID", artistId);
					tempObj.addProperty("TAGS", tags);
					tempObj.addProperty("XPOSITION",  x);
					tempObj.addProperty("YPOSITION", y);
					finalArray.add(tempObj);
			}
			DALService.closeConnection();
			return finalArray.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		DALService.closeConnection();
		return null;
	}

	public boolean updateTags() {
		return false;
	}

	public boolean getSuggestedArts() {
		return false;
	}
}
