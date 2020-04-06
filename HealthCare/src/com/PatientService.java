package com;

import model.Patient;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document; 

@Path("/Patients") 
public class PatientService {
	Patient pOBJ = new Patient();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems()
	 {
	 return pOBJ.viewPatient();
	 }
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String enterDetails(
		@FormParam("pId") String id,
		@FormParam("pName") String name,
		@FormParam("pAddress") String address,
		@FormParam("pSex") String sex,
		@FormParam("pDob") String dob,
		@FormParam("pMobile") String mob
	){
		String output = pOBJ.enterPdetails(id, name, address, sex, dob, mob);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePatient(String PatientData){
		
		//Convert the input string to a JSON object
		JsonObject patientObject = new JsonParser().parse(PatientData).getAsJsonObject();
	 
		//Read the values from the JSON object
		String pId = patientObject.get("pId").getAsString();
		String pName = patientObject.get("pName").getAsString();
		String pAddress = patientObject.get("pAddress").getAsString();
		String pSex = patientObject.get("pSex").getAsString();
		String pDob = patientObject.get("pDob").getAsString();
		String pMobile = patientObject.get("pMobile").getAsString();
	 
		String output = pOBJ.updatePatient(pId, pName, pAddress, pSex, pDob, pMobile);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePatient(String PatientData){
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(PatientData, "", Parser.xmlParser());

		//Read the value from the element <itemID>
		String pid = doc.select("pId").text();
		String output = pOBJ.deletePatient(pid);
		return output;
	}
}
