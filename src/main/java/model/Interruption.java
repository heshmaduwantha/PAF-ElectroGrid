package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Interruption {
	private static final String USERNAME= "root";
	private static final String PASSWORD = "";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/electro_db";
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	Statement statement = null;
	ResultSet resultSet = null;

	private String query = "";

	private Connection connect() throws SQLException {
		if(connection != null && !connection.isClosed()) {
			return connection;
		}
		else {
			try {
				Class.forName(DRIVER);
				connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
				System.out.println("Successfully Connected Electro Main Database");


			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return connection;
	}



	public String insertInterruption(String intType, String title, String description,String approval, String interruptionStartDate, String interruptionEndDate) {
		// TODO Auto-generated method stub
		String output = "";

		try {
			connection = connect();

			if (connection == null) {
				output = "Error while conecting to the database for inserting";
				return output;
			}

			query = "INSERT INTO `interruption` (`id`, `intType`, `title`, `description`, `approval`, `interruptionStartDate`, `interruptionEndDate`) VALUES (?,?,?,?,?,?,?)";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, 0);
			preparedStatement.setString(2, intType);
			preparedStatement.setString(3, title);
			preparedStatement.setString(4, description);
			preparedStatement.setString(5, approval);
			preparedStatement.setString(6, interruptionStartDate);
			preparedStatement.setString(7, interruptionEndDate);

			preparedStatement.execute();

			connection.close();

			output = "Inserted Successfully";
			query = "";

		} catch (Exception e) {
			// TODO: handle exception
			output = "Error while inserting interruption Details";
			System.err.println(e.getMessage());
		}

		return output;
	}

	//read
	
		public String readInterruption()
		  {
		     String output = "";
		     try
		    {
		      Connection con = connect();
		      if (con == null)
		    {
		        return "Error while connecting to the database for reading.";
		    }
		// Prepare the html table to be displayed
		   output = "<table border='1'><tr><th>intType</th>"
		            +"<th> title</th><th>description</th>"
		            + "<th>approval</th><th>interruptionStartDate</th>"
		            + "<th>interruptionEndDate</th>"
		            + "<th>Update</th><th>Remove</th></tr>";
		   
		    String query = "select * from interruption";
		    Statement stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		// iterate through the rows in the result set
		    
		   while (rs.next())
		   {
		     String id = Integer.toString(rs.getInt("id"));
		     String intType = rs.getString("intType");
		     String title = rs.getString("title");
		     String description = rs.getString("description");
		     String approval = rs.getString("approval");
		     String interruptionStartDate = rs.getString("interruptionStartDate");
		     String interruptionEndDate = rs.getString("interruptionEndDate");
		     
		// Add a row into the html table
		    output += "<tr><td>" + intType + "</td>";
		    output += "<td>" + title + "</td>";
		    output += "<td>" + description + "</td>";
		    output += "<td>" + approval + "</td>";
		    output += "<td>" + interruptionStartDate + "</td>";
		    output += "<td>" + interruptionEndDate + "</td>";
		// buttons
		    output += "<td><input name='btnUpdate' "
		    + " type='button' value='Update'></td>"
		    + "<td><form method='post' action='Interruption.jsp'>"
		    + "<input name='btnRemove' "
		    + " type='submit' value='Remove'>"
		    + "<input name='id' type='hidden' "
		    + " value='" + id + "'>" + "</form></td></tr>";
		    }
		    con.close();
		// Complete the html table
		    output += "</table>";
		    }
		   catch (Exception e)
		  {
		      output = "Error while reading the Interruption Details.";
		      System.err.println(e.getMessage());
		  }
		    return output;
		 }
		
		
		//update
		public String updateInterruption(String id, String IntType, String Title, String Description, String Approval,
				String InterruptionStartDate, String InterruptionEndDate) {
		    String output = "";
		try
		{
		   Connection con = connect();
		   if (con == null)
		    {  return "Error while connecting to the database for updating."; }
		   
		// create a prepared statement
		   String query = "UPDATE interruption SET intType=?,title=?,description=?,approval=?,interruptionStartDate=?,interruptionEndDate=? WHERE id=?";
		  PreparedStatement preparedStmt = con.prepareStatement(query);
		  
		// binding values
		   preparedStmt.setString(1, IntType);
		   preparedStmt.setString(2, Title);
		   preparedStmt.setString(3, Description);
		   preparedStmt.setString(4, Approval);
		   preparedStmt.setString(5, InterruptionStartDate);
		   preparedStmt.setString(6, InterruptionEndDate);
		   preparedStmt.setInt(7, Integer.parseInt(id));
		   
		// execute the statement
		   preparedStmt.execute();
		   con.close();
		   output = "Updated successfully";
		}
		catch (Exception e)
		{
		   output = "Error while updating the Interruption Details.";
		    System.err.println(e.getMessage());
		}
		   return output;
	 }

		
		//delete
		
		public String deleteInterruption(String id)
		{
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{
					return "Error while connecting to the database for deleting.";
				}
				// create a prepared statement
				String query = "delete from interruption where id=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(id));
				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Deleted successfully";
			}
			catch (Exception e)
			{
				output = "Error while deleting the item.";
				System.err.println(e.getMessage());
			}
			return output;
		}		

}
