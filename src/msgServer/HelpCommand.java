package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class HelpCommand implements Command {
	private BufferedReader in;
	private BufferedWriter out;
	private MsgSvrConnection conn;

	public HelpCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn) {
		this.in = in;
		this.out = out;
		this.conn = serverConn;
	}

	public void execute() throws IOException {
		out.write("Commands\r\n");
		out.write("--------\r\n");
		out.write("101: Login\r\n");
		out.write("102: Logout\r\n");
		out.write("103: Send\r\n");
		out.write("104: Messages Available\r\n");
		out.write("105: Get next Message\r\n");
		out.write("106: Get All Messages\r\n");
		out.write("107: Register User\r\n");
		out.write("108: Update User\r\n");
		out.write("109: Set Reminder\r\n");
		out.write("110: Access Reminders\r\n");
		out.write("111: Update Reminder\r\n");
		out.write("112: Cancel Reminder\r\n\r\n");
		out.flush();
	}
}
