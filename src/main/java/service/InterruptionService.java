package service;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Interruption;


@Path("/Interruption")
public class InterruptionService {
	
//	@GET
//	@Path("/")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String hello()
//	{
//	return "Hello world.";
//	}
	
		Interruption interruption = new Interruption();
		
		@POST
		@Path("/")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		
		public String insertInterruption(@FormParam("intType") String intType,@FormParam("title") String title,@FormParam("description") String description,@FormParam("approval")String approval,@FormParam("interruptionStartDate") String interruptionStartDate,@FormParam("interruptionEndDate") String interruptionEndDate) {
	
			String output = interruption.insertInterruption(intType, title, description, approval, interruptionStartDate, interruptionEndDate);
			return output;
		}
	
		//read
		@GET
		@Path("/")
		@Produces(MediaType.TEXT_HTML)
		public String readInterruption()
		{
			return interruption.readInterruption();
		}
	
		//update
		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String updateInterruption(String interruptionData)
		{
			//Convert the input string to a JSON object
			JsonObject interr =  new JsonParser().parse(interruptionData).getAsJsonObject();
			
			//Read the values from the JSON object
			String id = interr.get("id").getAsString();
			String intType = interr.get("intType").getAsString();
			String title = interr.get("title").getAsString();
			String description = interr.get("description").getAsString();
			String approval = interr.get("approval").getAsString();
			String interruptionStartDate = interr.get("interruptionStartDate").getAsString();
			String interruptionEndDate = interr.get("interruptionEndDate").getAsString();
			
			String output = interruption.updateInterruption(id, intType, title, description, approval, interruptionStartDate, interruptionEndDate);
			return output;
		}
		
		
		//Delete
		@DELETE
		@Path("/{id}")
		@Produces(MediaType.TEXT_PLAIN)
		public String deleteInterruption(@PathParam("id") String id) {
			String response = interruption.deleteInterruption(id);
			return response;
		}
		

}
