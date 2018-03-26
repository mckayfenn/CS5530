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
		PreparedStatement pstmt = null;
	 	try {
			String sql = "INSERT INTO user(login, password, name, address, phone) " +  "VALUES (?,?,?,?,?)";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
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
	 	finally
	 	{
	 		try{
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
	 	return null;


	}
	
	/**
	 * 
	 * @param login
	 * @param u
	 * @param con
	 */
	private boolean createDriver(String login, User u, Connector2 con) {
		PreparedStatement pstmt = null;
		boolean result = false;
	 	try {
	 		String sql = "INSERT INTO driver (login) " +  "VALUES (?)";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, login);
		 	System.out.println("executing " + sql);
	        if(pstmt.executeUpdate() > 0)
	        {
	    	 	u.set_isDriver(true);
	    	 	result = true;
	        }
	        else
	        {
	        	System.out.println("NOT SUCCESSFULL: Could not update driver boolean value");
	        	result = false;
	        }
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("cannot execute the query");
	 	}
	 	finally
	 	{
	 		try{
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
		boolean result = false;
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
			result = true;
		}
		
		return result;
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
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
	 		String sql = "INSERT INTO car (vin, category, model, make, year, owner) " +  "VALUES (?, ?, ?, ?, ?, ?)";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
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
	    	 	result = true;
	        }
	        else
	        {
	        	System.out.println("NOT SUCCESSFULL: Could not add new car");
	        	result = false;
	        }
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("cannot execute the query");
	 	}
		finally
	 	{
	 		try{
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
	
	
	public boolean editCar(int vin, String category, String model, String make, int year, Connector2 con) {
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
	 		String sql = "UPDATE car SET category = ?, model = ?, make = ?, year = ? WHERE vin = ?";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, category);
	        pstmt.setString(2, model);
	        pstmt.setString(3, make);
	        pstmt.setInt(4, year);
	        pstmt.setInt(5, vin);
		 	System.out.println("executing " + sql);
	        if(pstmt.executeUpdate() > 0)
	        {
	    	 	System.out.println("SUCCESSFULL: edited the car in sql");
	    	 	result = true;
	        }
	        else
	        {
	        	System.out.println("NOT SUCCESSFULL: Could not edit car in sql");
	        	result = false;
	        }
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println(e.getMessage() + "\ncannot execute the query");
	 	}
		finally
	 	{
	 		try{
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
	 * @param vin
	 * @param u
	 * @param con
	 * @return
	 */
	public boolean declareFavCar(int vin, User u, Connector2 con) {
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
	 		String sql = "INSERT INTO favorites (login, vin, fvdate) " +  "VALUES (?, ?, ?)";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, u.get_username());
	        pstmt.setInt(2, vin);
	        pstmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
		 	System.out.println("executing " + sql);
	        if(pstmt.executeUpdate() > 0)
	        {
	    	 	System.out.println("SUCCESSFULL: added car as fav");
	    	 	result = true;
	        }
	        else
	        {
	        	System.out.println("NOT SUCCESSFULL: Could not add car as fav");
	        	result = false;
	        }
	 	}
	 	catch(Exception e) {
	 		System.out.println("cannot execute the query: " + e.getMessage());
	 	}
		finally
	 	{
	 		try{
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
	 * @param otherUser
	 * @param isTrusted
	 * @param con
	 * @return
	 */
	public boolean declareTrusted(User currentUser, String otherUser, boolean isTrusted, Connector2 con) {
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
	 		String sql = "INSERT INTO trust (login1, login2, isTrusted) " +  "VALUES (?, ?, ?)";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, currentUser.get_username());
	        pstmt.setString(2, otherUser);
	        pstmt.setInt(3, (isTrusted) ? 1 : -1); // 1 is true, -1 is false
		 	System.out.println("executing " + sql);
	        if(pstmt.executeUpdate() > 0)
	        {
	    	 	System.out.println("SUCCESSFULL: added user as favorited or not");
	    	 	result = true;
	        }
	        else
	        {
	        	System.out.println("NOT SUCCESSFULL: Could not user as favorited or not");
	        	result = false;
	        }
	 	}
	 	catch(Exception e) {
	 		System.out.println("cannot execute the query: " + e.getMessage());
	 	}
		finally
	 	{
	 		try{
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
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
	 		String sql = "INSERT INTO available (login, pid) " +  "VALUES (?, ?)";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, currentUser.get_username());
	        pstmt.setInt(2, pid);
		 	System.out.println("executing " + sql);
	        if(pstmt.executeUpdate() > 0)
	        {
	    	 	System.out.println("SUCCESSFULL: driver set availability hours");
	    	 	result = true;
	        }
	        else
	        {
	        	System.out.println("NOT SUCCESSFULL: Could not set availability hours");
	        	result = false;
	        }
	 	}
	 	catch(Exception e) {
	 		System.out.println("cannot execute the query: " + e.getMessage());
	 	}
		finally
	 	{
	 		try{
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
		boolean result = false;
		for (Reservation reservation : reservations) {
			PreparedStatement pstmt = null;
			try {
		 		String sql = "INSERT INTO reserve (login, vin, pid, cost, date) " +  "VALUES (?, ?, ?, ?, ?)";
		        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
		        pstmt.setString(1, currentUser.get_username());
		        pstmt.setInt(2, Integer.parseInt(reservation.get_vin()));
		        pstmt.setInt(3, Integer.parseInt(reservation.get_pid()));
		        pstmt.setInt(4, reservation.get_cost());
		        pstmt.setDate(5, reservation.get_Date());
			 	System.out.println("executing " + sql);
			 	
		        if(pstmt.executeUpdate() > 0)
		        {
		    	 	System.out.println("SUCCESSFULL: Created one reservation");
		    	 	result = true;
		        }
		        else
		        {
		        	System.out.println("NOT SUCCESSFULL: Could not create reservation");
		        	result = false;
		        	break;
		        }
		 	}
		 	catch(Exception e) {
		 		System.out.println("cannot execute the query: " + e.getMessage());
		 		result = false;
		 	}
			finally
		 	{
		 		try{
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
		}
		
		return result;
	}
	
	
	/**
	 * 
	 * @param currentUser
	 * @param vin
	 * @param score
	 * @param feedback
	 * @param con
	 * @return
	 */
	public boolean giveFeedback(User currentUser, int vin, int score, String feedback, Connector2 con) {
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
	 		String sql = "INSERT INTO feedback (login, vin, score, text, fbdate) " +  "VALUES (?, ?, ?, ?, ?)";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, currentUser.get_username());
	        pstmt.setInt(2, vin);
	        pstmt.setInt(3, score);
	        pstmt.setString(4, feedback);
	        pstmt.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
		 	System.out.println("executing " + sql);
	        if(pstmt.executeUpdate() > 0)
	        {
	    	 	System.out.println("SUCCESSFULL: gave user feedback");
	    	 	result = true;
	        }
	        else
	        {
	        	System.out.println("NOT SUCCESSFULL: Could not give user feedback");
	        }
	 	}
	 	catch(Exception e) {
	 		System.out.println("cannot execute the query: " + e.getMessage());
	 	}
		finally
	 	{
	 		try{
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
	 * @param con
	 * @return
	 */
	public ArrayList<Reservation> getPastReservations(User currentUser, Connector2 con) {
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
	 		String sql = "SELECT * FROM reserve WHERE login = ? AND date <= current_date() AND pid IN (SELECT pid FROM period WHERE fromHour <= current_time())";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, currentUser.get_username());
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	Reservation r = new Reservation(rs.getString("vin"), rs.getString("pid"), rs.getInt("cost"), rs.getDate("date"));
	        	reservations.add(r);
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
		
		return reservations;
	}
	
	/**
	 * 
	 * @param rides
	 * @param con
	 * @return
	 */
	public boolean setRideHours(ArrayList<Ride> rides, Connector2 con) {
		boolean result = true;
		
		for (Ride ride : rides) {
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			try {
		 		String sql = "SELECT fromHour, toHour FROM period WHERE pid = ?";
		        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
		        pstmt.setInt(1, Integer.parseInt(ride.get_pid()));
			 	System.out.println("executing " + sql);
			 	rs = pstmt.executeQuery();
		        while (rs.next()) {
		        	ride.set_fromHour(rs.getTime("fromHour"));
		        	ride.set_toHour(rs.getTime("toHour"));
		        }
		 	}
		 	catch(Exception e)
		 	{
		 		System.out.println("cannot execute the query: " + e.getMessage());
		 		result = false;
		 		break;
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
		 			result = false;
		 			break;
		 		}
		 	}
		}
		
		
		return result;
	}
	
	/**
	 * 
	 * @param currentUser
	 * @param rides
	 * @param con
	 * @return
	 */
	public boolean setRides(User currentUser, ArrayList<Ride> rides, Connector2 con) {
		boolean result = false;
		
		
		for (Ride ride : rides) {
			PreparedStatement pstmt = null;
			try {
		 		String sql = "INSERT INTO ride (login, vin, cost, date, fromHour, toHour) " +  "VALUES (?, ?, ?, ?, ?, ?)";
		        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
		        pstmt.setString(1, currentUser.get_username());
		        pstmt.setInt(2, Integer.parseInt(ride.get_vin()));
		        pstmt.setInt(3, ride.get_cost());
		        pstmt.setDate(4, ride.get_Date());
		        pstmt.setTime(5, ride.get_fromHour());
		        pstmt.setTime(6, ride.get_toHour());
			 	System.out.println("executing " + sql);
			 	
		        if(pstmt.executeUpdate() > 0)
		        {
		    	 	System.out.println("SUCCESSFULL: Created one ride");
		    	 	result = true;
		        }
		        else
		        {
		        	System.out.println("NOT SUCCESSFULL: Could not create ride");
		        	result = false;
		        	break;
		        }
		 	}
		 	catch(Exception e) {
		 		System.out.println("cannot execute the query: " + e.getMessage());
		 		result = false;
		 		break;
		 	}
			finally
		 	{
		 		try{
			 		if(pstmt != null)
			 		{
				 		pstmt.close();
			 		}
		 		}
		 		catch(Exception e)
		 		{
		 			System.out.println("cannot close resultset");
		 			result = false;
		 			break;
		 		}
		 	}
		}
		
		if (result)
			deleteReservations(currentUser, rides, con);
		
		
		return result;
	}
	
	
	/**
	 * 
	 * @param currentUser
	 * @param rides
	 * @param con
	 * @return
	 */
	private boolean deleteReservations(User currentUser, ArrayList<Ride> rides, Connector2 con) {
		boolean result = false;
		
		// this is going through the rides list, but is actually deleting from the reserve table based of off pid
		for (Ride ride : rides) {
			PreparedStatement pstmt = null;
			try {
		 		String sql = "DELETE FROM reserve WHERE login = ? AND vin = ? AND pid = ?";
		        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
		        pstmt.setString(1, currentUser.get_username());
		        pstmt.setInt(2, Integer.parseInt(ride.get_vin()));
		        pstmt.setInt(3, Integer.parseInt(ride.get_pid()));
			 	System.out.println("executing " + sql);
			 	
		        if(pstmt.executeUpdate() > 0)
		        {
		    	 	System.out.println("SUCCESSFULL: deleted a reservation after making rides");
		    	 	result = true;
		        }
		        else
		        {
		        	System.out.println("NOT SUCCESSFULL: Could not delete reservation");
		        	result = false;
		        	break;
		        }
		 	}
		 	catch(Exception e) {
		 		System.out.println("cannot execute the query: " + e.getMessage());
		 		result = false;
		 		break;
		 	}
			finally
		 	{
		 		try{
			 		if(pstmt != null)
			 		{
				 		pstmt.close();
			 		}
		 		}
		 		catch(Exception e)
		 		{
		 			System.out.println("cannot close resultset");
		 			result = false;
		 			break;
		 		}
		 	}
		}
		
		return result;
	}
	
	
	/**
	 * 
	 * @param vin
	 * @param con
	 * @return
	 */
	public ArrayList<Feedback> getFeedbackList(User currentUser, int vin, Connector2 con) {
		ArrayList<Feedback> result = new ArrayList<Feedback>();
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
	 		String sql = "SELECT * FROM feedback where vin = ? and login != ?";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setInt(1, vin);
	        pstmt.setString(2, currentUser.get_username());
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	Feedback r = new Feedback(rs.getString("login"), rs.getInt("fid"), rs.getInt("score"), rs.getString("text"), vin, rs.getDate("fbdate"));
	        	result.add(r);
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
	 * @param fid
	 * @param rating
	 * @param con
	 * @return
	 */
	public boolean setFeedbackRating(User currentUser, int fid, int rating, Connector2 con) {
		boolean result = false;
		
		PreparedStatement pstmt = null;
		try {
	 		String sql = "INSERT INTO rates (login, fid, rating) " +  "VALUES (?, ?, ?)";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, currentUser.get_username());
	        pstmt.setInt(2, fid);
	        pstmt.setInt(3, rating);
		 	System.out.println("executing " + sql);
		 	
	        if(pstmt.executeUpdate() > 0)
	        {
	    	 	System.out.println("SUCCESSFULL: added a rating for a feedback");
	    	 	result = true;
	        }
	        else
	        {
	        	System.out.println("NOT SUCCESSFULL: Could not add a rating");
	        	result = false;
	        }
	 	}
	 	catch(Exception e) {
	 		System.out.println("cannot execute the query: " + e.getMessage());
	 		result = false;
	 	}
		finally
	 	{
	 		try{
		 		if(pstmt != null)
		 		{
			 		pstmt.close();
		 		}
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 			result = false;
	 		}
	 	}
		
		return result;
	}
	
	
	public ArrayList<User> getAllDrivers(Connector2 con) {
		ArrayList<User> drivers = getDriverNames(con);
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		for(int i = 0; i < drivers.size(); i++)
		{
			try {
	        	String sql2 = "SELECT * FROM car WHERE owner = ?";
	        	pstmt = (PreparedStatement) con.conn.prepareStatement(sql2);
	        	pstmt.setString(1, drivers.get(i).get_username());
	        	
	        	rs = pstmt.executeQuery();
	        	
	        	ArrayList<Car> cars = new ArrayList<Car>();
	        	while (rs.next()) {
	        		Car c = new Car(rs.getInt("vin"), rs.getString("category"), rs.getString("model"), rs.getString("make"), rs.getInt("year"), rs.getString("owner"));
	        		cars.add(c);
	        		
	        	}
	        	drivers.get(i).set_cars(cars);
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
			 		if(pstmt != null) {
				 		pstmt.close();
			 		}
		 		}
		 		catch(Exception e)
		 		{
		 			System.out.println("cannot close resultset");
		 		}
		 	}
	 	}
	 	
		return drivers;
	}
	private static ArrayList<User> getDriverNames(Connector2 con)
	{
		ArrayList<User> arr = new ArrayList<User>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "SELECT user.login, user.name, user.password FROM driver JOIN user ON user.login = driver.login";
	 		
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	User u = new User(rs.getString("login"), rs.getString("password"), true);
	        	u.set_fullname(rs.getString("name"));
	        	arr.add(u);
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
		 		if(pstmt != null) {
			 		pstmt.close();
		 		}
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
	        	
		return arr;
	}
	
	/**
	 * 
	 * @param u
	 * @param limit
	 * @param con
	 * @return
	 */
	public ArrayList<Feedback> getFeedbackOnDriver(User u, int limit, Connector2 con) {
		ArrayList<Feedback> result = new ArrayList<Feedback>();
		
		for(Car c : u.get_cars()) {
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			try {
				String sql = "select F.fid, F.score, F.login, F.text, F.vin, F.fbdate, A.rating from feedback F left outer join (select fid, avg(rating) as rating from rates where fid IN (select fid from feedback where vin = 9999) group by fid) A on F.fid = A.fid where F.vin = ? order by rating DESC limit ?";
		 		
		        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
		        pstmt.setInt(1, c.get_vin());
		        pstmt.setInt(2, limit);
			 	System.out.println("executing " + sql);
			 	rs = pstmt.executeQuery();
		        while (rs.next()) {
		        	Feedback f = new Feedback(rs.getString("login"), rs.getInt("fid"), rs.getInt("score"), rs.getString("text"), rs.getInt("vin"), rs.getDate("fbdate"));
		        	f.set_rating((double) rs.getDouble("rating"));
		        	result.add(f);
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
			 		if(pstmt != null) {
				 		pstmt.close();
			 		}
		 		}
		 		catch(Exception e)
		 		{
		 			System.out.println("cannot close resultset");
		 		}
		 	}
		}
		
		return result;
	}
	
	
	/**
	 * 
	 * @param currentUser
	 * @param address
	 * @param make
	 * @param category
	 * @param con
	 * @return
	 */
	public ArrayList<Car> getCarsBySearch(User currentUser, String address, String make, String category, Connector2 con) {
		ArrayList<Car> cars = new ArrayList<Car>();
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "select A.vin, A.category, A.make, A.model, A.year, A.owner, avg(F.score) as avgscore from (select * from car where (make like ?) or (category like ?) or car.owner IN (select login from user where address like ?)) A left outer join feedback F on A.vin = F.vin group by A.vin order by avgscore Desc";
			pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, "%" + make + "%");
	        pstmt.setString(2, "%" + category + "%");
	        pstmt.setString(3, "%" + address + "%");
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	Car c = new Car(rs.getInt("vin"), rs.getString("category"), rs.getString("model"), rs.getString("make"), rs.getInt("year"), rs.getString("owner"));
	        	c.set_avgFeedbackScore(rs.getDouble("avgscore"));
        		cars.add(c);
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
		 		if(pstmt != null) {
			 		pstmt.close();
		 		}
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		
		return cars;
	}
	
	
	/**
	 * 
	 * @param currentUser
	 * @param address
	 * @param make
	 * @param category
	 * @param con
	 * @return
	 */
	public ArrayList<Car> getCarsBySearchTrusted(User currentUser, String address, String make, String category, Connector2 con) {
		ArrayList<Car> cars = new ArrayList<Car>();
		
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "select A.vin, A.category, A.make, A.model, A.year, A.owner, avg(F.score) as avgscore from (select * from car where (make like ?) or (category like ?) or car.owner IN (select login from user where address like ?)) A left outer join (select * from feedback join trust on feedback.login = trust.login2 and trust.isTrusted = 1 where trust.login1 = ?) F on A.vin = F.vin group by A.vin order by avgscore Desc";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setString(1, "%" + make + "%");
	        pstmt.setString(2, "%" + category + "%");
	        pstmt.setString(3, "%" + address + "%");
	        pstmt.setString(4, currentUser.get_username());
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	Car c = new Car(rs.getInt("vin"), rs.getString("category"), rs.getString("model"), rs.getString("make"), rs.getInt("year"), rs.getString("owner"));
	        	c.set_avgFeedbackScore(rs.getDouble("avgscore"));
        		cars.add(c);
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
		 		if(pstmt != null) {
			 		pstmt.close();
		 		}
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		
		return cars;
	}
	
	
	/**
	 * 
	 * @param m
	 * @param con
	 * @return
	 */
	public ArrayList<String> statisticsGetMostRiddenCars(int m, Connector2 con) {
		ArrayList<String> result = new ArrayList<String>();
		
		result.add("Luxury");
		result.addAll(getMostCarLuxury(m, con));
		result.add("Comfort");
		result.addAll(getMostCarComfort(m, con));
		result.add("Standard");
		result.addAll(getMostCarStandard(m, con));
		
		return result;
	}
	
	private ArrayList<String> getMostCarLuxury(int m, Connector2 con) {
		ArrayList<String> result = new ArrayList<String>();
		
		String output = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "select count(car.vin) as carcount, car.vin as vin, car.category as category from ride join car on ride.vin = car.vin where category = 'Luxury' group by car.vin order by count(car.vin) DESC limit ?";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setInt(1, m);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	output = rs.getInt("carcount") + " | " + rs.getInt("vin") + " | " + rs.getString("category");
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
		 		if(pstmt != null) {
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
	
	
	private ArrayList<String> getMostCarComfort(int m, Connector2 con) {
		ArrayList<String> result = new ArrayList<String>();
		
		String output = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "select count(car.vin) as carcount, car.vin as vin, car.category as category from ride join car on ride.vin = car.vin where category = 'Comfort' group by car.vin order by count(car.vin) DESC limit ?";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setInt(1, m);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	output = rs.getInt("carcount") + " | " + rs.getInt("vin") + " | " + rs.getString("category");
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
		 		if(pstmt != null) {
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
	
	
	private ArrayList<String> getMostCarStandard(int m, Connector2 con) {
		ArrayList<String> result = new ArrayList<String>();
		
		String output = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "select count(car.vin) as carcount, car.vin as vin, car.category as category from ride join car on ride.vin = car.vin where category = 'Standard' group by car.vin order by count(car.vin) DESC limit ?";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setInt(1, m);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	output = rs.getInt("carcount") + " | " + rs.getInt("vin") + " | " + rs.getString("category");
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
		 		if(pstmt != null) {
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
	 * @param m
	 * @param con
	 * @return
	 */
	public ArrayList<String> statisticsGetMostExpensiveCars(int m, Connector2 con) {
		ArrayList<String> result = new ArrayList<String>();
		
		result.add("Luxury");
		result.addAll(getMostExpensiveLuxury(m, con));
		result.add("Comfort");
		result.addAll(getMostExpensiveComfort(m, con));
		result.add("Standard");
		result.addAll(getMostExpensiveStandard(m, con));
		
		return result;
	}
	
	
	private ArrayList<String> getMostExpensiveLuxury(int m, Connector2 con) {
		ArrayList<String> result = new ArrayList<String>();
		
		String output = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "select avg(ride.cost) as avgride, car.vin, car.category from ride join car on ride.vin = car.vin where car.category = 'Luxury' group by car.vin order by avgride DESC limit ?";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setInt(1, m);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	output = rs.getInt("avgride") + " | " + rs.getInt("vin") + " | " + rs.getString("category");
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
		 		if(pstmt != null) {
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
	private ArrayList<String> getMostExpensiveComfort(int m, Connector2 con) {
		ArrayList<String> result = new ArrayList<String>();
		
		String output = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "select avg(ride.cost) as avgride, car.vin, car.category from ride join car on ride.vin = car.vin where car.category = 'Comfort' group by car.vin order by avgride DESC limit ?";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setInt(1, m);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	output = rs.getInt("avgride") + " | " + rs.getInt("vin") + " | " + rs.getString("category");
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
		 		if(pstmt != null) {
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
	private ArrayList<String> getMostExpensiveStandard(int m, Connector2 con) {
		ArrayList<String> result = new ArrayList<String>();
		
		String output = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "select avg(ride.cost) as avgride, car.vin, car.category from ride join car on ride.vin = car.vin where car.category = 'Standard' group by car.vin order by avgride DESC limit ?";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setInt(1, m);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	output = rs.getInt("avgride") + " | " + rs.getInt("vin") + " | " + rs.getString("category");
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
		 		if(pstmt != null) {
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
	 * @param m
	 * @param con
	 * @return
	 */
	public ArrayList<String> statisticsGetHighlyRatedDrivers(int m, Connector2 con) {
		ArrayList<String> result = new ArrayList<String>();
	
		result.add("Luxury");
		result.addAll(getHighlyRatedLuxury(m, con));
		result.add("Comfort");
		result.addAll(getHighlyRatedComfort(m, con));
		result.add("Standard");
		result.addAll(getHighlyRatedStandard(m, con));
		
		return result;
	}
	private ArrayList<String> getHighlyRatedLuxury(int m, Connector2 con) {
		ArrayList<String> result = new ArrayList<String>();
		
		String output = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "select avg(feedback.score) as avgscore, car.owner as driver from feedback join car on feedback.vin = car.vin where car.category = 'Luxury' group by car.owner order by avgscore DESC limit ?";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setInt(1, m);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	output = rs.getInt("avgscore") + " | " + rs.getString("driver");
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
		 		if(pstmt != null) {
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
	private ArrayList<String> getHighlyRatedComfort(int m, Connector2 con) {
		ArrayList<String> result = new ArrayList<String>();
		
		String output = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "select avg(feedback.score) as avgscore, car.owner as driver from feedback join car on feedback.vin = car.vin where car.category = 'Comfort' group by car.owner order by avgscore DESC limit ?";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setInt(1, m);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	output = rs.getInt("avgscore") + " | " + rs.getString("driver");
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
		 		if(pstmt != null) {
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
	private ArrayList<String> getHighlyRatedStandard(int m, Connector2 con) {
		ArrayList<String> result = new ArrayList<String>();
		
		String output = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "select avg(feedback.score) as avgscore, car.owner as driver from feedback join car on feedback.vin = car.vin where car.category = 'Standard' group by car.owner order by avgscore DESC limit ?";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setInt(1, m);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	output = rs.getInt("avgscore") + " | " + rs.getString("driver");
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
		 		if(pstmt != null) {
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
	 * @param m
	 * @param con
	 * @return
	 */
	public ArrayList<String> awardGetMostTrusted(int m, Connector2 con) {
		ArrayList<String> result = new ArrayList<String>();
		
		String output = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "select sum(isTrusted) as trustedCount, login2 from trust group by login2 order by trustedCount DESC limit ?";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setInt(1, m);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	output = rs.getInt("trustedCount") + " | " + rs.getString("login2");
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
		 		if(pstmt != null) {
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
	 * @param m
	 * @param con
	 * @return
	 */
	public ArrayList<String> awardGetMostUseful(int m, Connector2 con) {
		ArrayList<String> result = new ArrayList<String>();
		
		String output = "";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "select avg(rates.rating) as usefulness, feedback.login from rates join feedback on rates.fid = feedback.fid group by feedback.login order by usefulness DESC limit ?";
	        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
	        pstmt.setInt(1, m);
		 	System.out.println("executing " + sql);
		 	rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	output = rs.getDouble("usefulness") + " | " + rs.getString("login");
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
		 		if(pstmt != null) {
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
	 * @param vin
	 * @param con
	 * @return
	 */
	public ArrayList<Car> getUCSuggestions(User currentUser, ArrayList<Reservation> reservations, Connector2 con) {
		ArrayList<Car> result = new ArrayList<Car>();
		ArrayList<String> carVins = new ArrayList<String>();
		
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		for (Reservation reserve : reservations) {
			int vin = Integer.parseInt(reserve.get_vin());
			
			
			try {
				String sql = "select count(ride.vin) as otherRides, car.vin, car.category, car.make, car.model, car.year, car.owner from ride join car on ride.vin = car.vin where login IN (select login from ride where ride.vin = ? and login != '?') and ride.vin != ? group by car.vin order by otherRides DESC";
		        pstmt = (PreparedStatement) con.conn.prepareStatement(sql);
		        pstmt.setInt(1, vin);
		        pstmt.setString(2, currentUser.get_username());
		        pstmt.setInt(3, vin);
			 	System.out.println("executing " + sql);
			 	rs = pstmt.executeQuery();
		        while (rs.next()) {
		        	
		        	if (carVins.contains(Integer.toString(rs.getInt("vin")))) {
		        		for (Car c : result) {
		        			if (c.get_vin() == (rs.getInt("vin"))) {
		        				c.set_otherRideCount(c.get_otherRideCount() + 1);
		        			}
		        		}
		        	}
		        	else {
		        	
			        	Car c = new Car(rs.getInt("vin"), rs.getString("category"), rs.getString("model"), rs.getString("make"), rs.getInt("year"), rs.getString("owner"));
			        	c.set_otherRideCount(rs.getInt("otherRides"));
			        	
			        	result.add(c);
		        	}
		        	
		        	carVins.add(Integer.toString(rs.getInt("vin")));
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
			 		if(pstmt != null) {
				 		pstmt.close();
			 		}
		 		}
		 		catch(Exception e)
		 		{
		 			System.out.println("cannot close resultset");
		 		}
		 	}
			
			
			
		}
		
		
		
		
		return result;
	}

}