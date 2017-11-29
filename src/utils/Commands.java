package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Commands {
	private Commands() {
	}
	
	//#################################################
//	private static final String DEMAND = "<<"; // server demands something
//	private static final String RESPONSE = ">>"; // information from server
//	private static final String FORCE = ">>>>"; // server forces alteration of client
	
	private static final String SERVER_COMMAND_PREFIX = ">>>>";
	//#################################################

	// COMMANDS FROM SERVER TO CLIENT (START WITH "<<")
	public static final String GIVE_USERNAME = SERVER_COMMAND_PREFIX + "300";
	
	// SERVER COMMANDS THAT FORCE ALTERATION OF CLIENT (START WITH ">>>>");
	
	//public static final String FORCE_USERNAME = FORCE + "600_";
	public static final String FORCE_DISCONNECT = SERVER_COMMAND_PREFIX + "601_";
	
	// POSITIVE SERVER RESPONSES (START WITH ">>1")
	/**
	 * username is valid and not-taken
	 */
	public static final String VALID_USERNAME = SERVER_COMMAND_PREFIX + "100_";
	/**
	 * user is logged in
	 */
	public static final String LOGGED_IN = SERVER_COMMAND_PREFIX + "101_";
	/**
	 * user is in a specific room
	 */
	public static final String IN_ROOM = SERVER_COMMAND_PREFIX + "102_";

	// NEGATIVE SERVER RESPONSES (START WITH ">>0")
	/**
	 * username is invalid
	 */
	public static final String INVALID_USERNAME = SERVER_COMMAND_PREFIX + "000_";
	/**
	 * username is already taken
	 */
	public static final String USERNAME_TAKEN = SERVER_COMMAND_PREFIX + "001_";
	/**
	 * the server is already full
	 */
	public static final String SERVER_FULL = SERVER_COMMAND_PREFIX + "002_";

	// COMMANDS FROM CLIENT USER TO SERVER (START WITH "/")
	/**
	 * show the conditions for the login
	 */
	//public static final String CONDITIONS = "/conditions";
	
	
	
	/**
	 * show a list of every usercommand
	 */
	public static final String SHOW_COMMANDS = "/commands";
	/**
	 * give server command to change to the following room
	 */
	public static final String GOTO_ROOM = "/goto ";
	/**
	 * whisper mode with following user
	 */
	public static final String WHISPER = "/whisper ";
	
	/**
	 * talk to every user in the room
	 */
	public static final String UN_WHISPER = "/unwhisper";

	/**
	 * give a list of all active users
	 */
	public static final String USERS = "/users";
	/**
	 * give a list of all active rooms
	 */
	public static final String ROOMS = "/rooms";
	
	public static final String LOG = "/log";
	/**
	 * quit the chat session and terminates all streams and socket connection
	 */
	public static final String QUIT = "/quit";
	
	// SERVER ADMIN BEFEHLE
	
	public static final String STOP = "/stop";
	
	public static final String KICK_USER = "/kick ";
	
	public static final List<String> USER_COMMANDS = new ArrayList<>(Arrays.asList(
			SHOW_COMMANDS + " (shows all commands)",
			GOTO_ROOM + "<designated room> (go to designated room)",
			WHISPER + "<user> (whisper to user)",
			UN_WHISPER + " (talk to everyone in the room)",
			USERS + " (shows all users in this room)",
			QUIT + " (terminates the chat session)",
			ROOMS + " (shows all rooms)"));
	
	public static final boolean messageAllowed(String message) {
		return !(message.startsWith(SERVER_COMMAND_PREFIX)); //|| message.startsWith(RESPONSE) || message.startsWith(FORCE));
	}

	
}
