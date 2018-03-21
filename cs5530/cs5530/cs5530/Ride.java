package cs5530;

import java.sql.Date;

public class Ride {
	
	private String _vin;
	private String _pid;
	private int _cost;
	private Date _date;
	
	public Ride(String vin, String pid, int cost, Date date) {
		_vin = vin;
		_pid = pid;
		_cost = cost;
		_date = date;
	}
	
	public String get_vin() {
		return _vin;
	}
	public void set_vin(String _vin) {
		this._vin = _vin;
	}
	
	
	public String get_pid() {
		return _pid;
	}
	public void set_pid(String _pid) {
		this._pid = _pid;
	}
	
	
	public int get_cost() {
		return _cost;
	}
	public void set_cost(int _cost) {
		this._cost = _cost;
	}
	
	
	public Date get_Date() {
		return _date;
	}
	public void set_Date(Date date) {
		this._date = date;
	}

}
