package cs5530;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
	        	System.out.println("NOT SUCCESSFULL: Could not update driver boolean value");
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
		 		if(pstmt != null)
		 		{
			 		pstmt.close();
		 		}
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
		 		if(pstmt != null)
		 		{
			 		pstmt.close();
		 		}
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
	    	 	System.out.println("SUCCESSFULL: added a new car");
	    	 	return true;
	        }
	        else
	        {
	        	System.out.println("NOT SUCCESSFULL: Could not add new car");
	        	return false;
	        }
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("cannot execute the query");
	 	}
		return false;
	}
	
	
	public boolean editCar(int vin, String category, String model, String make, int year, Connector2 con) {
		try {
	 		String sql = "UPDATE car SET category = ?, model = ?, make = ?, year = ? WHERE vin = ?";
	        PreparedStatement pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, category);
	        pstmt.setString(2, model);
	        pstmt.setString(3, make);
	        pstmt.setInt(4, year);
	        pstmt.setInt(5, vin);
		 	System.out.println("executing " + sql);
	        if(pstmt.executeUpdate() > 0)
	        {
	    	 	System.out.println("SUCCESSFULL: edited the car in sql");
	    	 	return true;
	        }
	        else
	        {
	        	System.out.println("NOT SUCCESSFULL: Could not edit car in sql");
	        	return false;
	        }
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println(e.getMessage() + "\ncannot execute the query");
	 	}
		return false;
	}
	
	
	/**
	 * 
	 * @param vin
	 * @param u
	 * @param con
	 * @return
	 */
	public boolean declareFavCar(int vin, User u, Connector2 con) {
		try {
	 		String sql = "INSERT INTO favorites (login, vin, fvdate) " +  "VALUES (?, ?, ?)";
	        PreparedStatement pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, u.get_username());
	        pstmt.setInt(2, vin);
	        pstmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
		 	System.out.println("executing " + sql);
	        if(pstmt.executeUpdate() > 0)
	        {
	    	 	System.out.println("SUCCESSFULL: added car as fav");
	    	 	return true;
	        }
	        else
	        {
	        	System.out.println("NOT SUCCESSFULL: Could not add car as fav");
	        	return false;
	        }
	 	}
	 	catch(Exception e) {
	 		System.out.println("cannot execute the query: " + e.getMessage());
	 	}
		return false;
	}
	
	
	/**
	 * 
	 * @param currentUser
	 * @param otherUser
	 * @param isTrusted
	 * @param con
	 * @return
	 */
	public boolean declareTrusted(User currentUser, String otherUser, boolean isTrusted, Connector2 con) {
		try {
	 		String sql = "INSERT INTO trust (login1, login2, isTrusted) " +  "VALUES (?, ?, ?)";
	        PreparedStatement pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, currentUser.get_username());
	        pstmt.setString(2, otherUser);
	        pstmt.setInt(3, (isTrusted) ? 1 : 0); // 1 is true, 0 is false
		 	System.out.println("executing " + sql);
	        if(pstmt.executeUpdate() > 0)
	        {
	    	 	System.out.println("SUCCESSFULL: added user as favorited or not");
	    	 	return true;
	        }
	        else
	        {
	        	System.out.println("NOT SUCCESSFULL: Could not user as favorited or not");
	        	return false;
	        }
	 	}
	 	catch(Exception e) {
	 		System.out.println("cannot execute the query: " + e.getMessage());
	 	}
		return false;
	}
	
	
	/**
	 * 
	 * @param currentUser
	 * @param vin
	 * @param pid
	 * @param cost
	 * @param date
	 * @return
	 */
	/*
	public boolean makeReservation(User currentUser, int vin, int pid, int cost, Date date) {
		try {
	 		String sql = "INSERT INTO reserve (login1, login2, isTrusted) " +  "VALUES (?, ?, ?)";
	        PreparedStatement pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, currentUser.get_username());
	        pstmt.setString(2, otherUser);
	        pstmt.setInt(3, (isTrusted) ? 1 : 0); // 1 is true, 0 is false
		 	System.out.println("executing " + sql);
	        if(pstmt.executeUpdate() > 0)
	        {
	    	 	System.out.println("SUCCESSFULL: added user as favorited or not");
	    	 	return true;
	        }
	        else
	        {
	        	System.out.println("Could not user as favorited or not");
	        	return false;
	        }
	 	}
	 	catch(Exception e) {
	 		System.out.println("cannot execute the query: " + e.getMessage());
	 	}
		
		return false;
	}
	*/
	
	
	/**
	 * 
	 * @param con
	 * @return
	 */
	public ArrayList driverViewPeriods(Connector2 con) {
		ResultSet rs = null;
		String output = null;
		PreparedStatement pstmt = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
	 		String sql = "SELECT * FROM period";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	output = rs.getString("pid") + " | " + rs.getString("fromHour") + " | " + rs.getString("toHour");
	        	result.add(output);
	        }
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("cannot execute the query: " + e.getMessage());
	 	}
		finally
	 	{
	 		try{
		 		if (rs!=null && !rs.isClosed()) {
		 			rs.close();
		 		}
		 		if(pstmt != null)
		 		{
			 		pstmt.close();
		 		}
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		
		
		return result;
	}
	
	
	/**
	 * 
	 * @param currentUser
	 * @param pid
	 * @param con
	 * @return
	 */
	public boolean driverSetAvailability(User currentUser, int pid, Connector2 con) {
		try {
	 		String sql = "INSERT INTO available (login, pid) " +  "VALUES (?, ?)";
	        PreparedStatement pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, currentUser.get_username());
	        pstmt.setInt(2, pid);
		 	System.out.println("executing " + sql);
	        if(pstmt.executeUpdate() > 0)
	        {
	    	 	System.out.println("SUCCESSFULL: driver set availability hours");
	    	 	return true;
	        }
	        else
	        {
	        	System.out.println("NOT SUCCESSFULL: Could not set availability hours");
	        	return false;
	        }
	 	}
	 	catch(Exception e) {
	 		System.out.println("cannot execute the query: " + e.getMessage());
	 	}
		return false;
	}
	
	
	/**
	 * 
	 * @param vin
	 * @param con
	 * @return
	 */
	public ArrayList getAvailableReservationTimes(int vin, Connector2 con) {
		ResultSet rs = null;
		String output = null;
		PreparedStatement pstmt = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
	 		String sql = "SELECT * FROM period WHERE pid IN (SELECT pid FROM available WHERE login = (SELECT owner FROM car WHERE vin = ?))";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setInt(1, vin);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	output = rs.getString("pid") + " | " + rs.getString("fromHour") + " | " + rs.getString("toHour");
	        	result.add(output);
	        }
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("cannot execute the query: " + e.getMessage());
	 	}
		finally
	 	{
	 		try{
		 		if (rs!=null && !rs.isClosed()) {
		 			rs.close();
		 		}
		 		if(pstmt != null)
		 		{
			 		pstmt.close();
		 		}
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		
		return result;
	}
	
	/**
	 * 
	 * @param currentUser
	 * @param reservations
	 * @param con
	 * @return
	 */
	public boolean setReservations(User currentUser, ArrayList<Reservation> reservations, Connector2 con) {
		
		for (Reservation reservation : reservations) {
			try {
		 		String sql = "INSERT INTO reserve (login, vin, pid, cost, date) " +  "VALUES (?, ?, ?, ?, ?)";
		        PreparedStatement pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
		        pstmt.setString(1, currentUser.get_username());
		        pstmt.setInt(2, Integer.parseInt(reservation.get_vin()));
		        pstmt.setInt(3, Integer.parseInt(reservation.get_pid()));
		        pstmt.setInt(4, reservation.get_cost());
		        pstmt.setDate(5, reservation.get_Date());
			 	System.out.println("executing " + sql);
			 	
		        if(pstmt.executeUpdate() > 0)
		        {
		    	 	System.out.println("SUCCESSFULL: Created one reservation");
		        }
		        else
		        {
		        	System.out.println("NOT SUCCESSFULL: Could not create reservation");
		        	return false;
		        }
		 	}
		 	catch(Exception e) {
		 		System.out.println("cannot execute the query: " + e.getMessage());
		 		return false;
		 	}
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param vin
	 * @param con
	 * @return
	 */
	private String getOwnerOfVin(int vin, Connector2 con) {
		ResultSet rs = null;
		String output = null;
		PreparedStatement pstmt = null;
		try {
	 		String sql = "SELECT owner FROM car WHERE vin = ?";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setInt(1, vin);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	output = rs.getString("owner");
	        }
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("cannot execute the query: " + e.getMessage());
	 	}
		finally
	 	{
	 		try{
		 		if (rs!=null && !rs.isClosed()) {
		 			rs.close();
		 		}
		 		if(pstmt != null)
		 		{
			 		pstmt.close();
		 		}
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		
		return output;
	}
	
	
	/**
	 * 
	 * @param login
	 * @param con
	 * @return
	 */
	private ArrayList getAvailableDriverTimes(String login, Connector2 con) {
		ResultSet rs = null;
		String output = null;
		PreparedStatement pstmt = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
	 		String sql = "SELECT * FROM period WHERE pid IN (SELECT pid FROM available WHERE login = ?)";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, login);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	output = rs.getString("pid") + " | " + rs.getString("fromHour") + " | " + rs.getString("toHour");
	        	result.add(output);
	        }
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("cannot execute the query: " + e.getMessage());
	 	}
		finally
	 	{
	 		try{
		 		if (rs!=null && !rs.isClosed()) {
		 			rs.close();
		 		}
		 		if(pstmt != null)
		 		{
			 		pstmt.close();
		 		}
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		
		
		return result;
	}

}
