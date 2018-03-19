package cs5530;

public class User {
	private String _username;
	private String _password;
	
	public User(String username, String password) {
		this.set_username(username);
		this.set_password(password);
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
}
