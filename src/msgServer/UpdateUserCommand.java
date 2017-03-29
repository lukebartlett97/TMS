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
		/*
		 * PROTOCOL: Update User 108 <username> <changeString> <newValue>
		 * 
		 * Needs to prompt for each <> using in.readLine() Works out field based
		 * on changeString Calls editFile(username, field, value)
		 */
	}
}
