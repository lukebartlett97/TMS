package msgServer;

import java.util.GregorianCalendar;

//Class representing a reminder
public class Reminder {
	private GregorianCalendar date;
	private ReminderType type;
	private String title, message, username;

	public Reminder(String username, String title, GregorianCalendar date, ReminderType type, String message) {
		// TODO: Add time method. Date to subject to type change in case there
		// is an alternative (possibly util.Date),
		// but Tawil used GregorianCalendar in Message.java
		this.username = username;
		this.title = title;
		this.date = date;
		this.type = type;
		this.message = message;
	}
	public String createAlertMessage(){
		return title + "\r\n" + date + "\r\n" + type + "\r\n" + message;
	}
	public GregorianCalendar getDate() {
		return date;
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
