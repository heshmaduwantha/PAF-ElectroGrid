package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.google.gson.Gson;

import models.Help;

public class Queries {
	
	Connection con = null;
	String output = "";
	
	public Queries() {
		
		String url = "jdbc:mysql://localhost:3306/pafassignment";
		//Provide the correct details: DBServer/DBName, username, password
		String username = "root";
		String password = "";
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url,username,password);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
		}
		
		if(con != null) {
			System.out.println("Database Connected successfully");
		} else {
			System.out.println("Database Connection failed");
		}
		
		
	}

	public String getAll() {
			
			output = "<table border='1'><tr>"+
			"<th>Id</th>"+
			"<th>Client Name</th>" +
			"<th>Nic</th>" +
			"<th>Account Number</th>" +
			"<th>Address</th>" +
			"<th>Email</th>" +
			"<th>Contact Number</th>" +
			"<th>Type</th>" +
			"<th>Message</th>" +
			"<th>Status</th></tr>";
			
			String sql = "select * from help";
		
			try {
				
				Statement st = this.con.createStatement();
				ResultSet rs = st.executeQuery(sql);
				
				while(rs.next()) {
						
					output += "<tr><td>" + rs.getInt(1) + "</td>";
					output += "<td>" + rs.getString(2) + "</td>";
					output += "<td>" + rs.getString(3) + "</td>";
					output += "<td>" + rs.getInt(4) + "</td>";
					output += "<td>" + rs.getString(5)+"</td>";
					output += "<td>" + rs.getString(6) + "</td>";
					output += "<td>" + rs.getInt(7) + "</td>";
					output += "<td>" + rs.getString(8) + "</td>";
					output += "<td>" + rs.getString(9) + "</td>";
					output += "<td>" + rs.getString(10) + "</td></tr>";

				}
				
				output += "</tr></table>";
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return output;
		}
	
	public String getOne(int id) {
		
		String sql = "select * from help where id="+id;
		Help req = new Help();
		Gson gson = new Gson();
		try {
			
			Statement st = this.con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			if(rs.next()) {
				
				req.setId(rs.getInt(1));
				req.setClientName(rs.getString(2));
				req.setNic(rs.getString(3));
				req.setAcNumber(rs.getInt(4));
				req.setAddress(rs.getString(5));
				req.setEmail(rs.getString(6));
				req.setContact(rs.getInt(7));
				req.setMessage(rs.getString(8));
				req.setType(rs.getString(9));
				req.setStatus(rs.getString(10));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return gson.toJson(req);
	}
	
	public String Insert(Help request) {
		
		String sql = "insert into help values(?,?,?,?,?,?,?,?,?,?)";
		
			try {
			
				PreparedStatement st = this.con.prepareStatement(sql);
				
				st.setInt(1,request.getId());
				st.setString(2,request.getClientName());
				st.setString(3,request.getNic());
				st.setInt(4,request.getAcNumber());
				st.setString(5,request.getAddress());
				st.setString(6,request.getEmail());
				st.setInt(7,request.getContact());
				st.setString(8,request.getMessage());
				st.setString(9,request.getType());
				st.setString(10,request.getStatus());
				
				st.executeUpdate();
				output = "Inserted successfully";
			
			} catch (Exception e) {
				
				System.err.println(e.getMessage());
				output = "Error while inserting the item.";
				
			}
			
			return output;
		
	}

	public String Update(
			Integer id,
			String clientName,
			String nic,
			Integer acNumber,
			String address,
			String email,
			Integer contact,
			String message,
			String type,
			String status
			) {
		
		
		String sql = "UPDATE help SET clientName=?,nic=?,acNumber=?,address=?,email=?,contact=?,message=?,type=?,status=?"
				+ "WHERE id=?";
		
			try {
			
				PreparedStatement st = this.con.prepareStatement(sql);
				
				st.setString(1,clientName);
				st.setString(2,nic);
				st.setInt(3,acNumber);
				st.setString(4,address);
				st.setString(5,email);
				st.setInt(6,contact);
				st.setString(7,message);
				st.setString(8,type);
				st.setString(9,status);
				st.setInt(10,id);
				
				st.executeUpdate();
				
				output = "Update successfully";
			
			} catch (Exception e) {
				
				System.err.println(e.getMessage());
				output = "Error while updating the item.";
				
			}
			
			return output;
		
	}

	public String delete(Integer id){
		
		String sql = "delete from help where id=?";
		
		try {
		
			PreparedStatement st = this.con.prepareStatement(sql);
			
			st.setInt(1,id);
			st.executeUpdate();
			
			output = "Delete successfully";
		
		} catch (Exception e) {
			
			System.err.println(e.getMessage());
			
			output = "Error while deleting the record.";
			
		}
		
		return output;
	
	}

}
