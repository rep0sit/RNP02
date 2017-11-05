package mainClasses;

import java.io.IOException;

abstract class AbstractClientServerThread extends AbstractWriteThread {
	
	protected boolean terminated = false;
	
	protected int port;
	
	protected void selfMessage(String message) {
		System.out.println(message);
	}

	protected void selfMessage(String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String m : messages) {
			sb.append(m);
		}
		selfMessage(sb.toString());
	}
	
	
	public void terminate() {
		terminated = true;
		try {
	
			br.close();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
}
