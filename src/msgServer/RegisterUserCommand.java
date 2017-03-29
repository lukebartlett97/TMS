package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class RegisterUserCommand implements Command  {
	  private BufferedReader in;
	  private BufferedWriter out;
	  private MsgSvrConnection conn;

	  public RegisterUserCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn)
	  {
	    this.in = in;
	    this.out = out;
	    this.conn = serverConn;
	  }
	  public void storeInFile(String[] userDetails){
		  //Stores userDetails in file
	  }

	  public void execute() throws IOException
	  {
		  String[] userDetails = new String[5];
		  /*PROTOCOL:
			Register New User
			107
			<username>
			<password>
			<DOB>
			<Telephone>
			<address>
			
			Needs to prompt for each <> using in.readLine()
			Creates a String[] of userDetails and then calls storeInFile(userDetails)
		   */
		  storeInFile(userDetails);
	  }
}
