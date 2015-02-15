package game6.server;

import game6.core.world.WorldGenerator;
import game6.server.world.World;

import java.net.BindException;

import de.nerogar.network.Connection;
import de.nerogar.network.ServerThread;

public class Server {

	private ServerThread serverThread;
	private TickTimer timer = new TickTimer(10); // Server ticks per second

	private World world;

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

		world = WorldGenerator.getWorld(0, 1000, 1000);

		timer.start();

		// Start mainloop
		while (true) {

			update();
			timer.nextTickDelay();

		}

	}

	private void update() {

		for (Connection conn : serverThread.getNewConnections()) {
			world.addPlayer(conn);
		}

		world.update(0);

	}

}
