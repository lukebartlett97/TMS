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
		// Stores userDetails in file
	}

	public void execute() throws IOException {
		String currentUser = conn.getCurrentUser();
		String username = in.readLine();
		String changeVariable = in.readLine();
		String changeValue = in.readLine();

		if (conn.getServer().getUserPassword(username) == null) {
			out.write("108 \r\n");
			out.write("\r\n");
			out.flush();
			return;
		}
		if (currentUser != null) {
			if (currentUser.equals(username)) {
				if (changeVariable.equalsIgnoreCase("username")) {
					if (changeValue.equals("")) {
						out.write("500 \r\n");
						out.write("Username is empty \r\n");
						out.flush();
						return;
					}
					if (conn.getServer().getUserPassword(changeValue) != null) {
						out.write("500 \r\n");
						out.write("That username already exists \r\n");
						out.flush();
						return;
					}
					editFile(username, 0, changeValue);

				}
				if (changeVariable.equalsIgnoreCase("password")) {
					if (changeVariable.length() <= 3) {
						out.write("500 \r\n");
						out.write("Password too short \r\n");
						out.flush();
						return;
					}
					editFile(username, 1, changeValue);
				}
				if (changeVariable.equalsIgnoreCase("dateofbirth")) {
					if (changeVariable.equals("") == false) {
						if (changeVariable.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})") == false) {
							out.write("500 \r\n");
							out.write("please enter a valid date format dd/mm/yyyy \r\n");
							out.flush();
							return;

						}
					}
					editFile(username, 2, changeValue);
				}
				if (changeVariable.equalsIgnoreCase("phonenumber")) {
					if (changeVariable.equals("") == false)
					  {
						if (changeVariable.length() != 11)
						{
							out.write("500 \r\n"); 
							out.write("number must be 11 digits long");
							out.flush();
							return;
							
						}
					  }
					
					editFile(username, 3, changeValue);
				}
				if (changeVariable.equalsIgnoreCase("address")) {
					editFile(username, 4, changeValue);
				}

			}
		}

		/*
		 * PROTOCOL: Update User 108 <username> <changeString> <newValue>
		 * 
		 * Needs to prompt for each <> using in.readLine() Works out field based
		 * on changeString Calls editFile(username, field, value)
		 */
	}
}
