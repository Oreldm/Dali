package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilesController {
	
	//TODO: to remove because upload will be in ArtistController
	//download will be in UserController
	@RequestMapping("/upload")
	public boolean upload(@RequestParam(value = "name", defaultValue = "World") String name) {
		return false;
	}
	
	public boolean download() {
		return false;
	}
	

}
