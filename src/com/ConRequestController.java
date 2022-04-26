package com;



import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



import org.jsoup.Jsoup;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import model.ConRequest;
import utils.RandomID;
import utils.SqlQueries;


@Path("/ConnectionRequest")
public class ConRequestController
{
	SqlQueries query = new SqlQueries();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String getConnectionRequests()
	{
		//List<ConRequest> requests = new ArrayList<>();
		String requests =  this.query.getAll();
		return requests;
		
	}
	
	@GET
	@Path("/{id}")
	//@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.TEXT_HTML)
	public String singleHelp(@PathParam("id") int id)
	{
		
		return  this.query.getOne(id);
	}
	
	@POST
	@Path("/Add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)	
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addConnectionRequest(
			@FormParam("name") String name,
			@FormParam("nic") String nic,
			@FormParam("email") String email,
			@FormParam("street") String street,
			@FormParam("postalCode") Integer postalCode,
			@FormParam("city") String city,
			@FormParam("contact") Integer contact,
			@FormParam("message") String message,
			@FormParam("district") String district,
			@FormParam("contype") String contype,
			@FormParam("status") String status	
	)
	{
		
		ConRequest request = new ConRequest();
		RandomID id = new RandomID();
		
		request.setId(id.getuniqueid());
		request.setName(name);
		request.setNic(nic);
		request.setEmail(email);
		request.setStreet(street);
		request.setPostalCode(postalCode);
		request.setCity(city);
		request.setContact(contact);
		request.setMessage(message);
		request.setDistrict(district);
		request.setContype(contype);
		request.setStatus(status);
		
		String output = this.query.Insert(request);
		
		return output;
		
	}
	
	@PUT
	@Path("/Update")
	//@Consumes(MediaType.APPLICATION_JSON)	
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.TEXT_PLAIN)
	public String UpdateConnectionRequest(String Data)
	{
		
		JsonObject itemObject = new JsonParser().parse(Data).getAsJsonObject();
		
		Integer id = itemObject.get("id").getAsInt();
		String name = itemObject.get("name").getAsString();
		String nic = itemObject.get("nic").getAsString();
		String email = itemObject.get("email").getAsString();
		String street = itemObject.get("street").getAsString();
		Integer postalCode = itemObject.get("postalCode").getAsInt();
		String city = itemObject.get("city").getAsString();
		Integer contact = itemObject.get("contact").getAsInt();
		String message = itemObject.get("message").getAsString();
		String district = itemObject.get("district").getAsString();
		String contype = itemObject.get("contype").getAsString();
		String status = itemObject.get("status").getAsString();
		
		String output = this.query.Update(id, name, nic, email, street, postalCode, city, contact, message, district, contype, status);
		
		return output;
		
	}

	@DELETE
	@Path("/Delete")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteConnectionRequest(String Data)
	{

		org.jsoup.nodes.Document doc = Jsoup.parse(Data, "", org.jsoup.parser.Parser.xmlParser());
		String id = doc.select("ID").text();
		String output = query.delete(Integer.parseInt(id));
		
		return output;
	}



}