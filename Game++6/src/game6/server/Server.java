package game6.server;

import game6.core.networking.ServerThread;
import game6.core.world.MapGenerator;
import game6.server.world.Map;

import java.net.BindException;

public class Server {

	private ServerThread serverThread;
	private TickTimer timer = new TickTimer(10); // Server ticks per second

	private Controller controller;

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
			Thread.currentThread().interrupt();
		}

	}

	private void start() {

		Map map = new Map(MapGenerator.getMap((long) (Math.random() * Long.MAX_VALUE), 100, 100));
		controller = new Controller(map, serverThread);

		timer.start();

		// Start mainloop
		while (true) {

			update();
			timer.nextTickDelay();

		}

	}

	private void update() {

		controller.update();

	}

}
