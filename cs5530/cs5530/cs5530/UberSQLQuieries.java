package cs5530;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class UberSQLQuieries {
	
	public UberSQLQuieries() {
		
	}
	
	public User createUser(String login, String password, String name, String address, String phone, boolean isDriver, Connector2 con) {
		//String sql = "insert into user (login, password, name, address, phone) values " + "('" + login + "', '" + password + "', '" + name + "', '" + address + "', '" + phone + "');";
			
	 	try {
			String sql = "INSERT INTO user(login, password, name, address, phone) " +  "VALUES (?,?,?,?,?)";
	        PreparedStatement pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, login);
	        pstmt.setString(2, password);
	        pstmt.setString(3, name);
	        pstmt.setString(4, address);
	        pstmt.setString(5, phone);
		 	System.out.println("executing "+sql);
	        if(pstmt.executeUpdate() > 0)
	        {
	        	return new User(login, password);
	        }
	        else
	        {
	        	return null;
	        }
	        
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println(e.getMessage() + "cannot execute the query");
	 	}
	 	return null;
//	 	if (isDriver)
//	 		createDriver(login, output, stmt);

	}
	
	private void createDriver(String login, User u, Statement stmt) {
		String sqldriver = "insert into driver (login) values " + "('" + login +  "')";

		
		String outputs = "";
		ResultSet rs = null;
	 	System.out.println("executing " + sqldriver);
	 	try {
			rs= stmt.executeQuery(sqldriver);
			

			while (rs.next()) {
				//System.out.print("cname:");
				outputs += rs.getString("login");
			}
			 
			rs.close();
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("cannot execute the query");
	 	}
	 	finally
	 	{
	 		try{
		 		if (rs!=null && !rs.isClosed())
		 			rs.close();
		 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
	 	
	 	u.set_isDriver(true);
	 	
	}

}
