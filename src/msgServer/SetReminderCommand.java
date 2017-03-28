package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class SetReminderCommand implements Command  {
	  private BufferedReader in;
	  private BufferedWriter out;
	  private MsgSvrConnection conn;

	  public SetReminderCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn)
	  {
	    this.in = in;
	    this.out = out;
	    this.conn = serverConn;
	  }
	  public void SaveReminder(String username, int field, String value){
		  //Saves the reminder
	  }

	  public void execute() throws IOException
	  {
		  ;
		  /* PROTOCOL:
		  Set Reminder
		  109
		  <username>
		  <date>
		  <time>
		  <"text"|"sound"|"popup">
		  <message>
		  
		  Prompts for each <> and checks they are valid.
		  Calls SaveReminder()
		  */
	  }
}
