package cs5530;

public class Car {
	
	private int _vin;
	private String _category;
	private String _model;
	private String _make;
	private int _year;
	private String _owner;
	
	private double _avgFeedbackScore;
	
	private int _otherRideCount;
	
	public Car(int vin, String category, String model, String make, int year, String owner) {
		this.set_vin(vin);
		this.set_category(category);
		this.set_model(model);
		this.set_make(make);
		this.set_year(year);
		this.set_owner(owner);
	}
	
	
	public int get_vin() {
		return _vin;
	}
	public void set_vin(int _vin) {
		this._vin = _vin;
	}
	public String get_category() {
		return _category;
	}
	public void set_category(String _category) {
		this._category = _category;
	}
	public String get_model() {
		return _model;
	}
	public void set_model(String _model) {
		this._model = _model;
	}
	public String get_make() {
		return _make;
	}
	public void set_make(String _make) {
		this._make = _make;
	}
	public int get_year() {
		return _year;
	}
	public void set_year(int _year) {
		this._year = _year;
	}
	public String get_owner() {
		return _owner;
	}
	public void set_owner(String _owner) {
		this._owner = _owner;
	}


	public double get_avgFeedbackScore() {
		return _avgFeedbackScore;
	}


	public void set_avgFeedbackScore(double _avgFeedbackScore) {
		this._avgFeedbackScore = _avgFeedbackScore;
	}


	public int get_otherRideCount() {
		return _otherRideCount;
	}


	public void set_otherRideCount(int _otherRideCount) {
		this._otherRideCount = _otherRideCount;
	}
	
	

}
