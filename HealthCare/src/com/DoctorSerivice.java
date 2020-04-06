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

import model.Doctor;

@Path("/doctor")
public class DoctorSerivice {

	Doctor dObj = new Doctor();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {
		return dObj.viewDoctors();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String enterDetails(
			@FormParam("dId") String id,
			@FormParam("dName") String name,
			@FormParam("dAddress") String address,
			@FormParam("dSpecialty") String specialty,
			@FormParam("dMobile") String mobile) {

		String output = dObj.addDoctor(id, name, address, specialty, mobile);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateDoctor(String DoctorData) {

		// Convert the input string to a JSON object
		JsonObject djosnObj = new JsonParser().parse(DoctorData).getAsJsonObject();

		// Read the values from the JSON object
		String dId = djosnObj.get("dId").getAsString();
		String dName = djosnObj.get("dName").getAsString();
		String dAddress = djosnObj.get("dAddress").getAsString();
		String dSpecialty = djosnObj.get("dSpecialty").getAsString();
		String dMobile = djosnObj.get("dMobile").getAsString();

		String output = dObj.updateDoctor(dId, dName, dAddress, dSpecialty, dMobile);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteDoctor(String DoctorData) {
		// Convert the input string to a JSON object
		JsonObject doc = new JsonParser().parse(DoctorData).getAsJsonObject();

		// Read the value from the element <ID>
		String dId = doc.get("dId").getAsString();
		String output = dObj.deleteDoctor(dId);
		return output;
	}

}
