package model; 
import java.sql.*; 
public class Item 
{ //A common method to connect to the DB
private Connection connect() 
 { 
 Connection con = null; 
 try
 { 
 Class.forName("com.mysql.jdbc.Driver"); 
 
 //Provide the correct details: DBServer/DBName, username, password 
 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/usermanagement", "root", "1998"); 
 } 
 catch (Exception e) 
 {e.printStackTrace();} 
 return con; 
 } 
public String insertUser(String itemname, String itemqty, String itemcode, String descripation) 
 { 
 String output = ""; 
 try
 { 
 Connection con = connect(); 
 if (con == null) 
 {return "Error while connecting to the database for inserting."; } 
 // create a prepared statement
 String query = " insert into inventory (`itemID`,`itemName`,`itemQty`,`itemCode`,`descripation`)"
 + " values (?, ?, ?, ?, ?)"; 
 PreparedStatement preparedStmt = con.prepareStatement(query); 
 // binding values
 preparedStmt.setInt(1, 0); 
 preparedStmt.setString(2, itemname); 
 preparedStmt.setString(3,itemqty ); 
 preparedStmt.setString(4, itemcode); 
 preparedStmt.setString(5, descripation); 
 // execute the statement
 
 preparedStmt.execute(); 
 con.close(); 
 output = "Inserted successfully"; 
 } 
 catch (Exception e) 
 { 
 output = "Error while inserting the item."; 
 System.err.println(e.getMessage()); 
 } 
 return output; 
 } 


public String readUsers() 
 { 
 String output = ""; 
 try
 { 
 Connection con = connect(); 
 if (con == null) 
 {return "Error while connecting to the database for reading."; } 
 // Prepare the html table to be displayed
 output = "<table border='1'><tr><th>Item Name</th><th>Item Qty</th>" +
 "<th>Item Code</th>" + 
 "<th>Descripation</th>" +
 "<th>Update</th><th>Remove</th></tr>"; 
 
 String query = "select * from inventory"; 
 Statement stmt = con.createStatement(); 
 ResultSet rs = stmt.executeQuery(query); 
 // iterate through the rows in the result set
 while (rs.next()) 
 { 
 String itemID = Integer.toString(rs.getInt("itemID")); 
 String itemName = rs.getString("itemName"); 
 String itemQty = rs.getString("itemQty"); 
 String itemCode = rs.getString("itemCode"); 
 String descripation = rs.getString("descripation"); 
 // Add into the html table
 output += "<tr><td>" + itemName + "</td>"; 
 output += "<td>" + itemQty + "</td>"; 
 output += "<td>" + itemCode + "</td>"; 
 output += "<td>" + descripation + "</td>"; 
 // buttons
 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
 + "<td><form method='post' action='Users.jsp'>"
 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
 + "<input name='itemID' type='hidden' value='" + itemID
 + "'>" + "</form></td></tr>"; 
 } 
 con.close(); 
 // Complete the html table
 output += "</table>"; 
 } 
 catch (Exception e) 
 { 
 output = "Error while reading the users."; 
 System.err.println(e.getMessage()); 
 } 
 return output; 
 } 


public String updateUser(String itemID, String itemname, String itemqty, String itemcode, String descripation) 
 
 { 
 String output = ""; 
 try
 { 
 Connection con = connect(); 
 if (con == null) 
 {return "Error while connecting to the database for updating."; } 
 // create a prepared statement
 String query = "UPDATE inventory SET itemName=?,itemQty=?,itemCode=?,descripation=? WHERE itemID=?"; 
 PreparedStatement preparedStmt = con.prepareStatement(query); 
 // binding values
 preparedStmt.setString(1, itemname); 
 preparedStmt.setString(2, itemqty); 
 preparedStmt.setString(3, itemcode); 
 preparedStmt.setString(4, descripation); 
 preparedStmt.setInt(5, Integer.parseInt(itemID)); 
 // execute the statement
 preparedStmt.execute(); 
 con.close(); 
 output = "Updated successfully"; 
 } 
 catch (Exception e) 
 { 
 output = "Error while updating the item."; 
 System.err.println(e.getMessage()); 
 } 
 return output; 
 } 

public String deleteUser(String itemID) 
 { 
 String output = ""; 
 try
 { 
 Connection con = connect(); 
 if (con == null) 
 {return "Error while connecting to the database for deleting."; } 
 // create a prepared statement
 String query = "delete from inventory where itemID=?"; 
 PreparedStatement preparedStmt = con.prepareStatement(query); 
 // binding values
 preparedStmt.setInt(1, Integer.parseInt(itemID)); 
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
