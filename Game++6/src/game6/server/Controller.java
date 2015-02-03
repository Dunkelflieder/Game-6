package game6.server;

import game6.core.networking.Connection;
import game6.core.networking.ServerThread;
import game6.core.networking.packets.PacketMap;
import game6.server.world.World;

import java.util.List;

public class Controller {

	private ServerThread server;
	private World world;

	public Controller(World world, ServerThread server) {
		this.world = world;
		this.server = server;
	}

	public void update() {

		// TODO the following is sample code. Actual Server logic goes here
		
		// Get joining clients
		List<Connection> conns = server.getNewConnections();

		// Send them the map
		if (conns.size() > 0) {
			PacketMap packet = new PacketMap(world.getMap().getCore());
			for (Connection conn : conns) {
				conn.send(packet);
			}
		}

		world.update();

	}

}
