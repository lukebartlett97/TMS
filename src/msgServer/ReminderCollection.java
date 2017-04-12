package msgServer;

import java.util.*;

public class ReminderCollection {
	private List<Reminder> reminders;
	public ReminderCollection(){
		reminders = new ArrayList<Reminder>();
	}

	public void addReminder(Reminder reminder) {
		reminders.add(reminder);
	}

	public void removeReminder(Reminder reminder) {
		reminders.remove(reminder);
	}
	public List<Reminder> getReminders() {
		return reminders;
	}
}
