package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

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

		String selectedMessage = in.readLine();
		List<Reminder> reminders = conn.getServer().getReminders().getReminders();
		for (Reminder reminder : reminders) {
			if (reminder.getTitle().equals(selectedMessage)) {
				conn.getServer().getReminders().removeReminder(reminder);
				out.write("200\r\n");
				out.flush();
				return;
			}
		}

		(new ErrorCommand(in, out, conn, "Selected message was not found")).execute();

		// Use following line to remove a reminder from the list.
		// conn.getServer().removeReminder(reminder);
	}
}
