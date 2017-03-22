package msgServer;
import java.io.BufferedReader;
import java.io.BufferedWriter; 
import java.io.IOException;

public class GetAllMsgsCommand implements Command 
{
  private BufferedReader in;
  private BufferedWriter out;
  private MsgSvrConnection conn;

  public GetAllMsgsCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn)
  { 
    this.in = in;
    this.out = out;
    this.conn = serverConn;
  }

  public void execute() throws IOException
  {
	//declare a variable user of type String and use it to get the user from the inputstream
    String user = in.readLine();
        //check if current user is not equal to null and current user is equal to the user (use the method getCurrentUser())
      String currentUser = conn.getCurrentUser();
      if(currentUser != null){
    	  if(currentUser.equals(user)){
	    	  Message[] messages = null;
	    	  messages = conn.getServer().getMessages().getAllMessages(user);
	    	  if(messages != null){
	    		  int responseCode = 200 + messages.length;
	    		  out.write(Integer.toString(responseCode) + "\r\n");
	    		  for(Message message : messages){
	    			  out.write(message.getSender() + "\r\n");
	    			  out.write(message.getDate() + "\r\n");
	    			  out.write(message.getContent() + "\r\n");
	    		  }
	    		  out.flush();
	    	  }
	    	  else{
	    		  out.write("200" + "\r\n");
	        	  out.write("No Messages" + "\r\n");
	    		  out.flush();
	    	  }
    	  }
    	  else{
        	  out.write("500" + "\r\n");
        	  out.write("Incorrect User" + "\r\n");
    		  out.flush();
    		  
    	  }
      }
      else{
    	  out.write("500" + "\r\n");
    	  out.write("You are not logged on" + "\r\n");
		  out.flush();
      }
        //intialise an array (msgs) that is used to hold all the messages read and set it's initialised value to null 
         
        //use the method getAllMessages(user) to populate the msgs array
        
        //check if msgs is not equal to null 
          
        //write to the OutputStream the message status code as specified in the protocol   
            
        //write the length of the messages returned
            
        //Loop through the messages and write the sender, date and cotent to the outputstream (use provided methods) 
          
        //Flush the outputstream
           
        //capture adequate errors (No messages) or (You are not logged on)
          
  }
}