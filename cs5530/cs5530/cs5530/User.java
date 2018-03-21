package cs5530;

import java.util.ArrayList;

public class User {
	private String _username;
	private String _password;
	private boolean _isDriver;
	private String _fullname;
	
	private ArrayList<Car> _cars;
	
	public User() {
		
	}
	
	public User(String username, String password, boolean isDriver) {
		this.set_username(username);
		this.set_password(password);
		this.set_isDriver(isDriver);
	}

	public String get_username() {
		return _username;
	}

	public void set_username(String _username) {
		this._username = _username;
	}

	public String get_password() {
		return _password;
	}

	public void set_password(String _password) {
		this._password = _password;
	}

	public boolean get_isDriver() {
		return _isDriver;
	}

	public void set_isDriver(boolean _isDriver) {
		this._isDriver = _isDriver;
	}

	public ArrayList<Car> get_cars() {
		return _cars;
	}

	public void set_cars(ArrayList<Car> _cars) {
		this._cars = _cars;
	}
	
	public void addCar(Car car) {
		if (_isDriver)
			_cars.add(car);
	}

	public String get_fullname() {
		return _fullname;
	}

	public void set_fullname(String _fullname) {
		this._fullname = _fullname;
	}
}
