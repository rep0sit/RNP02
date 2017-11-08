package mainClasses;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.Commands;
import utils.Constants;

public final class ServerThread extends AbstractClientServerThread{
	
	public static final String LOBBY = "lobby";
	public static final String MUSIC = "music";
	public static final String GOSSIP = "gossip";
	
	
	public static List<String> rooms = new ArrayList<>(Arrays.asList(LOBBY, MUSIC, GOSSIP));
	
	
	//private List<String> userNames = new ArrayList<>();
	private List<UserThread> users = new ArrayList<>();
	
	
	
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
				
				if(users.size() >= Constants.MAX_CLIENT_THREADS) {
					pw = new PrintWriter(socket.getOutputStream());
					write(Commands.SERVER_FULL + "Sorry, Server is Full.");
					pw.close();
				}else {
					new ClientLoginThread(socket, users);
				}
				
				
				
			
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
