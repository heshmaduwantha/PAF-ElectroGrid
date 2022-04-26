package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bill {
	private static final String USERNAME = "root";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/power_app";
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String PASSWORD = "";
	private static Connection connection = null;
	private static String query = "";
	private static PreparedStatement preparedStatement = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	
	private Connection connect() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			return connection;
		}
		else {
			try {
				Class.forName(DRIVER);
				connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
				System.out.println("Successfully Connected");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return connection;
		}
	}
	
	public String insertDetails(String accNo,String custName,double unitPrice,double units) {
		double amount = unitPrice * units;
		String output = "";
		
		try {
			connection = connect();
			if(connection == null) {
				output = "Error while connecting to the database for inserting";
				return null;
			}
			
			query = "INSERT INTO `bill_generate` (`invoiceID`, `accNo`, `custName`, `unitPrice`, `units`, `amount`) VALUES (NULL,?, ?, ?, ?, ?)";
			//query = "insert into power_app.bill_generate (invoiceID,accNo,custName,units,amount) values (?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, accNo);
			preparedStatement.setString(2, custName);
			preparedStatement.setDouble(3, unitPrice);
			preparedStatement.setDouble(4, units);
			preparedStatement.setDouble(5, amount);
			
			preparedStatement.execute();
			connection.close();
			
			output = "Inserted Successfully";
			query = "";
		} catch (Exception e) {
			// TODO: handle exception
			output = "Error while inserting tasks";
			System.err.println(e.getMessage());
		}
		return output;
}
	
	public String readItems()
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
	   output = "<table border='1'><tr><th>invoiceID</th>"
	            +"<th>accNo</th><th>unitPrice</th>"
	            + "<th>units</th>"
	            + "<th>card_issued</th>"
	            + "<th>amount</th>"
	            
	            + "<th>Update</th><th>Remove</th></tr>";
	   
	    String query = "select * from bill_generate";
	    Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	// iterate through the rows in the result set
	   while (rs.next())
	   {
	     String invoiceID = Integer.toString(rs.getInt("invoiceID"));
	     String accNo = rs.getString("accNo");
	     String custName = rs.getString("custName");
	     double unitPrice = rs.getDouble("unitPrice");
	     double units = rs.getDouble("units");
	     double amount = rs.getDouble("amount");
	// Add a row into the html table
	    output += "<tr><td>" + invoiceID + "</td>";
	    output += "<td>" + accNo + "</td>";
	    output += "<td>" + custName + "</td>";
	    output += "<td>" + unitPrice + "</td>";
	    output += "<td>" + units + "</td>";
	    output += "<td>" + amount + "</td>";
	// buttons
	    output += "<td><input name='btnUpdate' "
	    + " type='button' value='Update'></td>"
	    + "<td><form method='post' action='bill_generate.jsp'>"
	    + "<input name='btnRemove' "
	    + " type='submit' value='Remove'>"
	    + "<input name='userCard_ID' type='hidden' "
	    + " value='" + invoiceID + "'>" + "</form></td></tr>";
	    }
	    con.close();
	// Complete the html table
	    output += "</table>";
	  //  output = "Data reterive successfully.";
	    }
	   catch (Exception e)
	  {
	      output = "Error while reading the items.";
	      System.err.println(e.getMessage());
	  }
	    return output;
	 }
	
	
	public String insertPaymentData(String customer_name,String bill_accountNo,String date ,double pay_amount,String email,int invoiceID) {
		
		String output = "";
		
		try {
			connection = connect();
			if(connection == null) {
				output = "Error while connecting to the database for inserting";
				return null;
			}
			
			
			String qu = "select * from bill_generate where invoiceID = " +invoiceID;
			Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(qu);
		    
		    double amount = 0;
		    double rest_amount = 0;
		    
		    while(rs.next()) {
			 
			 amount = rs.getDouble("amount");
			
			 rest_amount = amount - pay_amount;
		    }
			
			
			query = "INSERT INTO `payment_table` (`payment_ID`, `customer_name`, `bill_accountNo`, `date`, `amount`, `pay_amount`,`email`,rest_amount) VALUES (NULL,?, ?, ?, ?, ?,?,?)";
			//query = "insert into power_app.bill_generate (invoiceID,accNo,custName,units,amount) values (?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, customer_name);
			preparedStatement.setString(2, bill_accountNo);
			preparedStatement.setString(3, date);
			preparedStatement.setDouble(4, amount);
			preparedStatement.setDouble(5, pay_amount);
			preparedStatement.setString(6, email);
			preparedStatement.setDouble(7, rest_amount);
			
			
			
			preparedStatement.execute();
			connection.close();
			
			output = "Inserted Successfully";
			query = "";
		} catch (Exception e) {
			// TODO: handle exception
			output = "Error while inserting tasks";
			System.err.println(e.getMessage());
		}
		return output;
}
	
	
	public String readPayment()
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
	   output = "<table border='1'><tr><th>payment_ID</th>"
	            +"<th>customer_name</th><th>bill_accountNo</th>"
	            + "<th>date</th>"
	            + "<th>amount</th>"
	            + "<th>pay_amount</th>"
	            +"<th>email</th>"
	            +"<th>rest_amount</th>"
	            
	            + "<th>Update</th><th>Remove</th></tr>";
	   
	    String query = "select * from payment_table";
	    Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	// iterate through the rows in the result set
	   while (rs.next())
	   {
	     String payment_ID = Integer.toString(rs.getInt("payment_ID"));
	     String customer_name = rs.getString("customer_name");
	     String bill_accountNo = rs.getString("bill_accountNo");
	     String date = rs.getString("date");
	     double amount = rs.getDouble("amount");
	     double pay_amount = rs.getDouble("pay_amount");
	     String email = rs.getString("email");
	     double rest_amount = rs.getDouble("rest_amount");
	     
	     
	// Add a row into the html table
	    output += "<tr><td>" + payment_ID + "</td>";
	    output += "<td>" + customer_name + "</td>";
	    output += "<td>" + bill_accountNo + "</td>";
	    output += "<td>" + date + "</td>";
	    output += "<td>" + amount + "</td>";
	    output += "<td>" + pay_amount + "</td>";
	    output += "<td>" + email + "</td>";
	    output += "<td>" + rest_amount + "</td>";
	// buttons
	    output += "<td><input name='btnUpdate' "
	    + " type='button' value='Update'></td>"
	    + "<td><form method='post' action='payment_table.jsp'>"
	    + "<input name='btnRemove' "
	    + " type='submit' value='Remove'>"
	    + "<input name='userCard_ID' type='hidden' "
	    + " value='" + payment_ID + "'>" + "</form></td></tr>";
	    }
	    con.close();
	// Complete the html table
	    output += "</table>";
	  //  output = "Data reterive successfully.";
	    }
	   catch (Exception e)
	  {
	      output = "Error while reading the items.";
	      System.err.println(e.getMessage());
	  }
	    return output;
	 }


}
