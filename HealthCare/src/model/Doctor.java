package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Doctor {
	public Connection connect() {
		Connection con = null;

		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/doctorDb", "root", "");
			// For testing
			System.out.print("Successfully connected");

		} catch (Exception e) {

			System.out.print("connection fail");
			e.printStackTrace();
			System.out.print(e);
		}

		return con;
	}

	public String viewDoctors() {

		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Doctor ID</th>" 
					+ "<th>Doctor Name</th><th>Doctor Address</th>"
					+ "<th>Doctor Specialty</th><th>Doctor Mobile</th>";

			String query = "select * from doctors";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String dId = rs.getString("DId");
				String dName = rs.getString("DName");
				String dAddress = rs.getString("DAddress");
				String dSpecialty = rs.getString("DSpecialty");
				String dMobile = rs.getString("DMobile");

				// Add into the html table
				output += "<tr><td>" + dId + "</td>";
				output += "<td>" + dName + "</td>";
				output += "<td>" + dAddress + "</td>";
				output += "<td>" + dSpecialty + "</td>";
				output += "<td>" + dMobile + "</td>";
			}
			
			con.close();
			// Complete the html table
			output += "</table>";

		} catch (Exception e) {
			output = "Error while reading the Doctors Details.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String addDoctor(String id, String name, String address, String specialty, String mobile) {

		String output = "";
		try {

			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database";
			}

			// create a prepared statement
			String query = " INSERT INTO doctors (DId, DName, DAddress, DSpecialty, DMobile) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, id);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, address);
			preparedStmt.setString(4, specialty);
			preparedStmt.setString(5, mobile);
	
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

	public String updateDoctor(String id, String name, String address, String specialty, String mobile) {

		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE doctors SET DName=?,DAddress=?,DSpecialty=?,DMobile=? WHERE DId=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, name);
			preparedStmt.setString(2, address);
			preparedStmt.setString(3, specialty);
			preparedStmt.setString(4, mobile);
			preparedStmt.setString(6, id);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the Doctor " +id;
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteDoctor(String dId) {
		String output = "";
		try {

			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from doctors where DId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, dId);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";

		} catch (Exception e) {

			output = "Error while deleting the Doctor" +dId;
			System.err.println(e.getMessage());
		}

		return output;
	}
}
