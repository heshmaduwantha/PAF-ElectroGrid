package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


import com.google.gson.Gson;

import model.ConRequest;

public class SqlQueries {
	
	Connection con = null;
	String output = "";
	
	public SqlQueries() {
		String url = "jdbc:mysql://localhost:3306/paf";
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
		"<th>Reqest Id</th>"+
		"<th>Name</th>" +
		"<th>Nic</th>" +
		"<th>Email</th>" +
		"<th>Address</th>" +
		"<th>Contact</th>" +
		"<th>Message</th>" +
		"<th>District</th>" +
		"<th>Connectin Type</th>" +
		"<th>Status</th></tr>";
		
		String sql = "select * from connectionrequests";
		try {
			
			Statement st = this.con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				
				output += "<tr><td>" + rs.getInt(1) + "</td>";
				output += "<td>" + rs.getString(2) + "</td>";
				output += "<td>" + rs.getString(3) + "</td>";
				output += "<td>" + rs.getString(4) + "</td>";
				output += "<td>" + rs.getString(5)+","+rs.getString(7) +"," +rs.getString(6)+"</td>";
				output += "<td>" + rs.getString(8) + "</td>";
				output += "<td>" + rs.getString(9) + "</td>";
				output += "<td>" + rs.getString(10) + "</td>";
				output += "<td>" + rs.getString(11) + "</td>";
				output += "<td>" + rs.getString(12) + "</td></tr>";

			}
			
			output += "</tr></table>";
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return output;
	}
	
	public String getOne(int id) {
		
		String sql = "select * from connectionrequests where id="+id;
		ConRequest req = new ConRequest(); 
		 Gson gson = new Gson();
		try {
			
			Statement st = this.con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			if(rs.next()) {
				
				req.setId(rs.getInt(1));
				req.setName(rs.getString(2));
				req.setNic(rs.getString(3));
				req.setEmail(rs.getString(4));
				req.setStreet(rs.getString(5));
				req.setPostalCode(rs.getInt(6));
				req.setCity(rs.getString(7));
				req.setContact(rs.getInt(8));
				req.setMessage(rs.getString(9));
				req.setDistrict(rs.getString(10));
				req.setContype(rs.getString(11));
				req.setStatus(rs.getString(12));
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return gson.toJson(req);
	}

	public String Insert(ConRequest request) {
		
		String sql = "insert into connectionrequests values(?,?,?,?,?,?,?,?,?,?,?,?)";
		
			try {
			
				PreparedStatement st = this.con.prepareStatement(sql);
				
				st.setInt(1,request.getId());
				st.setString(2,request.getName());
				st.setString(3,request.getNic());
				st.setString(4,request.getEmail());
				st.setString(5,request.getStreet());
				st.setInt(6,request.getPostalCode());
				st.setString(7,request.getCity());
				st.setInt(8,request.getContact());
				st.setString(9,request.getMessage());
				st.setString(10,request.getDistrict());
				st.setString(11,request.getContype());
				st.setString(12,request.getStatus());
				
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
			String name,
			String nic,
			String email,
			String street,
			Integer postalCode,
			String city,
			Integer contact,
			String message,
			String district,
			String contype,
			String status
			) {
		
		
		String sql = "UPDATE connectionrequests SET name=?,nic=?,email=?,street=?,postalCode=?,city=?,contact=?,message=?,district=?,contype=?,status=?"
				+ "WHERE id=?";
		
			try {
			
				PreparedStatement st = this.con.prepareStatement(sql);
				
				st.setString(1,name);
				st.setString(2,nic);
				st.setString(3,email);
				st.setString(4,street);
				st.setInt(5,postalCode);
				st.setString(6,city);
				st.setInt(7,contact);
				st.setString(8,message);
				st.setString(9,district);
				st.setString(10,contype);
				st.setString(11,status);
				st.setInt(12,id);
				
				st.executeUpdate();
				
				output = "Update successfully";
			
			} catch (Exception e) {
				
				System.err.println(e.getMessage());
				output = "Error while updating the item.";
				
			}
			
			return output;
		
	}

	public String delete(Integer id){
		
		String sql = "delete from connectionrequests where id=?";
		
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
