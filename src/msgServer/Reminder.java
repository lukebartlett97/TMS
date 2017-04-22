package msgServer;

import java.time.LocalDateTime;

//Class representing a reminder
public class Reminder {
	private LocalDateTime dateTime;
	private String title, message, username, type;

	public Reminder(String username, String title, LocalDateTime dateTime, String type, String message) {
		this.username = username;
		this.title = title;
		this.dateTime = dateTime;
		this.type = type;
		this.message = message;
	}

	public String createAlertMessage() {
		return title + "\r\n" + dateTime + "\r\n" + type + "\r\n" + message;
	}

	public LocalDateTime getDate() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
