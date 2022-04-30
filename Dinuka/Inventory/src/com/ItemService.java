package com; 
import model.Item; 
//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 
@Path("/Users") 
public class ItemService 
{ 
	Item userObj = new Item(); 
 @GET
@Path("/") 
@Produces(MediaType.TEXT_HTML) 
public String readItems() 
 { 
 return userObj.readUsers(); 
}
 
 
 @POST
 @Path("/") 
 @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
 @Produces(MediaType.TEXT_PLAIN) 
 public String insertItem(@FormParam("itemName") String itemName, 
  @FormParam("itemQty") String itemQty, 
  @FormParam("itemCode") String itemCode, 
  @FormParam("descripation") String descripation) 
 { 
  String output = userObj.insertUser(itemName,itemQty,itemCode,descripation); 
 return output; 
 }

 
 @PUT
 @Path("/") 
 @Consumes(MediaType.APPLICATION_JSON) 
 @Produces(MediaType.TEXT_PLAIN) 
 public String updateItem(String userData) 
 { 
 //Convert the input string to a JSON object 
  JsonObject userObject = new JsonParser().parse(userData).getAsJsonObject(); 
 //Read the values from the JSON object
  String itemID = userObject.get("itemID").getAsString(); 
  String itemName = userObject.get("itemName").getAsString(); 
  String itemQty = userObject.get("itemQty").getAsString(); 
  String itemCode = userObject.get("itemCode").getAsString(); 
  String  descripation = userObject.get("descripation").getAsString(); 
  String output = userObj.updateUser(itemID, itemName, itemQty, itemCode, descripation); 
 return output; 
 }

 
 @DELETE
 @Path("/") 
 @Consumes(MediaType.APPLICATION_XML) 
 @Produces(MediaType.TEXT_PLAIN) 
 public String deleteItem(String itemData) 
 { 
 //Convert the input string to an XML document
  Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
  
 //Read the value from the element <itemID>
  String userID = doc.select("itemID").text(); 
  String output = userObj.deleteUser(userID); 
 return output; 
 }
 
}