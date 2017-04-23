package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class GetAllMsgsCommand implements Command {
	private BufferedReader in;
	private BufferedWriter out;
	private MsgSvrConnection conn;

	public GetAllMsgsCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn) {
		this.in = in;
		this.out = out;
		this.conn = serverConn;
	}

	public void execute() throws IOException {
		// declare a variable user of type String and use it to get the user
		// from the inputstream
		String user = in.readLine();
		// check if current user is not equal to null and current user is equal
		// to the user (use the method getCurrentUser())
		String currentUser = conn.getCurrentUser();
		if (currentUser == null) {
			(new ErrorCommand(in, out, conn, "Incorrect User")).execute();
			return;
		}
		if (!currentUser.equals(user)) {
			(new ErrorCommand(in, out, conn, "Incorrect User")).execute();
			return;
		}
		// intialise an array (msgs) that is used to hold all the messages read
		// and set it's initialised value to null
		Message[] messages = null;
		// use the method getAllMessages(user) to populate the msgs array
		messages = conn.getServer().getMessages().getAllMessages(user);
		// check if msgs is not equal to null
		if (messages == null) {
			(new ErrorCommand(in, out, conn, "No Messages")).execute();
			return;
		}
		// write to the OutputStream the message status code as specified in the
		// protocol
		out.write("" + MsgProtocol.MESSAGE + "\r\n");
		// write the length of the messages returned
		out.write(Integer.toString(messages.length) + "\r\n");
		// Loop through the messages and write the sender, date and content to
		// the outputstream (use provided methods)
		for (Message message : messages) {
			out.write(message.getSender() + "\r\n");
			out.write(message.getDate() + "\r\n");
			out.write(message.getContent() + "\r\n\r\n");
		}
		// Flush the outputstream
		out.flush();
		// capture adequate errors (No messages) or (You are not logged on)

	}
}