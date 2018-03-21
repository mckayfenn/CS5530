package cs5530;

import java.sql.Date;
import java.sql.Time;

public class Ride {
	
	private String _vin;
	private String _pid;
	private int _cost;
	private Date _date;
	private Time _fromHour;
	private Time _toHour;
	
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

	public Time get_fromHour() {
		return _fromHour;
	}

	public void set_fromHour(Time _fromHour) {
		this._fromHour = _fromHour;
	}

	public Time get_toHour() {
		return _toHour;
	}

	public void set_toHour(Time _toHour) {
		this._toHour = _toHour;
	}

}
