package cs5530;


import java.lang.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.*;

public class CommandLineView {
	
	
	/**
	 * @param args
	 */
	public static void displayMainMenu()
	{
		 System.out.println("Welcome to UUBER please login");
    	 System.out.println(" Please select one of the following options ");
    	 System.out.println("1. login existing user ");
    	 System.out.println("2. register new user");
    	 System.out.println("3. exit ");
	}
	public static void displayResConfirmationInfo(String vin, int cost, String date)
	{
		 System.out.println("Confirmation information before finalizing reservation");
    	 System.out.println("Car vin #: " + vin);
    	 System.out.println("Date of reservation: " + date);
    	 //System.out.println("Time of reservation: " + times);
    	 System.out.println("Bidding Cost: " + cost);
	}
	public static void displayRideConfirmation(Ride ride)
	{
		 System.out.println("Confirmation information before finalizing ride and completed reservation");
    	 System.out.println("Car vin #: " + ride.get_vin());
    	 System.out.println("Date of ride: " + ride.get_Date());
    	 System.out.println("Cost of ride: " + ride.get_pid());
	}
	public static void displayReservationStatusChoices()
	{
		 System.out.println("Make a selection on what you would like to do next: ");
    	 System.out.println("1. Confirm and finalize confirmation(s)");
    	 System.out.println("2. Make another reservation");
    	 System.out.println("3. Cancel reservation(s)");
	}
	public static void displayRideStatusChoices()
	{
		 System.out.println("Make a selection on what you would like to do next: ");
    	 System.out.println("1. Confirm and finalize ride(s)");
    	 System.out.println("2. Mark another reservation as ride");
    	 System.out.println("3. Cancel ride(s)");
	}
	public static void displaySearchStatusResults()
	{
		System.out.println("Conduct search now or add more search filters?: ");
	   	System.out.println("1. Conduct search based on filer(s) already chosen");
	   	System.out.println("2. Add another search filter");
	   	System.out.println("3. Cancel searching");
	}
	public static void displayCars(ArrayList<Car> list) {
		System.out.println("Results:");
		for(int i = 0; i < list.size(); i++)
		{
			System.out.println("Avg Rating: "+ list.get(i).get_avgFeedbackScore() + " Owner: " + list.get(i).get_owner() + " Vin # : " + list.get(i).get_vin() + " Category: " + list.get(i).get_category() + " Make: " + list.get(i).get_make() + 
					" Model: " + list.get(i).get_model() + " Year: " + list.get(i).get_year()+ "\n");
		}
	}
	public static void displayCategoryOptions()
	{
		System.out.println("Pick a Category: ");
	   	System.out.println("1. Luxury");
	   	System.out.println("2. Comfort");
	   	System.out.println("3. Standard");
	}
	public static void displayFilterByTrusted()
	{
	   	System.out.println("1. Filter results only based on trusted user reviews");
	   	System.out.println("2. Show all results reguardless of user reviews");
	}
	public static void displayUserMenu(String username, boolean isDriver)
	{
		 System.out.println("Welcome: " + username + ". Please select from the following: ");
    	 System.out.println("User options: ");
    	 System.out.println("1. exit ");
    	 System.out.println("2. Declare a favorite car.");
    	 System.out.println("3. Denote user as trusted");
    	 System.out.println("4. Make Car Reservation");
    	 System.out.println("5. Give Car feedback");
    	 System.out.println("6. Mark reservation as ride");
    	 System.out.println("7. Rate feedback on a car");
    	 System.out.println("8. View most useful feedbacks on driver");
    	 System.out.println("9. Search for car");

		 if(isDriver) {
	    	 System.out.println("Driver options: ");
	    	 System.out.println("10. Add new car");
	    	 System.out.println("11. Edit exisiting car");
	    	 System.out.println("12. Add availability times");
		 }

	}
	public static void displayAvailabilityOptions(ArrayList<String> list)
	{
		System.out.println("Select ID of time you are available: ");
		System.out.println("ID | From Hour | To Hour");
		for(int i = 0; i < list.size(); i++)
		{
			System.out.println(list.get(i));
		}
	}
	public static void displayPastReservations(ArrayList<Reservation> list)
	{
		System.out.println("Here is a list of past reservations you made. \nPlease select a reservation that has been completed (A Ride): ");
		for(int i = 0; i < list.size(); i++)
		{
			System.out.println(i + ". " + " Vin #: " + list.get(i).get_vin() + " Date: " + list.get(i).get_Date() + " Cost: " + list.get(i).get_cost());
		}
	}
	public static void displayFeedback(ArrayList<Feedback> list, String vin)
	{
		System.out.println("List of feedbacks for car (vin): " + vin + "\nYou can select a feedback to rate it's usefulness");
		for(int i = 0; i < list.size(); i++)
		{
			System.out.println(i + ". " + " User: " + list.get(i).get_user() + " Date: " + list.get(i).get_date() + " Car Score: " + list.get(i).get_score() +
					"\n" + "\t" + "Feedback: " + list.get(i).get_text());
		}
	}
	public static void displaySearchFilters(String make, String address, String category)
	{
		System.out.println("Make a selection what filter(s) you wish to search for a car: ");
		if(category == null)
		{
		   	 System.out.println("1. Search by category (standard, comfort, luxury): ");
		}
		if(address == null)
		{
		   	 System.out.println("2. Search by State or City");
		}
		if(make == null)
		{
		   	 System.out.println("3. Search by car make");
		}
		if(make != null && address != null && category != null)
		{
			System.out.println("All filters already selected : press 4 to continue");
		}
	}
	public static void displayDriverFeedback(ArrayList<Feedback> list, int n, User user)
	{
		System.out.println("Top " + n + " Most useful feebacks for driver: " + user.get_fullname() + "\n");
		for(int i = 0; i < list.size(); i++)
		{
			System.out.println("Usefulness Rating: " + list.get(i).get_rating() + " -User: " + list.get(i).get_user() + " -Date: " + list.get(i).get_date() + " -Car Score: " + list.get(i).get_score() +
					"\n" + "\t" + "-Feedback: " + list.get(i).get_text() + "\n");
		}
	}
	public static void displayDrivers(ArrayList<User> list)
	{
		System.out.println("Pick a driver to view their feedback");
		for(int i = 0; i < list.size(); i++)
		{
			System.out.println(i + ". " + " Driver name: " + list.get(i).get_fullname());
			System.out.println("\t\t" + "Car(s): ");
			ArrayList<Car> cars = list.get(i).get_cars();
			for(int j = 0; j < cars.size(); j ++)
			{
				System.out.println("\t\t\t->" + " Vin #: " + cars.get(j).get_vin() + " Make: " + cars.get(j).get_make() + " Model: " + cars.get(j).get_model());
			}
		}
	}
	
