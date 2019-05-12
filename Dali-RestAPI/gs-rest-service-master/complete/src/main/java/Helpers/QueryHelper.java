package Helpers;

public interface QueryHelper  {
	
	public static String selectIdFromTable(String table, int id) {
		String ret="Select * from "+table+" where id="+id;
		return ret;
	}
	
	public static String selectIdFromTable(String table, String valueName, int valueId) {
		String ret="Select * from "+table+" where "+valueName+"="+valueId;
		return ret;
	}

}
