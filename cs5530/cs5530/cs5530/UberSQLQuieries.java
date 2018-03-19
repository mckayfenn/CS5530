package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;

public class UberSQLQuieries {
	
	public UberSQLQuieries() {
		
	}
	
	public User createUser(String login, String password, String name, String address, String phone, boolean isDriver, Statement stmt) {
		String sql = "insert into user (login, password, name, address, phone) values " + "(" + login + ", " + password + ", " + name + ", " + address + ", " + phone + ");";
		String sqldriver = "";
		if (isDriver) {
			sqldriver = "insert into driver (login) values " + "(" + login +  ");";
		}
		
		String outputs = "";
		User output = null;
		ResultSet rs = null;
		ResultSet rsd = null;
	 	System.out.println("executing "+sql);
	 	try {
			rs= stmt.executeQuery(sql);
			
			if (isDriver) {
				rsd = stmt.executeQuery(sqldriver);
			}
			
			while (rs.next()) {
				//System.out.print("cname:");
				outputs += rs.getString("login")+"   "+rs.getString("name")+"\n";
				output = new User(rs.getString("login"), rs.getString("password"));
			}
			while (rsd.next()) {
				output.set_isDriver(isDriver);
			}
			 
			rs.close();
			rsd.close();
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("cannot execute the query");
	 	}
	 	finally
	 	{
	 		try{
	 		if (rs!=null && !rs.isClosed())
	 			rs.close();
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
	 	
	    return output;
	}

}
