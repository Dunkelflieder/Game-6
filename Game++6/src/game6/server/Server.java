package game6.server;

import game6.networking.ServerThread;

import java.net.BindException;

public class Server {

	private ServerThread serverThread;
	private TickTimer timer = new TickTimer(10); // Server ticks per second

	public Server(int port) {

		initServer(port);
		start();

	}

	private void initServer(int port) {

		// init the Server Socket
		try {
			serverThread = new ServerThread(port);
		} catch (BindException e) {
			System.err.println("Could not start server. Port binding failed");
			e.printStackTrace();
			System.exit(-1);
		}
		
	}

	private void start() {

		timer.start();
		
		// Start mainloop
		while (true) {

			update();
			timer.nextTickDelay();

		}

	}

	private void update() {

		System.out.println("updating");

	}

}
