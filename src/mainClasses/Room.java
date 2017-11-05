package mainClasses;

class Room{
	
	private final String name;
	
	
	public Room(String name) {
		this.name = name;
	}
	
	
	public synchronized String getRoomName() {
		return name;
	}
	
	
	
	
}
