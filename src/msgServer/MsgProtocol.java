package msgServer;

public class MsgProtocol {
	/*
	 * The location of the password file.
	 */
	public static final String PASSWORD_FILE = "pwd.txt";

	/* -------------- Commands --------------- */
	/**
	 * client requests help. Following lines are a list of commands and their
	 * numbers
	 */
	public static final int HELP = 100;
	/**
	 * client requests to login. Following lines are username\r\n password\r\n
	 */
	public static final int LOGIN = 101;
	/**
	 * Client requests logout. Following line is: username\r\n
	 */
	public static final int LOGOUT = 102;
	/**
	 * client requests send a message. Following lines are: sender name\r\n
	 * recipient name\r\n content\r\n
	 */
	public static final int SEND = 103;
	/**
	 * client requests the number of messages. Following lines are username\r\n
	 */
	public static final int MESSAGES_AVAILABLE = 104;
	/**
	 * Client requests to get a single message. Following lines are:
	 * username\r\n
	 */
	public static final int GET_NEXT_MESSAGE = 105;
	/**
	 * Client requests to get all messages. Following lines are: username\r\n
	 * Server responds by sending all messages for that user
	 */
	public static final int GET_ALL_MESSAGES = 106;
	/**
	 * Client requests to register new user. Following lines are: username\r\n
	 * password\r\n DOB\r\n telephone\r\n address\r\n
	 */
	public static final int REGISTER_USER = 107;
	/**
	 * Client requests to register new user. Following lines are: username\r\n
	 * changeString\r\n newValue\r\n. changeString can be: username, password,
	 * dateofbirth, phonenumber, address
	 */
	public static final int UPDATE_USER = 108;
	/**
	 * Client requests to set a reminder. Following lines are: username\r\n
	 * title\r\n type\r\n year\r\n month\r\n day\r\n hour \r\n minutes\r\n
	 * message\r\n type can be popup, text, or sound. Title must be unique to
	 * that user.
	 */
	public static final int SET_REMINDER = 109;
	/**
	 * Client requests to access their reminders. Following lines are:
	 * username\r\n. Server responds by sending all reminders.
	 */
	public static final int ACCESS_REMINDERS = 110;
	/**
	 * Client requests to update a reminder. Following lines are: username\r\n
	 * title\r\n changeString\r\n changeValue\r\n. changeString can be: title,
	 * datetime, type, or message.
	 */
	public static final int UPDATE_REMINDER = 111;
	/**
	 * Client requests to cancel a reminder. Following lines are: username\r\n
	 * title\r\n.
	 */
	public static final int CANCEL_REMINDER = 112;
	/* -------------- Responses --------------- */
	/**
	 * Server responds OK
	 */
	public static final int OK = 200;
	/**
	 * Server reponds by sending one or more messages following will be An
	 * integer specifying number of messages terminated by \r\n Then repeated
	 * for the number of messages are sender terminated by \r\n content
	 * terminated by \r\n
	 */
	public static final int MESSAGE = 201;
	/**
	 * The server sends a reminder consisting of: a title, the date/time, alert
	 * type, and message separated by \r\n
	 */
	public static final int REMINDER = 300;
	/**
	 * The server sends an error message Requires a one line error message
	 * terminated by \r\n
	 */
	public static final int ERROR = 500;
}
