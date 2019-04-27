package dal_layer;

import java.sql.*;

public class DALService {

	public static DALService INSTANCE = new DALService();
	private static Connection con;
	private static final String PREFIX = "jdbc:oracle:thin:@";
	private static final String URL = "165.227.163.117:";
	private static final int PORT = 1521;
	private static final String SID = "orcl";
	private static final String USERNAME = "dali";
	private static final String PASSWORD = "Ad123456";
	public static String LAST_RESULT=null;

	private DALService() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

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

	public static ResultSet sendCommand(String command) {
		// create the statement object
		try {
			openConnection();
			Statement stmt = con.createStatement();

			// execute query
			ResultSet rs = stmt.executeQuery(command);
			return rs;

			// close the connection object
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
//			con = DriverManager.getConnection(PREFIX + URL + ":" + PORT + ":" + SID, USERNAME, PASSWORD);
			con=DriverManager
                    .getConnection("jdbc:mysql://165.227.163.117/DaliDB?"
                            + "user=orel&password=Dali2019");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}