	public static void main(String[] args) {
		UberController controller = new UberController();
		Connector2 con = null;
		mainMenu(controller, con);
	}
	public static void mainMenu(UberController controller, Connector2 con)
	{
		String choice;
        String username;
        String password;
        String name;
        String street;
        String city;
        String state;
        String address;
        String phone;
        String isDriver;
        int choiceAsInt = 0;
         try
		 {
			//remember to replace the password
			 	 con= new Connector2();
	             System.out.println ("Connection established");
	         
	             BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	             
	             while(true)
	             {
	            	 displayMainMenu();
	            	 while ((choice = in.readLine()) == null && choice.length() == 0);
	            	 try{
	            		 choiceAsInt = Integer.parseInt(choice);
	            	 }catch (Exception e)
	            	 {
	            		 
	            		 continue;
	            	 }
	            	 if (choiceAsInt < 1 | choiceAsInt > 3)
	            		 continue;
	            	 if (choiceAsInt == 1)
	            	 {
	            		 //logining in as existing user
	            		 System.out.println("username: ");
	            		 while ((username = in.readLine()) == null && username.length() == 0);
	            		 System.out.println("password: ");
	            		 while ((password = in.readLine()) == null && password.length() == 0);
	            		 if (controller.loginUser(username, password, con)) {
	            			 System.out.println("succesfull login");
	            			 userMenu(controller, con, controller.currentUser);
	            			 break;
	            		 }
	            		 else {
	            			 System.out.println("couldn not login");
	            		 }
	            		 
	            		 
	            	 }
	            	 else if (choiceAsInt == 2)
	            	 {	 
	            		 //registering new user
	            		 System.out.println("please enter your user info below: ");
	            		 System.out.println("username: ");
	            		 while ((username = in.readLine()) == null && username.length() == 0)
	            			 System.out.println(username);
	            		 System.out.println("password: ");
	            		 while ((password = in.readLine()) == null && password.length() == 0)
	            			 System.out.println(password);
	            		 System.out.println("name: ");
	            		 while ((name = in.readLine()) == null && name.length() == 0)
	            			 System.out.println(name);
	            		 System.out.println("The next 3 fields are for your home address");
	            		 System.out.println("Street (Ex: 123 Fake St. Apt #6): ");
	            		 while ((street = in.readLine()) == null && street.length() == 0)
	            			 System.out.println(street);
	            		 System.out.println("City (Ex: Houston): ");
	            		 while ((city = in.readLine()) == null && city.length() == 0)
	            			 System.out.println(city);
	            		 System.out.println("State (Ex): ");
	            		 while ((state = in.readLine()) == null && state.length() == 0)
	            			 System.out.println(state);
	            		 System.out.println("phone #: ");
	            		 while ((phone = in.readLine()) == null && phone.length() == 0)
	            			 System.out.println(phone);
	            		 System.out.println("Are you a driver? (yes/no): ");
	            		 while ((isDriver = in.readLine()) == null && isDriver.length() == 0)
	            			 System.out.println(isDriver);
	            		 if(isDriver != null)
	            		 {
	            			address = street + "-" + city + "-" + state;
	            			if(controller.setNewUser(username, password, name, address, phone, isDriver, con))
	            			{
	            				System.out.println("New user successfully registered");
	            				userMenu(controller, con, controller.currentUser);
	            				break;
	            			}
	            			else
	            			{
	            				System.out.println("Error creating new user");
	            			}
	            		 }
	            		 
	            	 }
	            	 else
	            	 {   
	            		 System.out.println("EoM");
	            		 con.closeConnection();
	            		 break;
	            	 }
	             }
		 }
         catch (Exception e)
         {
        	 e.printStackTrace();
        	 System.err.println ("Either connection error or query execution error!");
         }
         finally
         {
        	 if (con != null)
        	 {
        		 try
        		 {
        			 con.closeConnection();
        			 System.out.println ("Database connection terminated");
        		 }
        	 
        		 catch (Exception e) { /* ignore close errors */ }
        	 }	 
         }
	}
	public static void userMenu(UberController controller, Connector2 con, User user) throws IOException
	{
		String choice;
        int choiceAsInt = 0;
        String vin = "";
        String category = "";
        String model = "";
        String make = "";
        String year = "";
        String owner = "";
        String fav = "";
        String trustedUser = "";
        String isTrusted = "";
        String cost = "";
        String date = "";
        String feedbackText = "";
        String feedbackScore = "";

         System.out.println ("Connection established");
     
         BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
         
         while(true)
         {
        	 displayUserMenu(user.get_username(), user.get_isDriver());
        	 while ((choice = in.readLine()) == null && choice.length() == 0);
        	 try{
        		 choiceAsInt = Integer.parseInt(choice);
        	 }catch (Exception e)
        	 {
        		 
        		 continue;
        	 }
        	 if (choiceAsInt < 1 | choiceAsInt > 12)
        		 continue;
        	 if (choiceAsInt == 2) {
        		 // driver is declaring a car as their favorite
        		 System.out.println("Please enter the info below: ");
        		 System.out.println("Vin # of car you want to declare as favorite: ");
        		 while ((fav = in.readLine()) == null && fav.length() == 0)
        			 System.out.println(fav);
        		 
        		 if(controller.declareFavCar(Integer.parseInt(fav), user.get_username(), con))
        			 System.out.println("Successfully declared " + fav + " as favorite car.");
        		 else
        			 System.out.println("Failed to set as favorite.");
        	 }
        	 else if (choiceAsInt == 3) {
        		 // user is dclaring if another user is trusted or not
        		 System.out.println("Enter the login/username of the person you wish to denote: ");
        		 while ((trustedUser = in.readLine()) == null && trustedUser.length() == 0)
        			 System.out.println(trustedUser);
        		 System.out.println("Do you trust them? (yes/no) ");
        		 while ((isTrusted = in.readLine()) == null && isTrusted.length() == 0)
        			 System.out.println(isTrusted);
        		 if(controller.declareTrusted(trustedUser, isTrusted, con))
        			 System.out.println("Successfully trusted " + trustedUser + " as a trustworthy fellow.");
        		 else
        			 System.out.println("Failed to set as trusted.");
        	 }
        	 else if(choiceAsInt == 4)
        	 {
        		 //user is reserving a car
        		 reserveCar(user,controller,con,in);
        	 }
        	 else if(choiceAsInt == 5)
        	 {
        		 //user is giving car feedback
        		 System.out.println("Enter in following feedback info: ");
        		 System.out.println("Vin # of car you wish to give feedback: ");
        		 while ((vin = in.readLine()) == null && vin.length() == 0)
        			 System.out.println(vin);
        		 System.out.println("Feedback Score (0-10, 0 = Awful, 10 = Amazing!: ");
        		 while ((feedbackScore = in.readLine()) == null && feedbackScore.length() == 0)
        			 System.out.println(feedbackScore);
        		 System.out.println("Feedback comment [Optional] Limit to 200 characters: ");
        		 while ((feedbackText = in.readLine()) == null && feedbackText.length() == 0)
        			 System.out.println(feedbackText);
        		 
        		 int score = Integer.parseInt(feedbackScore);
        		 if(score < 0 || score > 10)
        		 {
        			 System.out.println("Score was invalid entry try again: ");
        			 continue;
        		 }
        		 else if(feedbackText.length() > 199)
        		 {
        			 System.out.println("Feedback comment too large try again: ");
        			 continue;
        		 }
        		 else
        		 {
        			 if(controller.giveFeedback(vin, score, feedbackText, con))
        			 {
        				 System.out.println("Succefully gave feedback (cmdline): ");
        			 }
        			 else
        			 {
        				 System.out.println("Couldnt give feedback (cmdline): ");
        			 }
        		 }
        	 }
        	 else if(choiceAsInt == 6)
        	 {
        		 //user is marking reservation as ride
        		 selectPastReservations(controller,controller.getPastReservations(con), con);
        		 
        	 }
        	 else if (choiceAsInt == 7)
        	 {
        		 //user is viewing feedback
        		 System.out.println("Vin # of car you wish to view feedback: ");
        		 while ((vin = in.readLine()) == null && vin.length() == 0)
        			 System.out.println(vin);
        		 viewOrSelectFeedback(controller, controller.getFeedbackList(vin, con), con, vin);
        	 }
        	 else if (choiceAsInt == 8)
        	 {
        		 //user is viewing most useful feedback
        		 usefulFeedbackChoices(controller, controller.getAllDrivers(con), con);
        		 
        	 }
        	 else if (choiceAsInt == 9)
        	 {
        		 //user is car browsing
        		 String searchMake = null;
        		 String searchAddress = null;
        		 String searchCategory = null;
        		 conductCarSearch(controller,con,searchMake, searchAddress, searchCategory);
        	 }
        	 else if (choiceAsInt == 10)
        	 {
        		 //driver is adding new car
        		 System.out.println("please enter the info of the new car: ");
        		 System.out.println("Vin #: ");
        		 while ((vin = in.readLine()) == null && vin.length() == 0)
        			 System.out.println(vin);
        		 System.out.println("Category (Must be exactly(case sensitive): Standard, Comfort or Luxury): ");
        		 while ((category = in.readLine()) == null && category.length() == 0)
        			 System.out.println(category);
        		 if(category.equals("Luxury") || category.equals("Standard") || category.equals("Comfort"))
        		 {
        			 
        		 }
        		 else
        		 {
        			 System.out.println("Incorrect category entered, try again");
        			 continue;
        		 }
        		 System.out.println("Make: ");
        		 while ((make = in.readLine()) == null && make.length() == 0)
        			 System.out.println(make);
        		 System.out.println("Model: ");
        		 while ((model = in.readLine()) == null && model.length() == 0)
        			 System.out.println(model);
        		 System.out.println("Year: ");
        		 while ((year = in.readLine()) == null && year.length() == 0)
        			 System.out.println(year);
        		 if(user != null && year != null)
        		 {
        			if(controller.addNewCar(Integer.parseInt(vin), category, model, make, Integer.parseInt(year), user.get_username(), con))
        			{
        				System.out.println("New car added!");
        				//break;
        				
        			}
        			else
        			{
        				System.out.println("Error adding new car");
        			}
        		 }
        		 
        		 
        	 }
        	 else if (choiceAsInt == 11)
        	 {	 
        		 //driver is editing an existing car
        		 System.out.println("Vin # of Car you wish to edit: ");
        		 while ((vin = in.readLine()) == null && vin.length() == 0)
        			 System.out.println(vin);
        		 System.out.println("New Category: ");
        		 while ((category = in.readLine()) == null && category.length() == 0)
        			 System.out.println(category);
        		 System.out.println("New Make: ");
        		 while ((make = in.readLine()) == null && make.length() == 0)
        			 System.out.println(make);
        		 System.out.println("New Model: ");
        		 while ((model = in.readLine()) == null && model.length() == 0)
        			 System.out.println(model);
        		 System.out.println("New Year: ");
        		 while ((year = in.readLine()) == null && year.length() == 0)
        			 System.out.println(year);
        		 if(user != null && year != null)
        		 {
        			if(controller.editCar(Integer.parseInt(vin), category, model, make, Integer.parseInt(year), user.get_username(), con))
        			{
        				System.out.println("Successully edit car with vin: " + vin);
        				//break;
        			}
        			else
        			{
        				System.out.println("Error editting car");
        			}
        		 }

        	 }
        	 else if (choiceAsInt == 12)
        	 {	 
        		 //driver is choosing availability times
        		 selectAvailability(user,controller, con, controller.driverViewPeriods(con), false, null);
        	 }
        	 else
        	 {   
        		 System.out.println("EoM");
        		 return;
        	 }
         }
	}
	private static void reserveCar(User user, UberController controller, Connector2 con, BufferedReader in) throws NumberFormatException, IOException
	{
		String vin = "";
        String cost = "";
        String date = "";
		System.out.println("Please enter reservation information: ");
		 System.out.println("Vin # for car you wish to reserve: ");
		 try {
		 while ((vin = in.readLine()) == null && vin.length() == 0)
			 System.out.println(vin);
		 System.out.println("Date of reservation (Required Format: 'DD-MM-YYYY'): ");
		 while ((date = in.readLine()) == null && date.length() == 0)
			 System.out.println(date);
		 System.out.println("Cost: ");
		 while ((cost = in.readLine()) == null && cost.length() == 0)
			 System.out.println(cost);
		 }
		 catch(Exception e)
		 {
			 e.getMessage();
		 }
		 if(user != null && cost != null)
		 {
			 selectAvailability(user,controller, con, controller.getAvailableReservationTimes(vin, con), true, 
					 new Reservation(vin, "-1", Integer.parseInt(cost), stringToDate(date)));
		 }
	}
	private static void selectAvailability(User user, UberController controller, Connector2 con, ArrayList<String> list, boolean isReserving, Reservation reservation) throws IOException
	{
		String choice;
        int choiceAsInt = 0;
			//remember to replace the password
	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        if(list.size() <= 0)
        {
        	if(isReserving)
        	{
        		System.out.println("No (more) available times for that car");
        		reserveCar(user, controller, con, in);
        		return;
        	}
        	else
        	{
        		System.out.println("All times already selected");
        		return;
        	}
        }
	
	     
	     while(true)
	     {
			 displayAvailabilityOptions(list);
	    	 while ((choice = in.readLine()) == null && choice.length() == 0);
	    	 try{
	    		 choiceAsInt = Integer.parseInt(choice);
	    	 }catch (Exception e)
	    	 {
	    		 
	    		 continue;
	    	 }
	    	 ArrayList<Integer> listOfPID = new ArrayList<Integer>();
	    	 for(int i = 0; i < list.size(); i++)
	    	 {
	    		 listOfPID.add(Integer.parseInt(list.get(i).split(" ")[0]));
	    	 }
	    	 if (!listOfPID.contains(choiceAsInt))
	    		 continue;
	    	 else if (listOfPID.contains(choiceAsInt))
	    	 {
	    		 for(int i = 0; i <= listOfPID.get(listOfPID.size() - 1); i++)
	    		 {
	    			 if(choiceAsInt == i)
	    			 {
	    				 if(!isReserving)
	    				 {
	    					 //here mean a driver is selecting their available times
	        				 if(controller.driverSetAvailability(list.get(i).toString(), con)) {
	            				 System.out.println("Driver availiablity successfully added from cmdline");
	            				 break;
	        				 }
	        				 else
	        				 {
	        					 System.out.println("Unable to set time due to error or you already have this availability selected \nTry Again!");
	        					 break;
	        				 }
	    				 }
	    				 else
	    				 {
	    					 //here means a user/driver is reserving a ride from available times
	    					 reservation.set_pid(i + "");
	    					 controller.getReservations().add(reservation);
	    					 displayResConfirmationInfo(reservation.get_vin(), reservation.get_cost(), reservation.get_Date().toString());
	    					 reservationStatusChoices(con, controller, user);
	    					 break;
	    				 }
	
	    			 }
	    		 }
	    		 break;
	    	 }
	    	 else
	    	 {   
	    		 System.out.println("EoM");
	    		 return;
	    	 }
	     }
	}
	private static void reservationStatusChoices(Connector2 con, UberController controller, User user) throws IOException
	{
		String choice;
        int choiceAsInt = 0;
	 
	     BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	     
	     while(true)
	     {
			 displayReservationStatusChoices();
	    	 while ((choice = in.readLine()) == null && choice.length() == 0);
	    	 try{
	    		 choiceAsInt = Integer.parseInt(choice);
	    	 }catch (Exception e)
	    	 {
	    		 continue;
	    	 }
	    	 if (choiceAsInt < 0 | choiceAsInt > 3)
	    		 continue;
	    	 else if (choiceAsInt == 1)
	    	 {
	    		 //confirm all resverations
	    		 controller.setReservations(con);
	    		 controller.getReservations().clear();
	    		 break;
	    		 
	    	 }
	    	 else if (choiceAsInt == 2)
	    	 {
	    		 //make another reservation
	    		 reserveCar(user, controller, con, in);
	    		 break;
	    		 
	    	 }
	    	 else if (choiceAsInt == 3)
	    	 {
	    		 //cancel all reservations
	    		 controller.getReservations().clear();
	    		 break;
	    	 }
	    	 else
	    	 {   
	    		 System.out.println("EoM");
	    		 return;
	    	 }
	     }

	}
	private static void selectPastReservations(UberController controller, ArrayList<Reservation> list, Connector2 con) throws IOException
	{
		String choice;
        int choiceAsInt = 0;
        if(list.size() <= 0)
        {
        	System.out.println("No Past Reservations to display");
        	return;
        }
	 
	     BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	     
	     while(true)
	     {
	    	 displayPastReservations(list);
	    	 while ((choice = in.readLine()) == null && choice.length() == 0);
	    	 try{
	    		 choiceAsInt = Integer.parseInt(choice);
	    	 }catch (Exception e)
	    	 {
	    		 
	    		 continue;
	    	 }
	    	 if(choiceAsInt < 0 || choiceAsInt >= list.size())
	    		 continue;
	    	 else if (choiceAsInt < list.size())
	    	 {
	    		 for(int i = 0; i < list.size(); i++)
	    		 {
	    			 if(choiceAsInt == i)
	    			 {
	    				 Ride ride = new Ride(list.get(i).get_vin(), list.get(i).get_pid(), list.get(i).get_cost(), list.get(i).get_Date());
	    				 list.remove(list.get(i));
	    				 controller.getRides().add(ride);
	    				 displayRideConfirmation(ride);
	    				 rideStatusChoices(controller, con, list);
	    				 break;
	    				 
	    			 }
	    		 }
	    		 break;
	    	 }
	     }
	}
	private static void rideStatusChoices(UberController controller, Connector2 con, ArrayList<Reservation> list) throws IOException
	{
		String choice;
        int choiceAsInt = 0;
	 
	     BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	     
	     while(true)
	     {
			 displayRideStatusChoices();
	    	 while ((choice = in.readLine()) == null && choice.length() == 0);
	    	 try{
	    		 choiceAsInt = Integer.parseInt(choice);
	    	 }catch (Exception e)
	    	 {
	    		 continue;
	    	 }
	    	 if (choiceAsInt < 0 | choiceAsInt > 3)
	    		 continue;
	    	 else if (choiceAsInt == 1)
	    	 {
	    		 //confirm all rides
	    		 controller.setRides(con);
	    		 controller.getRides().clear();
	    		 break;
	    		 
	    	 }
	    	 else if (choiceAsInt == 2)
	    	 {
	    		 //make another ride
	    		 if(list.size() <= 0 )
	    		 {
	    			 System.out.println("No other reservations to select, choose another option");
	    			 rideStatusChoices(controller, con, list);
	    		 }
	    		 else
	    		 {
		    		 selectPastReservations(controller, list, con);
		    		 break;
	    		 }

	    	 }
	    	 else if (choiceAsInt == 3)
	    	 {
	    		 //cancel all rides
	    		 controller.getRides().clear();
	    		 break;
	    	 }
	    	 else
	    	 {   
	    		 System.out.println("EoM");
	    		 return;
	    	 }
	     }

	}
	private static void viewOrSelectFeedback(UberController controller, ArrayList<Feedback> list, Connector2 con, String vin) throws IOException
	{
		String choice;
        int choiceAsInt = 0;
	 
	     BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	     
	     while(true)
	     {
			 displayFeedback(list, vin);
	    	 while ((choice = in.readLine()) == null && choice.length() == 0);
	    	 try{
	    		 choiceAsInt = Integer.parseInt(choice);
	    	 }catch (Exception e)
	    	 {
	    		 continue;
	    	 }
	    	 if(choiceAsInt < 0 || choiceAsInt >= list.size())
	    		 continue;
	    	 else if (choiceAsInt < list.size())
	    	 {
	    		 for(int i = 0; i < list.size(); i++)
	    		 {
	    			 if(choiceAsInt == i)
	    			 {
	    				 String rating = "";
	    				 Feedback fb = new Feedback(list.get(i).get_user(), list.get(i).get_fid(), list.get(i).get_score(), list.get(i).get_text(), list.get(i).get_vin(), list.get(i).get_date());
	    				 System.out.println("Your rating (0-2 only, 0 = useless, 1 = usefull, 2 = very usefull): ");
	            		 while ((rating = in.readLine()) == null && rating.length() == 0)
	            			 System.out.println(rating);
	            		 int ratingAsInt = Integer.parseInt(rating);
	            		 if(ratingAsInt >= 0 || ratingAsInt <= 2)
	            		 {
	            			 
	            			 controller.setFeedbackRating(list.get(i).get_fid(), ratingAsInt, con);
	            		 }
	            		 else
	            		 {
	            			 System.out.println("Invalid rating, it must be 0 1 or 2");
	            			 viewOrSelectFeedback(controller, list, con, vin);
	            		 }
	    				 break;
	    				 
	    			 }
	    		 }
	    		 break;
	    	 }
	     }
	}
	private static void usefulFeedbackChoices(UberController controller, ArrayList<User> list, Connector2 con) throws IOException
	{
		String choice;
        int choiceAsInt = 0;
	 
	     BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	     
	     while(true)
	     {
			 displayDrivers(list);
	    	 while ((choice = in.readLine()) == null && choice.length() == 0);
	    	 try{
	    		 choiceAsInt = Integer.parseInt(choice);
	    	 }catch (Exception e)
	    	 {
	    		 continue;
	    	 }
	    	 if(choiceAsInt < 0 || choiceAsInt >= list.size())
	    		 continue;
	    	 else if (choiceAsInt < list.size())
	    	 {
	    		 for(int i = 0; i < list.size(); i++)
	    		 {
	    			 if(choiceAsInt == i)
	    			 {
	    				 String numEntries = "";
	    				 System.out.println("How many feedback entries do you wish to view?");
	            		 while ((numEntries = in.readLine()) == null && numEntries.length() == 0)
	            			 System.out.println(numEntries);
	            		 int n = Integer.parseInt(numEntries);
	    				 ArrayList<Feedback> feedback = controller.getFeedbackOnDriver(list.get(i), n,con);
	    				 displayDriverFeedback(feedback, n, list.get(i));
	    			 }
	    		 }
	    		 break;
	    	 }
	     }
	}
	private static void conductCarSearch(UberController controller, Connector2 con, String make, String address, String category) throws IOException
	{
		String choice;
        int choiceAsInt = 0;
	 
	     BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	     
	     while(true)
	     {
			 displaySearchFilters(make, address, category);
	    	 while ((choice = in.readLine()) == null && choice.length() == 0);
	    	 try{
	    		 choiceAsInt = Integer.parseInt(choice);
	    	 }catch (Exception e)
	    	 {
	    		 continue;
	    	 }
	    	 if(choiceAsInt < 1 || choiceAsInt > 4)
	    	 {
	    		 continue;
	    	 }
	    	 else if(choiceAsInt == 1) {
	    		 //user is searching by category
	    		 if(category == null)
	    		 {
	    			 searchByCategory(controller, con, make, address, category);
		    		 break;
	    		 }
	    		 else
	    		 {
	    			 System.out.println("Already filterd by category, select another filter:");
					 continue;
	    		 }
	    	 }
			 else if(choiceAsInt == 2) {
				//user is searching by state or city
				 if(address == null)
				 {
					 System.out.println("Type in city or state (Ex: Houston, Texas, Boston:");
	        		 while ((address = in.readLine()) == null && address.length() == 0)
	        			 System.out.println(address);
	        		 searchStatusChoice(controller, con, make, address, category);
	        		 break;
				 }
				 else
				 {
					 System.out.println("Already filterd by city or state, select another filter:");
					 continue;
				 }
			 }
			 else if(choiceAsInt == 3) {
			 //user is searching by make
				 if(make == null)
				 {
					 System.out.println("Type the make you desire (Ex: Honda, Ford, Audi) :");
	        		 while ((make = in.readLine()) == null && make.length() == 0)
	        			 System.out.println(make);
	        		 searchStatusChoice(controller, con, make, address, category);
	        		 break;
				 }
				 else
				 {
					 System.out.println("Already filterd by make, select another filter:");
					 continue;
				 }
			 }
			 else if(choiceAsInt == 4)
			 {
				 searchStatusChoice(controller, con, make, address, category);
        		 break;
			 }
			 
	     }
	}
	private static void searchStatusChoice(UberController controller, Connector2 con, String make, String address, String category) throws IOException
	{
		String choice;
        int choiceAsInt = 0;
	 
	     BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	     
	     while(true)
	     {
	    	 displaySearchStatusResults();
	    	 while ((choice = in.readLine()) == null && choice.length() == 0);
	    	 try{
	    		 choiceAsInt = Integer.parseInt(choice);
	    	 }catch (Exception e)
	    	 {
	    		 continue;
	    	 }
	    	 if(choiceAsInt < 1 || choiceAsInt > 3)
	    	 {
	    		 continue;
	    	 }
	    	 else if(choiceAsInt == 1) {
	    		 //user wants to conduct search
	    		 if(filterByTrustedUsers())
	    		 {
	    			 displayCars(controller.getCarsBySearch(address, make, category, true, con));
	    		 }
	    		 else
	    		 {
	    			 displayCars(controller.getCarsBySearch(address, make, category, false, con));
	    		 }
	    		 break;
	    		 
	    	 }
			 else if(choiceAsInt == 2) {
				//user wants to add more filters
				 conductCarSearch(controller, con, make, address, category);
				 break;
				 
			 }
			 else if(choiceAsInt == 3) {
				 //user wants to cancel search
				 return;
			 }
	     }
	}
	private static void searchByCategory(UberController controller, Connector2 con, String make, String address, String category) throws IOException
	{
		String choice;
        int choiceAsInt = 0;
	 
	     BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	     
	     while(true)
	     {
	    	 displayCategoryOptions();
	    	 while ((choice = in.readLine()) == null && choice.length() == 0);
	    	 try{
	    		 choiceAsInt = Integer.parseInt(choice);
	    	 }catch (Exception e)
	    	 {
	    		 continue;
	    	 }
	    	 if(choiceAsInt < 1 || choiceAsInt > 3)
	    	 {
	    		 continue;
	    	 }
	    	 else if(choiceAsInt == 1) {
	    		 //user wants to conduct search by Luxury
	    		 category = "Luxury";
	    		 searchStatusChoice(controller, con, make, address, category);
	    		 break;
	    		 
	    	 }
			 else if(choiceAsInt == 2) {
				//user wants to conduct search by Comfort
				 category = "Comfort";
				 searchStatusChoice(controller, con, make, address, category);
				 break;
			 }
			 else if(choiceAsInt == 3) {
				//user wants to conduct search by Standard
				 category = "Standard";
				 searchStatusChoice(controller, con, make, address, category);
				 break;
			 }
	     }
	}
	private static boolean filterByTrustedUsers() throws IOException
	{
		String choice;
        int choiceAsInt = 0;
	 
	     BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	     
	     while(true)
	     {
	    	 displayFilterByTrusted();
	    	 while ((choice = in.readLine()) == null && choice.length() == 0);
	    	 try{
	    		 choiceAsInt = Integer.parseInt(choice);
	    	 }catch (Exception e)
	    	 {
	    		 continue;
	    	 }
	    	 if(choiceAsInt < 1 || choiceAsInt > 2)
	    	 {
	    		 continue;
	    		 
	    	 }
	    	 else if(choiceAsInt == 1) {
	    		 //user wants to conduct search by trusted reviews
	    		 return true;
	    	 }
	    	 else
	    	 {
	    		 return false;
	    	 }
	     }
	}
	private static java.sql.Date stringToDate(String str)
	{
		SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date date = null;
		try {
			date = sd.parse(str);
		} catch (ParseException e) {
			System.out.println("Invalid date format");
			e.printStackTrace();
		}
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());  
		return sqlDate;
	}
}
