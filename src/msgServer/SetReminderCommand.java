package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class SetReminderCommand implements Command {
	private BufferedReader in;
	private BufferedWriter out;
	private MsgSvrConnection conn;

	public SetReminderCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn) {
		this.in = in;
		this.out = out;
		this.conn = serverConn;
	}

	// LocalDateTime
	public void execute() throws IOException {
		String[] reminderDetails = new String[9];
		int[] reminderDetailsInts = new int[9];
		reminderDetails[0] = in.readLine(); // username
		reminderDetails[1] = in.readLine(); // title
		reminderDetails[2] = in.readLine(); // type
		reminderDetails[3] = in.readLine(); // year
		reminderDetails[4] = in.readLine(); // month
		reminderDetails[5] = in.readLine(); // day
		reminderDetails[6] = in.readLine(); // hour
		reminderDetails[7] = in.readLine(); // minute
		reminderDetails[8] = in.readLine(); // message
		System.out.println("All inputs taken");
		if (reminderDetails[0] == null) {
			(new ErrorCommand(in, out, conn, "Incorrect User")).execute();
			return;
		}

		String currentUser = conn.getCurrentUser();
		if (!reminderDetails[0].equals(currentUser)) {
			(new ErrorCommand(in, out, conn, "Incorrect User")).execute();
			return;
		}

		if (currentUser == null) {
			(new ErrorCommand(in, out, conn, "Incorrect User")).execute();
			return;
		}

		if (reminderDetails[1] == null) {
			(new ErrorCommand(in, out, conn, "Title is empty")).execute();
			return;
		}

		List<Reminder> reminders = conn.getServer().getReminders().getReminders();
		for (Reminder reminder : reminders) {
			if (reminder.getTitle().equals(reminderDetails[1]) && reminder.getUsername().equals(reminderDetails[0])) {
				(new ErrorCommand(in, out, conn, "A reminder with that title already exists")).execute();
				return;
			}
		}

		if (!(reminderDetails[2].equals("text") || reminderDetails[2].equals("sound")
				|| reminderDetails[2].equals("popup"))) {
			(new ErrorCommand(in, out, conn, "Please enter a valid alert type")).execute();
			return;
		}

		System.out.println("Non-date validation complete.");
		if (reminderDetails[3] == null) {
			(new ErrorCommand(in, out, conn, "Year is empty")).execute();
			return;
		}
		try {
			reminderDetailsInts[0] = Integer.parseInt(reminderDetails[3]);
		} catch (Exception e) {
			(new ErrorCommand(in, out, conn, "Year is not a valid number")).execute();
			return;
		}

		if (reminderDetails[4] == null) {
			(new ErrorCommand(in, out, conn, "Year is empty")).execute();
			return;
		}
		try {
			reminderDetailsInts[1] = Integer.parseInt(reminderDetails[4]);
		} catch (Exception e) {
			(new ErrorCommand(in, out, conn, "Year is not a valid number")).execute();
			return;
		}

		if (reminderDetails[5] == null) {
			(new ErrorCommand(in, out, conn, "Year is empty")).execute();
			return;
		}
		try {
			reminderDetailsInts[2] = Integer.parseInt(reminderDetails[5]);
		} catch (Exception e) {
			(new ErrorCommand(in, out, conn, "Year is not a valid number")).execute();
			return;
		}

		if (reminderDetails[6] == null) {
			(new ErrorCommand(in, out, conn, "Year is empty")).execute();
			return;
		}
		try {
			reminderDetailsInts[3] = Integer.parseInt(reminderDetails[6]);
		} catch (Exception e) {
			(new ErrorCommand(in, out, conn, "Year is not a valid number")).execute();
			return;
		}

		if (reminderDetails[7] == null) {
			(new ErrorCommand(in, out, conn, "Year is empty")).execute();
			return;
		}
		try {
			reminderDetailsInts[4] = Integer.parseInt(reminderDetails[7]);
		} catch (Exception e) {
			(new ErrorCommand(in, out, conn, "Year is not a valid number")).execute();
			return;
		}

		System.out.println("Date validation complete");
		LocalDateTime dateTime = LocalDateTime.of(reminderDetailsInts[0], reminderDetailsInts[1],
				reminderDetailsInts[2], reminderDetailsInts[3], reminderDetailsInts[4]);

		System.out.println("LocalDateTime created");
		Reminder reminder = new Reminder(reminderDetails[0], reminderDetails[1], dateTime, reminderDetails[2],
				reminderDetails[8]);

		System.out.println("Reminder created");
		conn.getServer().getReminders().addReminder(reminder);
		System.out.println("Reminder added");
		out.write("200\r\n");
		out.flush();
		/*
		 * PROTOCOL: Set Reminder 109 <username> <title>
		 * <"text"|"sound"|"popup"> <year> <month> <day> <hour> <minute>
		 * <message>
		 *
		 * 
		 * Prompts for each <> and checks they are valid. Calls SaveReminder()
		 */

		// Use following line to add a reminder to the list.
		// conn.getServer().addReminder(reminder);
	}
}
