package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import dal_layer.DALService;

public class MainTest {
	public static void main(String[]args) {
		ResultSet st = DALService.INSTANCE.sendCommand("Select * from Viewer");
		try {
			System.out.println(st.next() + "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
