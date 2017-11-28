package mainClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gui.ServerGui;
import utils.Commands;
import utils.Constants;

public final class ServerThread extends AbstractClientServerThread{
	
	public static final String LOBBY = "lobby";
	public static final String MUSIC = "music";
	public static final String GOSSIP = "gossip";
	
	
	public static List<String> rooms = new ArrayList<>(Arrays.asList(LOBBY, MUSIC, GOSSIP));
	public static List<String> completeLog = new ArrayList<>();
	
	//private List<String> userNames = new ArrayList<>();
	private List<UserThread> users = new ArrayList<>();
	
	
	
	private ServerSocket serverSocket;
	
	private ServerGui serverGui;
	
	public ServerThread(int port) {
		this.port = port;
		try {
			this.serverSocket = new ServerSocket(port);
			br = new BufferedReader(new InputStreamReader(System.in));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public ServerThread(ServerGui gui){
		this(Constants.SERVER_STANDARD_PORT);
		serverGui = gui;
		serverGui.writeToConsole("Server is running on port "+Constants.SERVER_STANDARD_PORT);
	}
	public ServerThread() {
		this(Constants.SERVER_STANDARD_PORT);
	}
	
	public void disconnect() {
		try {
			br.close();
			serverSocket.close();
			serverGui.dispose();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(-1);
	}
	
	static synchronized void addToLog(String message) {
		completeLog.add(message);
	}
	static void printLog() {
		System.out.println("COMPLETE LOG: ");
		System.out.println("################################");
		for(String line : completeLog) {
			System.out.println(line);
		}
		System.out.println("################################");
	}
	
	
	@Override
	public void run() {
		selfMessage("Server running on port ", Integer.toString(port));
		new ConsoleReader();
		while(!terminated) {
			try {
				socket = serverSocket.accept();
				selfMessage("Login Attempt: ", "IP = ", socket.getInetAddress().toString());
				
				if(users.size() >= Constants.MAX_CLIENT_THREADS) {
					pw = new PrintWriter(socket.getOutputStream());
					write(Commands.SERVER_FULL + "Sorry, Server is Full.");
					pw.close();
				}else {
//					new ClientLoginThread(socket, users);
					new ClientLoginThread(socket, users, serverGui);
				}
				
				
				
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String...args) {
		int port;
		ServerThread st = new ServerThread();
		if(args.length == 1) {
			port = Integer.parseInt(args[0]);
			st = new ServerThread(port);
			
		}
		
		st.start();
	}

	class ConsoleReader extends Thread{
		
		ConsoleReader(){
			this.setDaemon(true);
			start();
		}
		@Override
		public void run() {
			String command = "";
			while(true) {
				try {
					command = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(command.equals(Commands.STOP)) {
					disconnect();
				}
				
				else if(command.startsWith(Commands.KICK_USER)) {
					String user = command.substring(Commands.KICK_USER.length());
					for(UserThread u : users) {
						if (u.getUserName().equals(user)) {
							u.kick();
							users.remove(u);
							serverGui.writeToConsole("User "+u.getName()+" was kicked.");
						}
						
					}
				}
				else if(command.equals(Commands.LOG)) {
					printLog();
				}
				
				else if(command.equals(Commands.USERS)) {
					StringBuilder sb = new StringBuilder();
					for(UserThread u : users) {
						sb.append("[").append(u).append("]");
					}
					selfMessage(sb.toString());
				}
				else if(command.equals(Commands.QUIT)){
					disconnect();
				}
			}
		}
	}
	
}
