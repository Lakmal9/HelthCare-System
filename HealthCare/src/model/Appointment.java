package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Appointment {
	public Connection connect() {
		Connection con = null;

		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/appointmentdb", "root", "");
			// For testing purpose
			System.out.print("Successfully connected");

		} catch (Exception e) {

			System.out.print("Connection fail");
			e.printStackTrace();
			System.out.print(e);
		}

		return con;
	}

	public String viewAppointments() {

		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			//Creating html table
			output = "<table border=\"1\"><tr><th>Appointment ID</th>" 
					+ "<th>Patient Name</th>"
					+ "<th>Patient ID</th>"
					+ "<th>Doctor Name</th>"
					+ "<th>Doctor Mobile</th>";

			String query = "select * from appointments";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String aId = rs.getString("aId");
				String pName = rs.getString("pName");
				String dId = rs.getString("dId");
				String dName = rs.getString("dName");
				String dMobile = rs.getString("dMobile");

				// Add into the html table
				output += "<tr><td>" + aId + "</td>";
				output += "<td>" + pName + "</td>";
				output += "<td>" + dId + "</td>";
				output += "<td>" + dName + "</td>";
				output += "<td>" + dMobile + "</td>";
			}
			
			con.close();
			// Completing the html table
			output += "</table>";

		} catch (Exception e) {
			output = "Error while reading the Doctors Details.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String makeAppointments(String aId, String pName, String dId, String dName, String dMobile) {

		String output = "";
		try {

			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database";
			}

			// create a prepared statement
			String query = " INSERT INTO doctors (aId, pName, dId, dName, dMobile) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, aId);
			preparedStmt.setString(2, pName);
			preparedStmt.setString(3, dId);
			preparedStmt.setString(4, dName);
			preparedStmt.setString(5, dMobile);
	
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";

		} catch (Exception e) {
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String updateAppointments(String aId, String pName, String dId, String dName, String dMobile) {

		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE doctors SET pName=?,dId=?,dName=?,dMobile=? WHERE aId=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, aId);
			preparedStmt.setString(2, pName);
			preparedStmt.setString(3, dId);
			preparedStmt.setString(4, dName);
			preparedStmt.setString(6, dMobile);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Appointment " +aId;
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteAppointments(String aId) {
		String output = "";
		try {

			Connection con = connect();
			if (con == null) {
				return "Error connection database for deleting.";
			}

			// create a prepared statement
			String query = "delete from appointments where aId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, aId);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";

		} catch (Exception e) {

			output = "Error while deleting the Appointment" +aId;
			System.err.println(e.getMessage());
		}

		return output;
	}

}
