package mainClasses;

import utils.Constants;

final class Helpers {
	private Helpers() {}
	
	public static boolean validateName(String name) {
		return name.length() >= Constants.USERNAME_MIN_LEN && 
				name.length() <= Constants.USERNAME_MAX_LEN; 
	}
	
	

}
