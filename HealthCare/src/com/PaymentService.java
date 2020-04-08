package com;
import model.payment;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document; 

@Path("/payment")
public class PaymentService {
	
	payment payObj = new payment();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String viewPayment() {
		return payObj.viewPayment();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String enterDetails(
			@FormParam("payId") String id,
			@FormParam("pName") String name,
			@FormParam("pAddress") String address,
			@FormParam("time") String time,
			@FormParam("date") String date,
			@FormParam("amount") String amount) {

		String output = payObj.insertPayment(id, name, address, time, date,amount);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePayment(String PaymentData) {

		// Convert the input string to a JSON object
		JsonObject djosnObj = new JsonParser().parse(PaymentData).getAsJsonObject();

		// Read the values from the JSON object
		String payId = djosnObj.get("payId").getAsString();
		String pName = djosnObj.get("pName").getAsString();
		String pAddress = djosnObj.get("pAddress").getAsString();
		String time = djosnObj.get("time").getAsString();
		String date = djosnObj.get("date").getAsString();
		String amount = djosnObj.get("amount").getAsString();

		String output = payObj.updatePayment(payId, pName, pAddress, time,date, amount);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteDoctor(String PaymentData) {
		// Convert the input string to a JSON object
		JsonObject doc = new JsonParser().parse(PaymentData).getAsJsonObject();

		// Read the value from the element <ID>
		String payId = doc.get("payId").getAsString();
		String output = payObj.deletePayment(payId);
		return output;
	}
}
