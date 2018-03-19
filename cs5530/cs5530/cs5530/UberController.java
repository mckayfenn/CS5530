package cs5530;

import java.sql.Statement;

import com.mysql.jdbc.Connection;


public class UberController {
	
	User currentUser;
	UberSQLQuieries sql = new UberSQLQuieries();
	public UberController() {}
	
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

}
