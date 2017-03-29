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
		  userDetails[0] = in.readLine();
		  userDetails[1] = in.readLine();
		  userDetails[2] = in.readLine();
		  userDetails[3] = in.readLine().replace(" ","");
		  userDetails[4] = in.readLine();
		  
		  if (userDetails[0].equals(""))
		  {
			out.write("500 \r\n"); 
			out.write("Username is empty \r\n");
			out.flush();
			return;  
		  }
		  else if (conn.getServer().getUserPassword(userDetails[0]) != null)
		  {
			out.write("500 \r\n"); 
			out.write("That username already exists \r\n");
			out.flush();
			return;   
		  }
		  if (userDetails[1].equals(""))
		  {
			out.write("500 \r\n"); 
			out.write("invalid password \r\n");
			out.flush();
			return;  
		  }
		  else if (userDetails[1].length() <= 3)
		  {
			out.write("500 \r\n"); 
			out.write("Password too short \r\n");
			out.flush();
		  }
		  if (userDetails[2] != null)
		  {
			if (userDetails[2].matches("([0-9]{2})/([0-9]{2})/([0-9]{4})") == false)
			{
				out.write("500 \r\n"); 
				out.write("please enter a valid date format dd/mm/yyyy \r\n");
				out.flush();
				
			}
		  }
		  
		  if (userDetails[3].equals("") == false)
		  {
			if (userDetails[3].length() != 11)
			{
				out.write("500 \r\n"); 
				out.write("number must be 11 digits long");
				out.flush();
				
			}
		  }
		  
		  
		  
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
