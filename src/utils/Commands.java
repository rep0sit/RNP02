package utils;

public final class Commands {
	private Commands() {
	}
	
	//#################################################
	private static final String DEMAND = "<<"; // server demands something
	private static final String RESPONSE = ">>"; // information from server
	private static final String FORCE = ">>>>"; // server forces alteration of client
	//#################################################

	// COMMANDS FROM SERVER TO CLIENT (START WITH "<<")
	public static final String GIVE_USERNAME = DEMAND + "300";
	
	// SERVER COMMANDS THAT FORCE ALTERATION OF CLIENT (START WITH ">>>>");
	
	public static final String FORCE_USERNAME = FORCE + "600_";
	public static final String FORCE_DISCONNECT = FORCE + "601";
	
	// POSITIVE SERVER RESPONSES (START WITH ">>1")
	/**
	 * username is valid and not-taken
	 */
	public static final String VALID_USERNAME = RESPONSE + "100_";
	/**
	 * user is logged in
	 */
	public static final String LOGGED_IN = RESPONSE + "101_";
	/**
	 * user is in a specific room
	 */
	public static final String IN_ROOM = RESPONSE + "102_";

	// NEGATIVE SERVER RESPONSES (START WITH ">>0")
	/**
	 * username is invalid
	 */
	public static final String INVALID_USERNAME = RESPONSE + "000_";
	/**
	 * username is already taken
	 */
	public static final String USERNAME_TAKEN = RESPONSE + "001_";
	/**
	 * the server is already full
	 */
	public static final String SERVER_FULL = RESPONSE + "002_";

	// COMMANDS FROM CLIENT USER TO SERVER (START WITH "/")
	/**
	 * show the conditions for the login
	 */
	public static final String CONDITIONS = "/conditions";
	
	/**
	 * show a list of every usercommand
	 */
	public static final String SHOW_COMMANDS = "/showcommands";
	/**
	 * give server command to change to the following room
	 */
	public static final String GOTO_ROOM = "/gotoroom ";
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
	
	public static final boolean messageAllowed(String message) {
		return !(message.startsWith(DEMAND) || message.startsWith(RESPONSE) || message.startsWith(FORCE));
	}

}
