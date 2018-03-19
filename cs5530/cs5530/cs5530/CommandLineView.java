package cs5530;


import java.lang.*;
import java.sql.*;
import java.io.*;

public class CommandLineView {
	
	UberController controller = new UberController();
	
	/**
	 * @param args
	 */
	public static void displayMenu()
	{
		 System.out.println("Welcome to UUBER please login");
    	 System.out.println(" Please select one of the following options ");
    	 System.out.println("1. login existing user ");
    	 System.out.println("2. register new user");
    	 System.out.println("3. exit ");
	}
	
	public static void main(String[] args) {
		Connector2 con = null;
		String choice;
        String username;
        String password;
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
	            	 displayMenu();
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
	            		 System.out.println("address: ");
	            		 while ((address = in.readLine()) == null && address.length() == 0)
	            			 System.out.println(address);
	            		 System.out.println("phone #: ");
	            		 while ((phone = in.readLine()) == null && phone.length() == 0)
	            			 System.out.println(phone);
	            		 System.out.println("Are you a driver? (yes/no): ");
	            		 while ((isDriver = in.readLine()) == null && isDriver.length() == 0)
	            			 System.out.println(isDriver);
	            		 if(isDriver != "yes" || isDriver != "no")
	            		 {
	            			 isDriver = null;
	            			 System.out.println("Enter only yes or no: ");
	            			 System.out.println("Are you a driver? (yes/no): ");
		            		 while ((isDriver = in.readLine()) == null && isDriver.length() == 0)
		            			 System.out.println(isDriver);
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
}
