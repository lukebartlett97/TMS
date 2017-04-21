package msgServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReminderAlerter extends Thread {
	private MessageServer server;

	public ReminderAlerter(MessageServer server) {
		super();
		this.server = server;
	}

	public void run() {
		;
		// Main thread loop
		while (true) {
			System.out.println("Checking reminders...");
			List<Reminder> remindersToRemove = new ArrayList<>();
			// Loops through each reminder
			for (Reminder reminder : server.getReminders().getReminders()) {
				// Checks if it is time to give the user the alarm
				if (alertTime(reminder)) {
					System.out.println("Time for reminder!");
					// Finds the socket that the user is logged into - null if
					// user isnt logged into server
					Socket userSocket = findUserSocket(reminder.getUsername());
					if (userSocket != null) {
						// Sends reminder and removes it from list
						sendReminder(reminder, userSocket);
						System.out.println("Reminder sent.");
						remindersToRemove.add(reminder);
					}
					else{
						System.out.println("User not logged in..");						
					}
				}
			}
			for(Reminder reminder : remindersToRemove){
				server.getReminders().removeReminder(reminder);
			}
			// Sleeps thread
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean alertTime(Reminder reminder) {
		// Checks if it is time to alert the user
		return reminder.getDate().isBefore(LocalDateTime.now());
	}

	private Socket findUserSocket(String username) {
		// Loops through each connection
		for (MsgSvrConnection connection : server.getConnections()) {
			// Checks if user on that connection is the user that the reminder
			// was set for
			if (connection.getCurrentUser().equals(username)) {
				// Returns the socket
				return connection.getSocket();
			}
		}
		// Returns null if user not found
		return null;
	}

	private void sendReminder(Reminder reminder, Socket socket) {
		// Sends reminder message to user
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			writer.write("" + MsgProtocol.REMINDER + "\r\n");
			writer.write(reminder.createAlertMessage() + "\r\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
