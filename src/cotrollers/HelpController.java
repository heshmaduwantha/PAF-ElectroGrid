package cotrollers;

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

import models.Help;
import utils.Queries;
import utils.RandomID;

@Path("/HelpRequest")
public class HelpController {

	Queries query = new Queries();
	
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String getAllHelps()
	{
		//List<ConRequest> requests = new ArrayList<>();
		String requests =  this.query.getAll();
		return requests;
		
	}
	
	@GET
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String singleHelp(@PathParam("id")int id)
	{
		
		return this.query.getOne(id);
	
	}
	
	@POST
	@Path("/Add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)	
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addHelp(
			@FormParam("clientName") String clientName,
			@FormParam("nic") String nic,
			@FormParam("acNumber") Integer acNumber,
			@FormParam("address") String address,
			@FormParam("email") String email,
			@FormParam("contact") Integer contact,
			@FormParam("message") String message,
			@FormParam("type") String type,
			@FormParam("status") String status	
	)
	{
		
		Help request = new Help();
		RandomID id = new RandomID();
		
		request.setId(id.getuniqueid());
		request.setClientName(clientName);
		request.setNic(nic);
		request.setAcNumber(acNumber);
		request.setAddress(address);
		request.setEmail(email);
		request.setContact(contact);
		request.setMessage(message);
		request.setType(type);
		request.setStatus(status);
		
		String output = this.query.Insert(request);
		
		return output;
		
	}
	
	
	@PUT
	@Path("/Update")
	//@Consumes(MediaType.APPLICATION_JSON)	
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.TEXT_PLAIN)
	public String Updatehelp(String Data)
	{
		
		JsonObject Object = new JsonParser().parse(Data).getAsJsonObject();
		
		Integer id = Object.get("id").getAsInt();
		String clientName = Object.get("clientName").getAsString();
		String nic = Object.get("nic").getAsString();
		Integer acNumber = Object.get("acNumber").getAsInt();
		String address = Object.get("address").getAsString();
		String email = Object.get("email").getAsString();
		Integer contact = Object.get("contact").getAsInt();
		String message = Object.get("message").getAsString();
		String type = Object.get("type").getAsString();
		String status = Object.get("status").getAsString();

		
		String output = this.query.Update(id,clientName,nic,acNumber,address,email,contact,message,type,status);
		
		return output;
		
	}
	
	
	@DELETE
	@Path("/Delete")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteHelp(String Data)
	{

		org.jsoup.nodes.Document doc = Jsoup.parse(Data, "", org.jsoup.parser.Parser.xmlParser());
		String id = doc.select("ID").text();
		String output = query.delete(Integer.parseInt(id));
		
		return output;
	}
	
	
	
	
	
}
