package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class connection {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		System.out.println("hello");
		System.out.println(System.currentTimeMillis());
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		
		Connection conn=DriverManager.getConnection(
		        "jdbc:ucanaccess://C:\\Users\\Anshuman\\Documents\\Database1.accdb");
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery("SELECT [Nayme] FROM [temptable]");
		while (rs.next()) {
		    System.out.println(rs.getString(1));
		}
	}
}
