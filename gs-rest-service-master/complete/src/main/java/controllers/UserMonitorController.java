package controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserMonitorController {
	
	//TODO: might remove it 
	@RequestMapping("/uploadscore")
	public boolean uploadScoreForArt(@RequestParam(value = "name", defaultValue = "World") String name) {
		return false;
	}
	
}
