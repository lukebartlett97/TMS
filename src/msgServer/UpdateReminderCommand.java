package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class UpdateReminderCommand implements Command {
	private BufferedReader in;
	private BufferedWriter out;
	private MsgSvrConnection conn;

	public UpdateReminderCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn) {
		this.in = in;
		this.out = out;
		this.conn = serverConn;
	}

	public void updateReminder() {

	}

	public void execute() throws IOException {
		;
	}
}
