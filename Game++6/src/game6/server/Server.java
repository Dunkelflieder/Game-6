package game6.server;

import game6.core.networking.ServerThread;
import game6.server.world.World;

import java.net.BindException;

public class Server {

	private ServerThread serverThread;
	private TickTimer timer = new TickTimer(10); // Server ticks per second

	private World world;
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
			System.exit(-1);
		}

	}

	private void start() {

		world = new World();
		controller = new Controller(world, serverThread);

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
