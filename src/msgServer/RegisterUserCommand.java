package msgServer;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class RegisterUserCommand implements Command {
	private BufferedReader in;
	private BufferedWriter out;
	private MsgSvrConnection conn;

	public RegisterUserCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn) {
		this.in = in;
		this.out = out;
		this.conn = serverConn;
	}

	public void storeInDatabase(String[] userDetails) throws IOException {
		Connection dbConnection = null;
		Statement dbStatement = null;
		// Checks if strings are empty. If so, sets them to null. Otherwise,
		// surrounds them with ' ' to make them literals in SQL string
		if (userDetails[2].equals("")) {
			userDetails[2] = null;
		} else {
			userDetails[2] = "'" + userDetails[2] + "'";
		}
		if (userDetails[3].equals("")) {
			userDetails[3] = null;
		} else {
			userDetails[3] = "'" + userDetails[3] + "'";
		}
		if (userDetails[4].equals("")) {
			userDetails[4] = null;
		} else {
			userDetails[4] = "'" + userDetails[4] + "'";
		}
		try {
			// Starts connection
			dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdetails", "groupcwk",
					"textMessaging");
			// Create a statement to use with the database.
			dbStatement = dbConnection.createStatement();
			// Creates a string to hold the SQL which allows a new user to be added to the table using the fields of userDetails.
			String inputSql = "insert into customers " + " (username, password, DOB, telNumber, address)" + " values ('"
					+ userDetails[0] + "', '" + userDetails[1] + "', " + userDetails[2] + ", " + userDetails[3] + ", "
					+ userDetails[4] + ")";
			// Execute the SQL statement.
			dbStatement.executeUpdate(inputSql);
			conn.userMsg("Successfully added " + userDetails[0] + " to database.");
			// Closes the connection and statement objects.
			dbConnection.close();
			dbStatement.close();
			// Handle any exceptions.
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		// Reloads database
		conn.getServer().loadFromDatabase();
	}

	public void storeInFile(String[] userDetails) throws IOException {
		// Stores userDetails in file
		FileWriter write = new FileWriter(MsgProtocol.PASSWORD_FILE, true);
		PrintWriter print_line = new PrintWriter(write);
		print_line.printf("%s=%s~%s~%s~%s\r\n", userDetails[0], userDetails[1], userDetails[2], userDetails[3],
				userDetails[4]);
		print_line.flush();
		print_line.close();

	}

	public void execute() throws IOException {
		String[] userDetails = new String[5];
		userDetails[0] = in.readLine();
		userDetails[1] = in.readLine();
		userDetails[2] = in.readLine();
		userDetails[3] = in.readLine().replace(" ", "");
		userDetails[4] = in.readLine();

		if (userDetails[0].equals("")) {
			(new ErrorCommand(in, out, conn, "Username is empty")).execute();
			return;
		} else if (conn.getServer().getUserPassword(userDetails[0]) != null) {
			(new ErrorCommand(in, out, conn, "That username already exists")).execute();
			return;
		}

		if (userDetails[1].length() < 3) {
			(new ErrorCommand(in, out, conn, "Password too short")).execute();
			return;
		}
		if (userDetails[2].equals("") == false) {
			if (userDetails[2].matches("([0-9]{4})-([0-9]{2})-([0-9]{2})") == false) {
				(new ErrorCommand(in, out, conn, "please enter a valid date format dd/mm/yyyy")).execute();
				return;

			}
		}

		if (userDetails[3].equals("") == false) {
			if (userDetails[3].length() != 11) {
				(new ErrorCommand(in, out, conn, "number must be 11 digits long")).execute();
				return;

			}
		}

		for (String x : userDetails) {
			if (x.contains("~")) {
				(new ErrorCommand(in, out, conn, "cannot contain ~")).execute();
				return;
			}

		}
		
		// Call the method storeInDatabase to register a new user and print out the status code 200 to show that it has been successful.
		storeInDatabase(userDetails);
		out.write("200 \r\n");
		out.flush();

		/*
		 * PROTOCOL: Register New User 107 <username> <password> <DOB>
		 * <Telephone> <address>
		 * 
		 * Needs to prompt for each <> using in.readLine() Creates a String[] of
		 * userDetails and then calls storeInFile(userDetails)
		 */
	}
}
