package cs5530;


import java.lang.*;
import java.sql.*;
import java.io.*;

public class CommandLineView {
	
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
        String sql = null;
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
	            		 System.out.println("username: ");
	            		 while ((username = in.readLine()) == null && username.length() == 0);
	            		 System.out.println("please enter a dname:");
	            		 //while ((dname = in.readLine()) == null && dname.length() == 0);
	            		 Course course=new Course();
	            		 //System.out.println(course.getCourse(cname, dname, con.stmt));
	            	 }
	            	 else if (choiceAsInt == 2)
	            	 {	 
	            		 System.out.println("please enter your query below:");
	            		 while ((sql = in.readLine()) == null && sql.length() == 0)
	            			 System.out.println(sql);
	            		 ResultSet rs=con.stmt.executeQuery(sql);
	            		 ResultSetMetaData rsmd = rs.getMetaData();
	            		 int numCols = rsmd.getColumnCount();
	            		 while (rs.next())
	            		 {
	            			 //System.out.print("cname:");
	            			 for (int i=1; i<=numCols;i++)
	            				 System.out.print(rs.getString(i)+"  ");
	            			 System.out.println("");
	            		 }
	            		 System.out.println(" ");
	            		 rs.close();
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
