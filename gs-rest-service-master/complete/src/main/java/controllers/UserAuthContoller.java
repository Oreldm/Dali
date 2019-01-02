package controllers;

import dal_layer.DALService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthContoller {
	//register
	@RequestMapping("/UserRegister")
	public boolean userRegister(@RequestParam String id, @RequestParam String password) {
		id="'"+id+"'";
		password="'"+password+"'";
		boolean response=DALService.INSTANCE.sendCommand("INSERT INTO ARTIST (ARTISTID,PASS) VALUES ("+id+","+password+");");
		return response;
	}
	
	//boolean verify password

}
