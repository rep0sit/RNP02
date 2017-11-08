package mainClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import utils.Commands;
import utils.Constants;

final class ClientLoginThread extends AbstractWriteThread {

	
	private List<UserThread> users;
	
	private String userName;
	
	public ClientLoginThread(Socket socket, List<UserThread> users) {
		this.socket = socket;
		this.users = users;
		this.setDaemon(true);
		init();
		start();
	}
	

	private void init() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private String askUserName() {
		String ret = null;
		String givenName = "";
		boolean taken = false;
		
		boolean firstAttempt = true;
		for(int i = 1; i <= Constants.MAX_NAME_ATTEMPTS; i++ ) {
			
			if(firstAttempt) {
				write(Commands.GIVE_USERNAME);
				firstAttempt = false;
			}
			
			try {

				givenName = br.readLine();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			for(UserThread u : users) {
				if(u.getUserName().equals(givenName)) {
					taken = true;
				}
			}
			
			if(taken) {
				write(Commands.USERNAME_TAKEN);
			}
			else if(!Helpers.validateName(givenName)) {
				write(Commands.INVALID_USERNAME + "Username must be between " + 
						Constants.USERNAME_MIN_LEN + " and " + Constants.USERNAME_MAX_LEN + 
						" characters long.");
			}
			else {
				ret = givenName;
				break;
			}
		}
		
		
		return ret;
	}
	
	
	private void login() {

		userName = askUserName();

		if (userName == null) {
			write(Commands.FORCE_DISCONNECT + "you entered an invalid username for "
					+ Integer.toString(Constants.MAX_NAME_ATTEMPTS) + " times.");
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		else {

			if (users.size() >= Constants.MAX_CLIENT_THREADS) {
				write(Commands.SERVER_FULL + "Sorry, Server is full now.");
			} else {
				write(Commands.VALID_USERNAME);
				
				
				users.add(new UserThread(userName, socket, users));
			
				write(Commands.LOGGED_IN);
				write("####################################################");
				write("You are in the lobby now.");
				write("Type in " + Commands.SHOW_COMMANDS + " for a list of all commands.");
				write("Have fun and be nice.");
				write("####################################################");
				
			}

			
		}

	}
	
	@Override
	public void run() {
		login();
	}

	
}
