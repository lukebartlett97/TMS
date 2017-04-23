package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccessRemindersCommand implements Command {
	private BufferedReader in;
	private BufferedWriter out;
	private MsgSvrConnection conn;

	public AccessRemindersCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn) {
		this.in = in;
		this.out = out;
		this.conn = serverConn;
	}

	public void execute() throws IOException {

		String username = in.readLine();
		if (username == null) {
			(new ErrorCommand(in, out, conn, "Incorrect User")).execute();
			return;
		}

		String currentUser = conn.getCurrentUser();
		if (!username.equals(currentUser)) {
			(new ErrorCommand(in, out, conn, "Incorrect User")).execute();
			return;
		}

		if (currentUser == null) {
			(new ErrorCommand(in, out, conn, "Incorrect User")).execute();
			return;
		}

		List<Reminder> printReminders = new ArrayList<>();
		List<Reminder> reminders = conn.getServer().getReminders().getReminders();
		for (Reminder reminder : reminders) {
			if (reminder.getUsername().equals(currentUser)) {
				printReminders.add(reminder);
			}
		}

		out.write("202");
		out.write("" + printReminders.size());
		for (Reminder reminder : printReminders) {
			out.write(reminder.createAlertMessage() + "\r\n");
		}
		out.flush();

		// Use following line to get reminder list for all users.
		// List<Reminder> reminders = conn.getServer().getReminders();
	}
}
