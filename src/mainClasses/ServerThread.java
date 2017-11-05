package mainClasses;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import utils.Constants;

public final class ServerThread extends AbstractClientServerThread{
	
	private List<String> userNames = new ArrayList<>();

	
	private ServerSocket serverSocket;
	
	
	
	public ServerThread(int port) {
		this.port = port;
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ServerThread() {
		this(Constants.SERVER_STANDARD_PORT);
	}
	
	
	
	@Override
	public void run() {
		selfMessage("Server running on port ", Integer.toString(port));
		
		while(!terminated) {
			try {
				socket = serverSocket.accept();
				selfMessage("Login Attempt: ", "IP = ", socket.getInetAddress().toString());
			
				new ClientLoginThread(socket, userNames);
				
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String...args) {
		
	
		ServerThread st = new ServerThread();
		st.start();
	}

	
	
}
