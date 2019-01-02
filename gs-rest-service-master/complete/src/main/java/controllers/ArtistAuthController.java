package controllers;

import dal_layer.DALService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtistAuthController {
	//register
	@RequestMapping("/ArtistRegister")
	public boolean artistRegister(@RequestParam String id, @RequestParam String password) {
		id="'"+id+"'";
		password="'"+password+"'";
		String command="INSERT INTO ARTIST (ARTISTID,PASS) VALUES ("+id+","+password+")";
		System.out.println(command);
		boolean response=DALService.INSTANCE.sendCommand(command);
		return response;
	}
	
	//boolean verify password

}
