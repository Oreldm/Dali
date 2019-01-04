package dal_layer;

import java.sql.*;

public class DALService {

	public static DALService INSTANCE = new DALService();
	private static Connection con;
	private static final String PREFIX = "jdbc:oracle:thin:@";
	private static final String URL = "dalidb.cuznfsjsfeti.eu-west-1.rds.amazonaws.com";
	private static final int PORT = 1521;
	private static final String SID = "orcl";
	private static final String USERNAME = "dali";
	private static final String PASSWORD = "Ad123456";
	public static String LAST_RESULT=null;

	private DALService() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean sendCommandDontRecieve(String command) {
		DALService.INSTANCE.LAST_RESULT=null;
		try {
			openConnection();
			// create the statement object
			Statement stmt = con.createStatement();

			// execute query
			ResultSet rs = stmt.executeQuery(command);
			closeConnection();

			// close the connection object
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean sendCommand(String command) {
		// create the statement object
		try {
			openConnection();
			Statement stmt = con.createStatement();

			// execute query
			ResultSet rs = stmt.executeQuery("select * from artist where ARTISTID='orel'");
			// HOW TO GET FROM STATEMENT:
			rs.next();
			LAST_RESULT=rs.getString(2);
			
			closeConnection();

			// close the connection object
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static void  closeConnection()  {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void openConnection() {
		try {
			con = DriverManager.getConnection(PREFIX + URL + ":" + PORT + ":" + SID, USERNAME, PASSWORD);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}