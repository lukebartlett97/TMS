package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class CancelReminderCommand implements Command {
	private BufferedReader in;
	private BufferedWriter out;
	private MsgSvrConnection conn;

	public CancelReminderCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn) {
		this.in = in;
		this.out = out;
		this.conn = serverConn;
	}

	public void deleteReminder() {

	}

	public void execute() throws IOException {
		;
		//Use following line to remove a reminder from the list.
		//conn.getServer().removeReminder(reminder);
	}
}
