package cs5530;

import java.sql.Statement;


public class UberController {
	
	User currentUser;
	UberSQLQuieries sql = new UberSQLQuieries();
	public UberController() {}
	
	public boolean setNewUser(String login, String password, String name, String address, String phone, String isDriver, Statement stmt) {
		boolean _isDriver = false;
		if (isDriver.equals("yes") || isDriver.equals("y")) {
			_isDriver = true;
		}
		currentUser = sql.createUser(login, password, name, address, phone, _isDriver, stmt);
		
		
		if (currentUser != null) {
			return true;
		}
		
		return false;
	}

}
