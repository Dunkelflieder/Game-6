package game6.server;

import game6.core.buildings.BuildingType;
import game6.core.buildings.CoreBuilding;
import game6.core.events.Event;
import game6.core.networking.Connection;
import game6.core.networking.ServerThread;
import game6.core.networking.packets.PacketMap;
import game6.core.networking.packets.PacketPlaceBuilding;
import game6.server.world.World;

import java.util.LinkedList;
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
			PacketMap packet = new PacketMap(world.getMap().getTiles());
			for (Connection conn : conns) {
				conn.send(packet);
				for (CoreBuilding building : world.getMap().getBuildings()) {
					PacketPlaceBuilding packetBuilding = new PacketPlaceBuilding(BuildingType.fromServerClass(building.getClass()), building.getID(), building.getPosX(), building.getPosY());
					conn.send(packetBuilding);
				}
				world.addPlayer(conn);
			}
		}

		LinkedList<Event> events = new LinkedList<>();

		world.update(events);

		while (!events.isEmpty()) {
			Event event = events.pop();
			event.doMap(events, world.getMap());
			event.doNetwork(events, server);
		}

	}
}
