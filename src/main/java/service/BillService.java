package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Bill;
import model.PaymentCard;

@Path("/BillService")
public class BillService {
	
	Bill bill = new Bill();
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String createBill(@FormParam("accNo") String accNo ,@FormParam("custName") String custName,@FormParam("unitPrice") double unitPrice ,@FormParam("units") double units ) {
		String output = bill.insertDetails(accNo, custName, unitPrice, units);
		return output;
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems()
	{
	return bill.readItems();
	}
	
	//PaymentCard card = new PaymentCard();
	@POST
	@Path("/pay")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String createpayment(@FormParam("customer_name") String customer_name ,@FormParam("invoiceID") int invoiceID ,@FormParam("bill_accountNo") String bill_accountNo,@FormParam("date") String date ,@FormParam("pay_amount") double pay_amount,@FormParam("email") String email) {
		String output = bill.insertPaymentData(customer_name, bill_accountNo, date,pay_amount,email,invoiceID);
		return output;
	}
	
	@GET
	@Path("/pay")
	@Produces(MediaType.TEXT_HTML)
	public String readPayment()
	{
	return bill.readPayment();
	}
	

}
