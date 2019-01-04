package controllers;

import dal_layer.DALService;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtistAuthController {

	@RequestMapping("/Artist/Register")
	public boolean artistRegister(@RequestParam String id, @RequestParam String password) {
		id = addBrackets(id);
		password = addBrackets(password);
		String command = "INSERT INTO ARTIST (ARTISTID,PASS) VALUES (" + id + "," + password + ")";
		System.out.println(command);
		boolean response = DALService.INSTANCE.sendCommandDontRecieve(command);
		return response;
	}

	@RequestMapping("/Artist/VerifyPassword")
	public boolean verifyPassword(@RequestParam String user, @RequestParam String password) {
		// TODO: make it like that select * from artist where ARTISTID='' AND PASS=''
		user = addBrackets(user);

		String command = "SELECT * FROM ARTIST WHERE user=" + user;
		System.out.println(command);
		DALService.sendCommand(command);
		if (DALService.LAST_RESULT.equals(password)) {
			return true;
		}
		return false;

	}

	private static String addBrackets(String str) {
		return ("'" + str + "'");
	}
}
