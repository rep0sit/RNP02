package mainClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import utils.Commands;
import utils.Constants;

/**
 * 
 * @author Nelli Welker, Etienne Onasch
 *
 */
public final class ClientThread extends AbstractClientServerThread {

	private String serverIp;
	private String name;
	private InetAddress inetAddress;
	
	

	// state
	private boolean loggedIn = false;
	
	
	private int timeout = Constants.TIMEOUT_5SEC;
	
	public ClientThread(String name, String serverIp, int port) {
		this.name = name;
		this.serverIp = serverIp;
		this.port = port;
		try {
			this.inetAddress = InetAddress.getByName(serverIp);
		} catch (UnknownHostException e) {
			selfMessage("serverIp couldn't be resolved into a valid internet address.");
			e.printStackTrace();
		}

	}
	
	private void init() {

		try {
			
			socket = new Socket();
			socket.connect(new InetSocketAddress(inetAddress, port), timeout);
			
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream());
		} 
		catch (SocketTimeoutException e) {

			e.printStackTrace();
		}
		
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		selfMessage("ClientThread initialized.");
	}
	
	
	
	

	/**
	 * Writes a server response to the user invoking the message(String message)
	 * method.
	 * 
	 * @param command
	 *            a command from server to client defined in Commands.
	 * @param interpretation
	 *            the interpretation of the command.
	 * @param completeLine
	 *            the complete line the server has sended over the stream. (command
	 *            + optional message)
	 */
	private void selfMessageResponse(String command, String interpretation, String completeLine) {
		StringBuilder sb = new StringBuilder();
		sb.append("Command code: ").append(command);
		if (!interpretation.equals("")) {
			sb.append("(").append(interpretation).append(")");
		}
		if (completeLine.length() > command.length()) {
			sb.append("[optional server message: ").append(completeLine.substring(command.length())).append("]");
		}
		selfMessage(sb.toString());
	}

	/**
	 * Writes a server response to the user invoking the message(String message)
	 * method.
	 * 
	 * @param command
	 *            a command from server to client defined in Commands.
	 * @param completeLine
	 *            the complete line the server has sended over the stream. (command
	 *            + optional message)
	 */
	private void selfMessageResponse(String command, String completeLine) {
		selfMessageResponse(command, "", completeLine);
	}

	
	
	
	
	
	@Override
	public void run() {
		init();
		String currentLine;
		boolean invalidName = false;
		try {
			while ((currentLine = br.readLine()) != null && !terminated) {
				// server commands
				if (currentLine.equals(Commands.GIVE_USERNAME)) {
					write(name);
				}
				
				// forced state-alterating commands
				else if(currentLine.startsWith(Commands.FORCE_USERNAME)) {
					if(currentLine.length() > Commands.FORCE_USERNAME.length()) {
						String userName = currentLine.substring(Commands.FORCE_USERNAME.length());
						this.name = userName;
						selfMessage("Username was changed to ", userName);
					}
				}
				else if(currentLine.startsWith(Commands.FORCE_DISCONNECT)) {
					selfMessage("You were kicked from server.");
					terminate();
					break;
				}

				// negative server responses
				else if (currentLine.startsWith(Commands.USERNAME_TAKEN)) {
					selfMessageResponse(Commands.USERNAME_TAKEN, "username already taken", currentLine);
					invalidName = true;
				} 
				else if (currentLine.startsWith(Commands.INVALID_USERNAME)) {
					selfMessageResponse(Commands.INVALID_USERNAME, "invalid username", currentLine);
					invalidName = true;
				} 
				else if (currentLine.startsWith(Commands.SERVER_FULL)) {
					selfMessageResponse(Commands.SERVER_FULL, "server already full", currentLine);
				}

				// positive server responses
				else if (currentLine.startsWith(Commands.LOGGED_IN)) {
					loggedIn = true;
					selfMessageResponse(Commands.LOGGED_IN, "you are logged in now", currentLine);
				}

				else if (currentLine.startsWith(Commands.VALID_USERNAME)) {
					selfMessageResponse(Commands.VALID_USERNAME, currentLine);
				} 
				else if (currentLine.startsWith(Commands.IN_ROOM)) {
					selfMessageResponse(Commands.IN_ROOM, currentLine);
				}

				else {
					selfMessage(currentLine);
				}

				if (invalidName) {
					selfMessage("--> Enter new username.");
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String...args) throws IOException {
		if(args.length != 3) {
			System.out.println("3 arguments needed! <username>, <server ip> and <server port>.");
		}else {
			String username = args[0];
			String ip = args[1];
			String port = args[2];
			
			ClientThread ct = new ClientThread(username, ip, Integer.parseInt(port));
			ct.start();
			
		}
		
		
		
		
	}

	
	

}
