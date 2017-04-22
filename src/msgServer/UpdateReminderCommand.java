package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class UpdateReminderCommand implements Command {
	private BufferedReader in;
	private BufferedWriter out;
	private MsgSvrConnection conn;

	public UpdateReminderCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn) {
		this.in = in;
		this.out = out;
		this.conn = serverConn;
	}

	public void execute() throws IOException {
		;
		// Takes in initial inputs
		String user = in.readLine();
		String title = in.readLine();
		String changeVariable = in.readLine();
		List<String> changeValues = new ArrayList<>();
		// Time requires more lines - array handles that
		if (changeVariable.equalsIgnoreCase("dateTime")) {
			changeValues.add(in.readLine());
			changeValues.add(in.readLine());
			changeValues.add(in.readLine());
			changeValues.add(in.readLine());
			changeValues.add(in.readLine());
		} else {
			changeValues.add(in.readLine());
		}
		String currentUser = conn.getCurrentUser();
		// Checks if a user is logged on
		if (currentUser == null) {
			(new ErrorCommand(in, out, conn, "Incorrect User")).execute();
			return;
		}
		// CHecks if the correct user is logged on
		if (!currentUser.equals(user)) {
			(new ErrorCommand(in, out, conn, "Incorrect User")).execute();
			return;
		}
		// Search for reminder with matching Username + Title combination
		Reminder reminderToSet = null;
		for (Reminder reminder : conn.getServer().getReminders().getReminders()) {
			if (reminder.getUsername().equals(user) && reminder.getTitle().equals(title)) {
				reminderToSet = reminder;
			}
		}
		// Reminder is not found error
		if (reminderToSet == null) {
			(new ErrorCommand(in, out, conn, "Reminder not found")).execute();
			return;
		}
		// Checks if all values are filled out
		for (String changeValue : changeValues) {
			if (changeValue == null) {
				(new ErrorCommand(in, out, conn, "Invalid Value")).execute();
				return;
			}
		}
		// Specific checks for each value in reminder - Same as checks used when
		// setting a reminder
		if (changeVariable.equalsIgnoreCase("title")) {
			// Check if reminder is taken
			List<Reminder> reminders = conn.getServer().getReminders().getReminders();
			for (Reminder reminder : reminders) {
				if (reminder.getUsername().equals(user) && reminder.getTitle().equals(changeValues.get(0))) {
					(new ErrorCommand(in, out, conn, "Reminder title already used.")).execute();
					return;
				}
			}
			reminderToSet.setTitle(changeValues.get(0));
		} else if (changeVariable.equalsIgnoreCase("dateTime")) {
			// Check all inputs are integers
			int[] dateTimeInts = new int[5];
			for (int i = 0; i < 5; i++) {
				try {
					dateTimeInts[i] = Integer.parseInt(changeValues.get(i));
				} catch (Exception e) {
					(new ErrorCommand(in, out, conn, "Invalid date entered.")).execute();
					return;
				}
			}
			LocalDateTime dateTime = LocalDateTime.of(dateTimeInts[0], dateTimeInts[1], dateTimeInts[2],
					dateTimeInts[3], dateTimeInts[4]);
			reminderToSet.setDateTime(dateTime);
		} else if (changeVariable.equalsIgnoreCase("message")) {
			reminderToSet.setMessage(changeValues.get(0));
		} else if (changeVariable.equalsIgnoreCase("type")) {
			// Check a valid type is entered
			if (!(changeValues.get(0).equals("text") || changeValues.get(0).equals("sound")
					|| changeValues.get(0).equals("popup"))) {
				(new ErrorCommand(in, out, conn, "Invalid Reminder Type.")).execute();
				return;
			}
			reminderToSet.setType(changeValues.get(0));
		} else {
			(new ErrorCommand(in, out, conn, "Invalid change variable")).execute();
			return;
		}
		out.write("200\r\n");
		out.flush();
	}
}
