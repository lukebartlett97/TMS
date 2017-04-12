package msgServer;

import java.time.LocalDateTime;

//Class representing a reminder
public class Reminder {
	private LocalDateTime dateTime;
	private ReminderType type;
	private String title, message, username;

	public Reminder(String username, String title, LocalDateTime dateTime, ReminderType type, String message) {
		// TODO: Add time method. Date to subject to type change in case there
		// is an alternative (possibly util.Date),
		// but Tawil used GregorianCalendar in Message.java
		this.username = username;
		this.title = title;
		this.dateTime = dateTime;
		this.type = type;
		this.message = message;
	}
	public String createAlertMessage(){
		return title + "\r\n" + dateTime + "\r\n" + type + "\r\n" + message;
	}
	public LocalDateTime getDate() {
		return dateTime;
	}

	public ReminderType getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

	public String getUsername() {
		return username;
	}
}
