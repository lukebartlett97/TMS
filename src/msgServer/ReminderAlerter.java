package msgServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

public class ReminderAlerter extends Thread {
	MessageServer server;

	public ReminderAlerter(MessageServer server) {
		super();
		this.server = server;
	}

	public void run() {
		;
		// Loops over each reminder
		//// checks if it is time for reminder to go off using
		// alertTime(reminder)
		////// checks where user is logged in using findUserSocket()
		////// if socket is not null
		//////// Send message to user
		//////// Update reminder status/remove reminder
		while (true) {
			List<Reminder> reminders = getReminders();
			if (reminders != null) {
				for (Reminder reminder : reminders) {
					if (alertTime(reminder)) {
						Socket userSocket = findUserSocket(reminder.getUsername());
						if (userSocket != null) {
							sendReminder(reminder, userSocket);
							reminders.remove(reminder);
						}
					}
				}
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean alertTime(Reminder reminder) {
		return false;
	}

	private List<Reminder> getReminders() {
		return null;
	}

	private Socket findUserSocket(String username) {
		for (MsgSvrConnection conn : server.getConnections()) {
			if (conn.getCurrentUser().equals(username)) {
				return conn.getSocket();
			}
		}
		return null;
	}

	private void sendReminder(Reminder reminder, Socket socket) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			writer.write("300\r\n");
			writer.write(reminder.createAlertMessage() + "\r\n");
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
