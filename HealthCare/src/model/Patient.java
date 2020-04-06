package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Patient {
	
	public Connection connect() {
		 Connection con = null;

		 try {
			 
			 Class.forName("com.mysql.jdbc.Driver");
			 con= DriverManager.getConnection("jdbc:mysql://localhost/patient_db","root","");
			 //For testing
			 System.out.print("Successfully connected");
		
		 }catch(Exception e) {
			 
			 System.out.print("not connected");
			 e.printStackTrace();
			 System.out.print(e);
		 }

		 return con;
		}
	
	public String viewPatient(){
		 
		String output = "";
		try{
			Connection con = connect();
			if (con == null){
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Patient ID</th>"
					+"<th>Patient Name</th><th>Patient Address</th>"
					+ "<th>Patient SEX</th><th>Patient DOB</th><th>Patient Mobile</th>"
					+ "</tr>";
			
			String query = "select * from p_details";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()){
				String pId = rs.getString("pId");
				String pName = rs.getString("pName");
				String pAddress = rs.getString("pAddress");
				String pSex = rs.getString("pSex");
				String pDob = rs.getString("pDob");
				String pMobile = rs.getString("pMobile");
				
				// Add into the html table
				output += "<tr><td>" + pId + "</td>";
				output += "<td>" + pName + "</td>"; 
				output += "<td>" + pAddress + "</td>"; 
				output += "<td>" + pSex + "</td>";
				output += "<td>" + pDob + "</td>";
				output += "<td>" + pMobile + "</td>";
			}	
			con.close();
			// Complete the html table
			output += "</table>";
			
			
		}catch (Exception e){
			output = "Error while reading the Patient Details.";
			System.err.println(e.getMessage());
		}
		
	return output;
	}
	
	public String enterPdetails(String id, String name, String address, String sex, String dob, String mob){
		
		String output = "";
		try {
			
		 Connection con = connect();
		 
		 if (con == null){
			 return "Error while connecting to the database";
		 }
		 
		 // create a prepared statement
		 String query = " INSERT INTO p_details (pId, pName, pAddress, pSex, pDob, pMobile) VALUES (?, ?, ?, ?, ?, ?)";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		 // binding values
		 preparedStmt.setString(1, id);
		 preparedStmt.setString(2, name);
		 preparedStmt.setString(3, address);
		 preparedStmt.setString(4, sex);
		 preparedStmt.setString(5, dob);
		 preparedStmt.setString(6, mob); 
		 
		//execute the statement
		 preparedStmt.execute();
		 con.close();
		 output = "Inserted successfully";
		 
		 }catch (Exception e){
			 output = "Error while inserting";
			 System.err.println(e.getMessage());
		 }
		
		return output; 
	}
	
	public String updatePatient(String id, String name, String address, String sex, String dob, String mob){

		String output = "";

		try{
			Connection con = connect();
			if (con == null){
				return "Error while connecting to the database for updating."; 
			}
			// create a prepared statement
			String query = "UPDATE p_details SET pName=?,pAddress=?,pSex=?,pDob=?,pMobile=? WHERE pId=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			
			 preparedStmt.setString(1, name);
			 preparedStmt.setString(2, address);
			 preparedStmt.setString(3, sex);
			 preparedStmt.setString(4, dob);
			 preparedStmt.setString(5, mob);
			 preparedStmt.setString(6, id);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		}
		catch (Exception e)
		{
			output = "Error while updating the Patient." +e;
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String deletePatient(String pID)
	{
		String output = "";
		try{
			
		 Connection con = connect();
		 if (con == null){
			 return "Error while connecting to the database for deleting.";
		 }
		 
		 // create a prepared statement
		 String query = "delete from p_details where pId=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		 // binding values
		 preparedStmt.setString(1, pID);

		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 output = "Deleted successfully";
		 
		}catch (Exception e){
			
		 output = "Error while deleting the Patient." +e;
		 System.err.println(e.getMessage());
		}
		
		return output; 
	}
}
