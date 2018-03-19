package cs5530;


import java.lang.*;
import java.sql.*;
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
	public static void displayUserMenu(String username, boolean isDriver)
	{
		 System.out.println("Welcome: " + username + " Please select from the following: ");
    	 System.out.println("User options: ");
    	 System.out.println("1. exit ");

		 if(isDriver) {
	    	 System.out.println("Driver options: ");
	    	 System.out.println("2. Add new car");
	    	 System.out.println("3. Edit exisiting car");
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
        String vin;
        String category;
        String model;
        String make;
        String year;
        String owner;
         try
		 {
			//remember to replace the password
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
	            	 if (choiceAsInt < 1 | choiceAsInt > 3)
	            		 continue;
	            	 if (choiceAsInt == 2)
	            	 {
	            		 //driver is adding new car
	            		 System.out.println("please enter your user info below: ");
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
	            			if(controller.setNewUser(username, password, name, address, phone, isDriver, con))
	            			{
	            				System.out.println("New user successfully registered");
	            				break;
	            			}
	            			else
	            			{
	            				System.out.println("Error creating new user");
	            			}
	            		 }
	            		 
	            		 
	            	 }
	            	 else if (choiceAsInt == 3)
	            	 {	 
	            		 //driver is editing an exisiting car
	            		 
	            		 
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
}
