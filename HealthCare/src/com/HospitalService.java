package com;

import model.Hospital;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Hospital")
public class HospitalService {
	Hospital hospitalObj = new Hospital();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readHospital() {
		return hospitalObj.readHospital();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertHospital(@FormParam("name") String hospitalName,
	 @FormParam("location") String Location,
	 @FormParam("rooms") String Rooms)
	{
	 String output = hospitalObj.insertHospital(hospitalName, Location, Rooms);
	return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateHospital(String hospitalData)
	{
	//Convert the input string to a JSON object
	 JsonObject hospitalObject = new JsonParser().parse(hospitalData).getAsJsonObject();
	//Read the values from the JSON object
	 String id = hospitalObject.get("id").getAsString();
	 String name = hospitalObject.get("name").getAsString();
	 String location = hospitalObject.get("location").getAsString();
	 String rooms = hospitalObject.get("rooms").getAsString();
	 String output = hospitalObj.updateHospital(id, name, location, rooms);
	return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteHospital(String HospitalData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(HospitalData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String id = doc.select("id").text();
	 String output = hospitalObj.deleteHospital(id);
	return output;
	}

}
