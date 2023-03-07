package customclient.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.mojang.util.UUIDTypeAdapter;

import net.minecraft.util.Session;

public class ClientDatabaseManager {
	private static Connection conn;
	public static Statement st;
	
	public static void init() {
		connect();
	}
	
	public static void connect() {
	      String myDriver = "com.mysql.jdbc.Driver";
	      String myUrl = "jdbc:mysql://localhost:3306/mysql";
	      try {
	    	  Class.forName(myDriver);
	    	  conn = DriverManager.getConnection(myUrl, "root", "");
	    	  
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet executeQuery(String query) {
		try {
			if(conn.isClosed())
				connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			 st = conn.createStatement();
			 ResultSet rs = st.executeQuery(query);
			 return rs;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
}
