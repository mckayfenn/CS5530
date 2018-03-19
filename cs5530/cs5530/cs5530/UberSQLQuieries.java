package cs5530;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class UberSQLQuieries {
	
	public UberSQLQuieries() {
		
	}
	
	/**
	 * 
	 * @param String: login
	 * @param String: password
	 * @param String: name
	 * @param String: address
	 * @param String: phone
	 * @param Boolean: isDriver
	 * @param Connector2: con
	 * @return a user that has been inserted into the database
	 */
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
	        	User user = new User(login, password, false);
	    	 	if (isDriver)
	    	 		createDriver(login, user, con);
	        	return user;
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


	}
	
	/**
	 * 
	 * @param login
	 * @param u
	 * @param con
	 */
	private void createDriver(String login, User u, Connector2 con) {

	 	try {
	 		String sql = "INSERT INTO driver (login) " +  "VALUES (?)";
	        PreparedStatement pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, login);
		 	System.out.println("executing " + sql);
	        if(pstmt.executeUpdate() > 0)
	        {
	    	 	u.set_isDriver(true);
	        }
	        else
	        {
	        	System.out.println("Could not update driver boolean value");
	        	return;
	        }
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("cannot execute the query");
	 	}
	}
	
	/**
	 * 
	 * @param login
	 * @param password
	 * @param con
	 * @return
	 */
	public User loginUser(String login, String password, Connector2 con) {
		User u;
		ResultSet rs = null;
		String receivedLogin = null;
		String receivedPass = null;
		PreparedStatement pstmt = null;
		try {
	 		String sql = "SELECT login, password from user where login = ?" +  " and password = ?";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, login);
	        pstmt.setString(2, password);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	receivedLogin = rs.getString("login");
	        	receivedPass = rs.getString("password");
	        }
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("cannot execute the query");
	 	}
		finally
	 	{
	 		try{
		 		if (rs!=null && !rs.isClosed()) {
		 			rs.close();
		 		}
		 		pstmt.close();
		 		//con.conn.close();
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		
		if (receivedLogin != null && receivedPass != null) {
			System.out.println("check if driver");
			boolean isDriver = checkIfDriver(receivedLogin, con);
			u = new User(receivedLogin, receivedPass, isDriver);
        	return u;
		}
		
		return null;
	}
	

	/**
	 * 
	 * @param login
	 * @param con
	 * @return
	 */
	private boolean checkIfDriver(String login, Connector2 con) {
		ResultSet rs = null;
		String receivedLogin = null;
		PreparedStatement pstmt = null;
		try {
	 		String sql = "SELECT login from driver where login = ?";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, login);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	receivedLogin += rs.getString("login");
	        }
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("cannot execute the query");
	 	}
		finally
	 	{
	 		try{
		 		if (rs!=null && !rs.isClosed()) {
		 			rs.close();
		 		}
		 		pstmt.close();
		 		con.conn.close();
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		
		if (receivedLogin != null) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param vin
	 * @param category
	 * @param model
	 * @param make
	 * @param year
	 * @param owner
	 * @param con
	 * @return
	 */
	public boolean addNewCar(int vin, String category, String model, String make, int year, String owner, Connector2 con) {
		try {
	 		String sql = "INSERT INTO car (vin, category, model, make, year, owner) " +  "VALUES (?, ?, ?, ?, ?, ?)";
	        PreparedStatement pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setInt(1, vin);
	        pstmt.setString(2, category);
	        pstmt.setString(3, model);
	        pstmt.setString(4, make);
	        pstmt.setInt(5, year);
	        pstmt.setString(6, owner);
		 	System.out.println("executing " + sql);
	        if(pstmt.executeUpdate() > 0)
	        {
	    	 	System.out.println("added a new car");
	    	 	return true;
	        }
	        else
	        {
	        	System.out.println("Could not add new car");
	        	return false;
	        }
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("cannot execute the query");
	 	}
		return false;
	}

}
