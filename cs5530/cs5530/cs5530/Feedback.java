package cs5530;

import java.sql.Date;

public class Feedback {
	
	private String _user;
	private int _fid;
	private int _score;
	private String _text;
	private int _vin;
	private Date _date;
	
	private int _rating; // this is average useful rating for this fid from rates table
	
	public Feedback(String user, int fid, int score, String text, int vin, Date date) {
		this.set_user(user);
		this.set_fid(fid);
		this.set_score(score);
		this.set_text(text);
		this.set_vin(vin);
		this.set_date(date);
	}

	public int get_fid() {
		return _fid;
	}

	public void set_fid(int _fid) {
		this._fid = _fid;
	}

	public Date get_date() {
		return _date;
	}

	public void set_date(Date _date) {
		this._date = _date;
	}

	public int get_vin() {
		return _vin;
	}

	public void set_vin(int _vin) {
		this._vin = _vin;
	}

	public String get_text() {
		return _text;
	}

	public void set_text(String _text) {
		this._text = _text;
	}

	public int get_score() {
		return _score;
	}

	public void set_score(int _score) {
		this._score = _score;
	}

	public String get_user() {
		return _user;
	}

	public void set_user(String _user) {
		this._user = _user;
	}

	public int get_rating() {
		return _rating;
	}

	public void set_rating(int _rating) {
		this._rating = _rating;
	}
	

}
