package cs5530;

import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;


public class UberController {
	
	User currentUser;
	private ArrayList<Reservation> reservations = new ArrayList<Reservation>();
	private ArrayList<Ride> rides = new ArrayList<Ride>();
	
	UberSQLQuieries sql = new UberSQLQuieries();
	public UberController() {}
	
	
	public ArrayList<Reservation> getReservations() {
		return this.reservations;
	}
	
	public ArrayList<Ride> getRides() {
		return this.rides;
	}
	
	/**
	 * 
	 * @param login
	 * @param password
	 * @param name
	 * @param address
	 * @param phone
	 * @param isDriver
	 * @param con
	 * @return
	 */
	public boolean setNewUser(String login, String password, String name, String address, String phone, String isDriver, Connector2 con) {
		boolean _isDriver = false;
		if (isDriver.equals("yes") || isDriver.equals("y")) {
			_isDriver = true;
		}
		currentUser = sql.createUser(login, password, name, address, phone, _isDriver, con);
		
		
		if (currentUser != null) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param login
	 * @param password
	 * @param con
	 * @return
	 */
	public boolean loginUser(String login, String password, Connector2 con) {
		currentUser = sql.loginUser(login, password, con);
		
		if (currentUser != null) {
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
		if (currentUser.get_isDriver() && currentUser.get_username().equals(owner))
			return sql.addNewCar(vin, category, model, make, year, owner, con);
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
	public boolean editCar(int vin, String category, String model, String make, int year, String owner, Connector2 con) {
		if (currentUser.get_isDriver() && currentUser.get_username().equals(owner))
			return sql.editCar(vin, category, model, make, year, con);
		return false;
	}
	
	
	/**
	 * 
	 * @param vin
	 * @param u
	 * @param con
	 * @return
	 */
	public boolean declareFavCar(int vin, String u, Connector2 con) {
		if (currentUser.get_username().equals(u))
			return sql.declareFavCar(vin, currentUser, con);
		return false;
	}
	
	
	/**
	 * 
	 * @param otherUser
	 * @param isTrusted
	 * @param con
	 * @return
	 */
	public boolean declareTrusted(String otherUser, String isTrusted, Connector2 con) {
		boolean _isTrusted = false;
		if (isTrusted.equals("yes") || isTrusted.equals("y"))
			_isTrusted = true;
		
		return sql.declareTrusted(currentUser, otherUser, _isTrusted, con);
	}
	
	
	/**
	 * 
	 * @param con
	 * @return
	 */
	public ArrayList driverViewPeriods(Connector2 con) {
		if (!currentUser.get_isDriver())
			return null; // don't do anything if user isn't a driver
		return sql.driverViewPeriods(con);
	}
	
	/**
	 * 
	 * @param s
	 * @param con
	 * @return
	 */
	public boolean driverSetAvailability(String s, Connector2 con) {
		if (!currentUser.get_isDriver())
			return false;
		String[] parse = s.split(" ");
		int i = Integer.parseInt(parse[0]);
		System.out.println(i);
		return sql.driverSetAvailability(currentUser, i, con);
	}
	
	
	/**
	 * 
	 * @param vin
	 * @param con
	 * @return
	 */
	public ArrayList getAvailableReservationTimes(String vin, Connector2 con) {
		return sql.getAvailableReservationTimes(Integer.parseInt(vin), con);
	}
	
	
	/**
	 * 
	 * @param con
	 * @return
	 */
	public boolean setReservations(Connector2 con) {
		return sql.setReservations(currentUser, reservations, con);
	}
	
	
	/**
	 * 
	 * @param vin
	 * @param score
	 * @param feedback
	 * @param con
	 * @return
	 */
	public boolean giveFeedback(String vin, int score, String feedback, Connector2 con) {
		int v = Integer.parseInt(vin);
		return sql.giveFeedback(currentUser, v, score, feedback, con);
	}
	
	
	public ArrayList<Reservation> getPastReservations(Connector2 con) {
		return sql.getPastReservations(currentUser, con);
	}

}
