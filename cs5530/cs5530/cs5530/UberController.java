package cs5530;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.mysql.jdbc.Connection;


public class UberController {
	
	User currentUser;
	private ArrayList<User> drivers = new ArrayList<User>();
	private ArrayList<Reservation> reservations = new ArrayList<Reservation>();
	private ArrayList<Ride> rides = new ArrayList<Ride>();
	private ArrayList<Feedback> feedbackList = new ArrayList<Feedback>();
	
	UberSQLQuieries sql = new UberSQLQuieries();
	public UberController() {}
	
	
	public ArrayList<Reservation> getReservations() {
		return this.reservations;
	}
	
	public ArrayList<Ride> getRides() {
		return this.rides;
	}
	
	public ArrayList<Feedback> getFeedbackRatingList() {
		return this.feedbackList;
	}
	
	public ArrayList<User> getDrivers() {
		return this.drivers;
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
	
	
	/**
	 * 
	 * @param con
	 * @return
	 */
	public ArrayList<Reservation> getPastReservations(Connector2 con) {
		reservations = sql.getPastReservations(currentUser, con);
		return reservations;
	}
	
	
	/**
	 * 
	 * @param con
	 * @return
	 */
	public boolean setRides(Connector2 con) {
		sql.setRideHours(rides, con);
		return sql.setRides(currentUser, rides, con);
	}
	
	/**
	 * 
	 * @param vin
	 * @param con
	 * @return
	 */
	public ArrayList<Feedback> getFeedbackList(String vin, Connector2 con) {
		feedbackList = sql.getFeedbackList(currentUser, Integer.parseInt(vin), con);
		return feedbackList;
	}
	
	
	/**
	 * 
	 * @param fid
	 * @param rating
	 * @param con
	 * @return
	 */
	public boolean setFeedbackRating(int fid, int rating, Connector2 con) {
		return sql.setFeedbackRating(currentUser, fid, rating, con);
	}
	
	
	/**
	 * 
	 * @param con
	 * @return
	 */
	public ArrayList<User> getAllDrivers(Connector2 con) {
		drivers = sql.getAllDrivers(con);
		return drivers;
	}
	/**
	 * returns array list given address [street, city, state]
	 * @return
	 */
	public String[] parseAddress(String address)
	{
		return address.split("-");
	}
	
	/**
	 * 
	 * @param u
	 * @param limit
	 * @param con
	 * @return
	 */
	public ArrayList<Feedback> getFeedbackOnDriver(User u, int limit, Connector2 con) {
		feedbackList = sql.getFeedbackOnDriver(u, limit, con);
		Collections.sort(feedbackList, new Comparator<Feedback>() {
	        public int compare(Feedback c1, Feedback c2) {
	            return (int) (c2.get_rating() - c1.get_rating()); // Ascending
	        }
	
	    });
		
		return feedbackList;
	}
	
	/**
	 * 
	 * @param address
	 * @param make
	 * @param category
	 * @param wantsTrusted
	 * @param con
	 * @return
	 */
	public ArrayList<Car> getCarsBySearch(String address, String make, String category, boolean wantsTrusted, Connector2 con) {
		ArrayList<Car> result;
		if (address == null)
			address = "";
		if (make == null)
			make = "";
		if (category == null)
			category = "";
		if (wantsTrusted)
			result = sql.getCarsBySearchTrusted(currentUser, address, make, category, con);
		else
			result = sql.getCarsBySearch(currentUser, address, make, category, con);
		return result;
	}
	
	/**
	 * <p>
	 * Gets m most ridden cars per category. Returns ArrayList of Strings in format
	 * </p><p>
	 * "(carcount) | (vin) | (category)"
	 * </p>
	 * 
	 * @param String m
	 * @param con
	 * @return
	 */
	public ArrayList<String> statisticsGetMostRiddenCars(String m, Connector2 con) {
		int n = Integer.parseInt(m);
		if (n <= 0) {
			System.out.println("Please enter a positive number");
			return  null;
		}
		return sql.statisticsGetMostRiddenCars(n, con);
	}
	
	/**
	 * <p>
	 * Gets m most expensive cars per category. Returns ArrayList of Strings in format
	 * </p><p>
	 * "(avgcost) | (vin) | (category)"
	 * </p>
	 * 
	 * @param String m
	 * @param con
	 * @return
	 */
	public ArrayList<String> statisticsGetMostExpensiveCars(String m, Connector2 con) {
		int n = Integer.parseInt(m);
		if (n <= 0) {
			System.out.println("Please enter a positive number");
			return  null;
		}
		return sql.statisticsGetMostExpensiveCars(n, con);
	}
	
	
	/**
	 * <p>
	 * Gets m most highly rated drivers per category. Returns ArrayList of Strings in format
	 * </p><p>
	 * "(avgscore) | (driver)"
	 * </p>
	 * 
	 * @param String m
	 * @param con
	 * @return
	 */
	public ArrayList<String> statisticsGetHighlyRatedDrivers(String m, Connector2 con) {
		return sql.statisticsGetHighlyRatedDrivers(Integer.parseInt(m), con);
	}
	
	/**
	 * <p>
	 * Gets m most most trusted users. Returns ArrayList of Strings in format
	 * </p><p>
	 * "(trustedCount) | (user)"
	 * </p>
	 * 
	 * @param String m
	 * @param con
	 * @return
	 */
	public ArrayList<String> awardGetMostTrusted(String m, Connector2 con) {
		int n = Integer.parseInt(m);
		if (n <= 0) {
			System.out.println("Please enter a positive number");
			return  null;
		}
		return sql.awardGetMostTrusted(n, con);
	}
	
	
	/**
	 * <p>
	 * Gets m most most useful users. Returns ArrayList of Strings in format
	 * </p><p>
	 * "(usefulness) | (user)"
	 * </p>
	 * 
	 * @param String m
	 * @param con
	 * @return
	 */
	public ArrayList<String> awardGetMostUseful(String m, Connector2 con) {
		int n = Integer.parseInt(m);
		if (n <= 0) {
			System.out.println("Please enter a positive number");
			return  null;
		}
		return sql.awardGetMostUseful(n, con);
	}
	
	
	/**
	 * 
	 * @param vin
	 * @param con
	 * @return
	 */
	public ArrayList<Car> getUCSuggestions(Connector2 con) {
		ArrayList<Car> cars = sql.getUCSuggestions(currentUser, reservations, con);

		// sort the list of cars by the count of rides taken by other users.
	    Collections.sort(cars, new Comparator<Car>() {
	        public int compare(Car c1, Car c2) {
	            return c2.get_otherRideCount() - c1.get_otherRideCount(); // Ascending
	        }
	
	    });
		
		return cars;
	}
	
	
	public int degreesOfSeparation(String u1, String u2, Connector2 con) {
		return sql.degreesOfSeparation(u1, u2, con);
	}
	
	

}
