package msgServer;

public class MsgProtocol 
{
  /*
   * The location of the password file.
   */
  public static final String PASSWORD_FILE = "pwd.txt";

  /* -------------- Commands --------------- */
  /**
   * client requests to login
   * Following lines are
   * username\r\n
   * password\r\n
   */
  public static final int LOGIN = 101;
  /**
   * Client requests logout
   * following line is:
   * username\r\n
   */
  public static final int LOGOUT = 102;
  /**
   * client requests send a message
   * Following lines are:
   * sender name\r\n
   * recipient name\r\n
   * content\r\n
   */
  public static final int SEND = 103;
  /**
   * client requests the number of messages
   * following lines are
   * username\r\n
   */
  public static final int MESSAGES_AVAILABLE = 104;
  /**
   * Client requests to get a single message
   * Following lines are:
   * username\r\n
   */
  public static final int GET_NEXT_MESSAGE = 105;
  /**
   * Client requests to get all messages
   * Following lines are:
   * username\r\n
   * Server reponds by sending all messages for that user
   */
  public static final int GET_ALL_MESSAGES = 106;
  /* -------------- Responses --------------- */
  /**
   * Server reponds OK
   */
  public static final int REGISTER_USER = 107;
  /**
   * 
   */
  public static final int UPDATE_USER = 108;
  /**
   * 
   */
  public static final int OK = 200;
  /**
   * Server reponds by sending one or more messages
   * following will be
   * An integer specifying number of messages terminated by \r\n
   * Then repeated for the number of messages are
   * sender terminated by \r\n
   * content terminated by \r\n
   */
  public static final int MESSAGE = 201;
  /**
   * The server sends an error message
   * Requires a one line error message terminated by \r\n
   */
  public static final int ERROR = 500;
}
