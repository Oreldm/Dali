package com.project_dali;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dal_layer.DALService;

public class MainTest {
	public static void main(String[]args) {
		ResultSet st = DALService.INSTANCE.sendCommand("Select * from Viewer");
		ArrayList<String> arrayList = new ArrayList<String>(); 
		try {
			while (st.next()) {              
			        int i = 1;
			        while(i <= 1) {
			            arrayList.add(st.getString(i++));
			        }
			        System.out.println(st.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
