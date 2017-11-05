package mainClasses;

import java.net.Socket;
import java.util.List;

import utils.Commands;
import utils.Constants;

final class ClientLoginThread extends AbstractWriteThread {

	
	private List<String> userNames;
	
	private String userName = "";
	
	public ClientLoginThread(Socket socket, List<String> userNames) {
		this.socket = socket;
		this.userNames = userNames;
		this.setDaemon(true);
		start();
	}

	private void login() {
		if(userNames.size() == Constants.MAX_CLIENT_THREADS) {
			write(Commands.SERVER_FULL);
		}
		else {
			
			write(Commands.GIVE_USERNAME);
			
			if(userNames.contains(userName)) {
				write(Commands.USERNAME_TAKEN);
			}
			else if(!Helpers.validateName(userName)) {
				write(Commands.INVALID_USERNAME);
			}
		}
		


		
	}
	
	public void run() {
		login();
	}

	
}
