package game6.client.main;

import game6.server.Server;

public class Main {

	public static void main(String[] args) {

		// Start internal server
		Thread t = new Thread(() -> {
			new Server(34543);
		});
		t.setName("Serverthread");
		t.setDaemon(true);
		t.start();

		new Game().start();

	}

}
