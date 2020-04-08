package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Appointment;

@Path("/appointment")

public class AppointmentService {
	Appointment dObj = new Appointment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {
		return dObj.viewAppointments();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String enterDetails(
			@FormParam("aId") String aId,
			@FormParam("pName") String pName,
			@FormParam("dId") String dId,
			@FormParam("dName") String dName,
			@FormParam("dMobile") String dMobile) {

		String output = dObj.makeAppointments(aId, pName, dId, dName, dMobile);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateDoctor(String AppointmentData) {

		// Convert the input string to a JSON object
		JsonObject djosnObj = new JsonParser().parse(AppointmentData).getAsJsonObject();

		// Read the values from the JSON object
		String aId = djosnObj.get("aId").getAsString();
		String pName = djosnObj.get("pName").getAsString();
		String dId = djosnObj.get("dId").getAsString();
		String dName = djosnObj.get("dName").getAsString();
		String dMobile = djosnObj.get("dMobile").getAsString();

		String output = dObj.updateAppointments(aId, pName, dId, dName, dMobile);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteAppointments(String AppointmentData) {
		// Convert the input string to a JSON object
		JsonObject doc = new JsonParser().parse(AppointmentData).getAsJsonObject();

		// Read the value from the element <ID>
		String aId = doc.get("aId").getAsString();
		String output = dObj.deleteAppointments(aId);
		return output;
	}

}
