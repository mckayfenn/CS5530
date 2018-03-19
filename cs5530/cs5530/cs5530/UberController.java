package cs5530;

import java.sql.Statement;

import com.mysql.jdbc.Connection;


public class UberController {
	
	User currentUser;
	UberSQLQuieries sql = new UberSQLQuieries();
	public UberController() {}
	
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

}
