package oldOraclecontrollers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtistController {

	// get list of arts (including cordinates)
	// get all settings
	// update tag for an art
	// upload art
	// delete art
	// getListOfTags

	@RequestMapping("/Artist/uploadArt")
	public boolean artistRegister(@RequestParam String path, @RequestParam String password) {

		// id=addBrackets(id);
		// password=addBrackets(password);
		// String command="INSERT INTO ARTIST (ARTISTID,PASS) VALUES
		// ("+id+","+password+")";
		// System.out.println(command);
		// boolean response=DALService.INSTANCE.sendCommandDoneRecieve(command);
		// return response;

		return false;
	}

}
