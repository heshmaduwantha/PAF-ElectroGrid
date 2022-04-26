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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.PaymentCard;

@Path("/card")

public class PaymentCardServer {
	
	PaymentCard card = new PaymentCard();
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String createBill(@FormParam("card_number") String card_number ,@FormParam("cvv") int cvv,@FormParam("exp_date") String exp_date ,@FormParam("crd_holder_name") String crd_holder_name ,@FormParam("card_issued") String card_issued) {
		String output = card.insertDetails(card_number, cvv, exp_date, crd_holder_name,card_issued);
		return output;
	}
	
	//read
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems()
	{
	return card.readItems();
	}
	
	
//	//delete
//	@DELETE
//	@Path("/")
//	@Consumes(MediaType.APPLICATION_XML)
//	@Produces(MediaType.TEXT_PLAIN)
//	public String deleteItem(String cardData)
//	{
//	//Convert the input string to an XML document
//	Document doc = Jsoup.parse(cardData, "", Parser.xmlParser());
//	//Read the value from the element <userCard_ID>
//	String userCard_ID = doc.select("userCard_ID").text();
//	String output = card.deleteItem(userCard_ID);
//	return output;
//	}
	
	
	//Delete
	@DELETE
	@Path("/{userCard_ID}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCards(@PathParam("userCard_ID") String userCard_ID) {
		String response = card.deleteData(userCard_ID);
		return response;
	}
	
	
	//update
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCard(String cardData)
	{
	//Convert the input string to a JSON object
	JsonObject cardOb = new JsonParser().parse(cardData).getAsJsonObject();
	//Read the values from the JSON object
	String userCard_ID = cardOb.get("userCard_ID").getAsString();
	String card_number = cardOb.get("card_number").getAsString();
	String cvv = cardOb.get("cvv").getAsString();
	String exp_date = cardOb.get("exp_date").getAsString();
	String crd_holder_name = cardOb.get("crd_holder_name").getAsString();
	String card_issued = cardOb.get("card_issued").getAsString();
	
	String output = card.updateCard(userCard_ID, card_number, cvv, exp_date, crd_holder_name,card_issued);
	return output;
	}
	
	

	


}
