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
	public static void displayResConfirmationInfo(String vin, int cost, String date, String times)
	{
		 System.out.println("Confirmation information before finalizing reservation");
    	 System.out.println("Car vin #: " + vin);
    	 System.out.println("Date of reservation: " + date);
    	 System.out.println("Time of reservation: " + times);
    	 System.out.println("Bidding Cost: " + cost);
	}
	public static void displayReservationStatusChoices()
	{
		 System.out.println("Make a selection on what you would like to do next: ");
    	 System.out.println("1. Confirm and finalize confirmation(s)");
    	 System.out.println("2. Make another reservation");
    	 System.out.println("3. Cancel reservation(s)");
	}
	public static void displayUserMenu(String username, boolean isDriver)
	{
		 System.out.println("Welcome: " + username + ". Please select from the following: ");
    	 System.out.println("User options: ");
    	 System.out.println("1. exit ");
    	 System.out.println("2. Declare a favorite car.");
    	 System.out.println("3. Denote user as trusted");
    	 System.out.println("4. Make Car Reservation");

		 if(isDriver) {
	    	 System.out.println("Driver options: ");
	    	 System.out.println("5. Add new car");
	    	 System.out.println("6. Edit exisiting car");
	    	 System.out.println("7. Add availability times");
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
	            		 System.out.println("address: ");
	            		 while ((address = in.readLine()) == null && address.length() == 0)
	            			 System.out.println(address);
	            		 System.out.println("phone #: ");
	            		 while ((phone = in.readLine()) == null && phone.length() == 0)
	            			 System.out.println(phone);
	            		 System.out.println("Are you a driver? (yes/no): ");
	            		 while ((isDriver = in.readLine()) == null && isDriver.length() == 0)
	            			 System.out.println(isDriver);
	            		 if(isDriver != null)
	            		 {
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
	            		 con.stmt.close(); 
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
	public static void userMenu(UberController controller, Connector2 con, User user)
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
         try
		 {
			//remember to replace the password
        	 	con.closeConnection();
			 	 con= new Connector2();
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
	            	 if (choiceAsInt < 1 | choiceAsInt > 7)
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
	            	 else if (choiceAsInt == 5)
	            	 {
	            		 //driver is adding new car
	            		 System.out.println("please enter the info of the new car: ");
	            		 System.out.println("Vin #: ");
	            		 while ((vin = in.readLine()) == null && vin.length() == 0)
	            			 System.out.println(vin);
	            		 System.out.println("Category: ");
	            		 while ((category = in.readLine()) == null && category.length() == 0)
	            			 System.out.println(category);
	            		 System.out.println("Model: ");
	            		 while ((model = in.readLine()) == null && model.length() == 0)
	            			 System.out.println(model);
	            		 System.out.println("Make: ");
	            		 while ((make = in.readLine()) == null && make.length() == 0)
	            			 System.out.println(make);
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
	            	 else if (choiceAsInt == 6)
	            	 {	 
	            		 //driver is editing an existing car
	            		 System.out.println("Vin # of Car you wish to edit: ");
	            		 while ((vin = in.readLine()) == null && vin.length() == 0)
	            			 System.out.println(vin);
	            		 System.out.println("New Category: ");
	            		 while ((category = in.readLine()) == null && category.length() == 0)
	            			 System.out.println(category);
	            		 System.out.println("New Model: ");
	            		 while ((model = in.readLine()) == null && model.length() == 0)
	            			 System.out.println(model);
	            		 System.out.println("New Make: ");
	            		 while ((make = in.readLine()) == null && make.length() == 0)
	            			 System.out.println(make);
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
	            	 else if (choiceAsInt == 7)
	            	 {	 
	            		 //driver is choosing availability times
	            		 selectAvailability(user,controller, con, controller.driverViewPeriods(con), user.get_isDriver(), null);
	            	 }
	            	 else
	            	 {   
	            		 System.out.println("EoM");
	            		 con.stmt.close(); 
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
	private static void reserveCar(User user, UberController controller, Connector2 con, BufferedReader in)
	{
		String vin = "";
        String cost = "";
        String date = "";
		System.out.println("Please enter reservation information: ");
		 System.out.println("Vin # for car you wish to reserve: ");
		 while ((vin = in.readLine()) == null && vin.length() == 0)
			 System.out.println(vin);
		 System.out.println("Date of reservation: ");
		 while ((date = in.readLine()) == null && date.length() == 0)
			 System.out.println(date);
		 System.out.println("Cost: ");
		 while ((cost = in.readLine()) == null && cost.length() == 0)
			 System.out.println(cost);
		 if(user != null && cost != null)
		 {
			 selectAvailability(user,controller, con, controller.getAvailableReservationTimes(vin, con), user.get_isDriver(), 
					 new Reservation(vin, "-1", Integer.parseInt(cost), stringToDate(date)));
		 }
	}
	private static void selectAvailability(User user, UberController controller, Connector2 con, ArrayList<String> list, boolean isDriver, Reservation reservation)
	{
		String choice;
        int choiceAsInt = 0;
         try
		 {
			//remember to replace the password
        	 	con.closeConnection();
			 	 con= new Connector2();
	             System.out.println ("Connection established");
	         
	             BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	             
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
	            	 if (choiceAsInt < 0 | choiceAsInt > list.size())
	            		 continue;
	            	 else if (choiceAsInt >= 0 | choiceAsInt < list.size())
	            	 {
	            		 for(int i = 0; i < list.size(); i++)
	            		 {
	            			 if(choiceAsInt == i)
	            			 {
	            				 if(isDriver)
	            				 {
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
	            					 reservation.set_pid(list.get(i).split(" ")[0]);
	            					 controller.getReservations().add(reservation);
	            					 displayResConfirmationInfo(reservation.get_vin(), reservation.get_cost(), reservation.get_Date().toString(), list.get(i));
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
	            		 con.stmt.close(); 
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
	private static void reservationStatusChoices(Connector2 con, UberController controller, User user)
	{
		String choice;
        int choiceAsInt = 0;
         try
		 {
			//remember to replace the password
        	 	con.closeConnection();
			 	 con= new Connector2();
	             System.out.println ("Connection established");
	         
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
	            		 con.stmt.close(); 
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
