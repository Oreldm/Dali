package dal_layer;

import java.sql.*;

public class DALService {
	
	public static DALService INSTANCE=new DALService();
	private static Connection con; 
	private final String PREFIX="jdbc:oracle:thin:@";
	private final String URL="dalidb.cuznfsjsfeti.eu-west-1.rds.amazonaws.com";
	private final int PORT=1521;
	private final String SID="orcl";
	private final String USERNAME="dali";
	private final String PASSWORD="Ad123456";
	
	
	private DALService() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			this.con=DriverManager.getConnection(PREFIX+URL+":"+PORT+":"+SID, USERNAME, PASSWORD);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean sendCommand(String command) {
		try {
		// create the statement object
		Statement stmt = con.createStatement();

		// execute query
		ResultSet rs = stmt.executeQuery(command);
//HOW TO GET FROM STATEMENT:
//		while (rs.next())
//			System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));

		// close the connection object
		con.close();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}