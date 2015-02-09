package game6.server;

import game6.core.networking.Connection;
import game6.core.networking.ServerThread;
import game6.server.world.Map;

public class Controller {

	private ServerThread server;
	private Map map;

	public Controller(Map map, ServerThread server) {
		this.map = map;
		this.server = server;
	}

	public void update() {

		// Get joining clients
		for (Connection conn : server.getNewConnections()) {
			map.addPlayer(conn);
		}

		map.update();

	}
}
