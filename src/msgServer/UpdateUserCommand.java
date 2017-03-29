package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class UpdateUserCommand implements Command {
	private BufferedReader in;
	private BufferedWriter out;
	private MsgSvrConnection conn;

	public UpdateUserCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn) {
		this.in = in;
		this.out = out;
		this.conn = serverConn;
	}

	public void editFile(String username, int field, String value) {
		//REMEMBER: When changing username, change current user
		//When changing ANYTHING, update userInfo in MessageServer
		System.out.println("TEST - Username:" + username + " FieldNo:" + field + " Value:" + value);
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
			editFile(username, 0, changeValue);
		} else if (changeVariable.equalsIgnoreCase("password")) {
			if (changeVariable.length() <= 3) {
				(new ErrorCommand(in, out, conn, "Password too short")).execute();
				return;
			}
			editFile(username, 1, changeValue);
		} else if (changeVariable.equalsIgnoreCase("dateofbirth")) {
			if (changeVariable.equals("") == false) {
				if (changeVariable.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})") == false) {
					(new ErrorCommand(in, out, conn, "please enter a valid date format dd/mm/yyyy")).execute();
					return;
				}
			}
			editFile(username, 2, changeValue);
		} else if (changeVariable.equalsIgnoreCase("phonenumber")) {
			if (changeVariable.equals("") == false) {
				if (changeVariable.length() != 11) {
					(new ErrorCommand(in, out, conn, "number must be 11 digits long")).execute();
					return;
				}
			}
			editFile(username, 3, changeValue);
		} else if (changeVariable.equalsIgnoreCase("address")) {
			editFile(username, 4, changeValue);
		}
		else{
			(new ErrorCommand(in, out, conn, "Valid types: username | password | dateofbirth | phonenumber | address")).execute();
		}
	}
}
