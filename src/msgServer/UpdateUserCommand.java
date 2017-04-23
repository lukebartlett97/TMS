package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.*;

public class UpdateUserCommand implements Command {
	private BufferedReader in;
	private BufferedWriter out;
	private MsgSvrConnection conn;

	public UpdateUserCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn) {
		this.in = in;
		this.out = out;
		this.conn = serverConn;
	}

	public void editDatabase(String username, String field, String value) {
		Connection dbConnection = null;
		Statement updateStatement = null;
		// If the user inputs an empty string for a field, make it null so that the database can process it.
		if (value.equals("")) {
			value = null;
			// Change the user's input in to literal in SQL string so it is accessible by the database.
		} else {
			value = "'" + value + "'";
		}
		try {
			// Connect to the database.
			dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdetails", "groupcwk",
					"textMessaging");
			// Create a statement for the database.
			updateStatement = dbConnection.createStatement();
			// Create a string that holds the SQL information to update a field for a given username.
			String updateSql = "update customers " + " set " + field + "=" + value + " where username= '" + username
					+ "'";
			// Execute the update.
			updateStatement.executeUpdate(updateSql);
			// Provide a message to confirm that the update has been successful.
			conn.userMsg("Successfully updated database.");
			// Close the connection and statement objects.
			dbConnection.close();
			updateStatement.close();
			// Handle any exceptions.
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		// Reload the database with the new update.
		conn.getServer().loadFromDatabase();
	}

	public void editFile(String username, String field, String value) {
		// REMEMBER: When changing username, change current user
		// When changing ANYTHING, update userInfo in MessageServer
		System.out.println("TEST - Username:" + username + " FieldNo:" + field + " Value:" + value);
	}

	public void changeUsername(String oldName, String newName) {
		for (Reminder reminder : conn.getServer().getReminders().getReminders()) {
			if (reminder.getUsername().equals(oldName)) {
				reminder.setUsername(newName);
			}
		}
		conn.getServer().getMessages().changeUsername(oldName, newName);
		conn.setCurrentUser(newName);
	}

	public void execute() throws IOException {
		String currentUser = conn.getCurrentUser();
		String username = in.readLine();
		String changeVariable = in.readLine();
		String changeValue = in.readLine();

		// A user is logged on
		if (currentUser == null) {
			(new ErrorCommand(in, out, conn, "You are not logged on")).execute();
			return;
		}

		// Username entered is current user
		if (!currentUser.equals(username)) {
			(new ErrorCommand(in, out, conn, "You can only edit current user")).execute();
			return;
		}

		// Checking which field to edit, and completing suitable validation
		if (changeVariable.equalsIgnoreCase("username")) {
			if (changeValue.equals("")) {
				(new ErrorCommand(in, out, conn, "Username is empty")).execute();
				return;
			}
			if (conn.getServer().getUserPassword(changeValue) != null) {
				(new ErrorCommand(in, out, conn, "That username already exists")).execute();
				return;
			}
			// Edit the username in the database for the current user.
			editDatabase(username, "username", changeValue);
			changeUsername(currentUser, username);
		} else if (changeVariable.equalsIgnoreCase("password")) {
			if (changeValue.length() < 3) {
				(new ErrorCommand(in, out, conn, "Password too short")).execute();
				return;
			}
			// Edit the password in the database for the current user.
			editDatabase(username, "password", changeValue);
		} else if (changeVariable.equalsIgnoreCase("dateofbirth")) {
			if (changeValue.equals("") == false) {
				if (changeValue.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})") == false) {
					(new ErrorCommand(in, out, conn, "please enter a valid date format yyyy-mm-dd")).execute();
					return;
				}
			}
			// Edit the date of birth in the database for the current user.
			editDatabase(username, "DOB", changeValue);
		} else if (changeVariable.equalsIgnoreCase("phonenumber")) {
			if (changeValue.equals("") == false) {
				if (changeValue.length() != 11) {
					(new ErrorCommand(in, out, conn, "number must be 11 digits long")).execute();
					return;
				}
			}
			// Edit the telephone number in the database for the current user.
			editDatabase(username, "telNumber", changeValue);
		} else if (changeVariable.equalsIgnoreCase("address")) {
			// Edit the address in the database for the current user.
			editDatabase(username, "address", changeValue);
		} else {
			(new ErrorCommand(in, out, conn, "Valid types: username | password | dateofbirth | phonenumber | address"))
					.execute();
			return;
		}
		out.write("200 \r\n");
		out.flush();
	}
}
