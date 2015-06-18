package game6.server;

import game6.core.faction.Faction;
import game6.core.faction.Player;
import game6.core.networking.PacketList;
import game6.core.world.WorldGenerator;
import game6.server.world.ServerWorld;

import java.net.BindException;

import de.nerogar.network.Connection;
import de.nerogar.network.ServerThread;

public class Server {

	private ServerThread serverThread;
	private TickTimer timer = new TickTimer(10); // Server ticks per second

	private ServerWorld world;

	public Server(int port) {

		if (initServer(port)) {
			System.out.println("Started server at port " + port);
			start();
		}

	}

	private boolean initServer(int port) {

		// init the Server Socket
		try {
			serverThread = new ServerThread(port);
		} catch (BindException e) {
			System.err.println("Could not start server. Port binding failed");
			e.printStackTrace();
			return false;
		}

		return true;

	}

	private void start() {

		PacketList.init();
		world = WorldGenerator.getWorld(0, 100, 100);

		timer.start();

		// Start mainloop
		while (true) {

			update();
			timer.nextTickDelay();

		}

	}

	private void update() {

		for (Connection conn : serverThread.getNewConnections()) {
			Player player = new Player(conn);
			// TODO don't add player to random faction
			Faction.getRandom().addPlayer(player);
			world.initNewPlayer(player);
		}

		world.update(1f / timer.TICKRATE);
		Faction.updateAll();

	}

}
