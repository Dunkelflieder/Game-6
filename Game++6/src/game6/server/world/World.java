package game6.server.world;

import game6.core.events.Event;
import game6.core.networking.Connection;
import game6.core.networking.PacketChannel;
import game6.core.networking.packets.Packet;
import game6.core.networking.packets.PacketPlaceBuilding;
import game6.core.world.MapGenerator;
import game6.server.buildings.BaseBuilding;

import java.util.ArrayList;
import java.util.List;

public class World {

	private Map map;
	private List<Player> players;

	public World() {
		this.map = new Map(MapGenerator.getMap(100, 100));
		this.players = new ArrayList<>();
	}

	public void update(List<Event> events) {

		// check for building placement request. Sample code btw.
		for (Player player : players) {
			for (Packet packet : player.getConnection().get(PacketChannel.BUILDINGS)) {
				if (packet instanceof PacketPlaceBuilding) {
					PacketPlaceBuilding ppb = (PacketPlaceBuilding) packet;
					BaseBuilding building = ppb.building.getServerBuilding(map.getBuildings().size());
					if (map.canAddBuilding(ppb.posX, ppb.posY, building)) {
						map.addBuilding(ppb.posX, ppb.posY, building);
						broadcast(new PacketPlaceBuilding(ppb.building, building.getID(), building.getPosX(), building.getPosY()));
					}
				}
			}
		}
		
		map.update(events);
		
	}

	public Map getMap() {
		return map;
	}

	public void addPlayer(Connection connection) {
		players.add(new Player(connection));
	}

	private void broadcast(Packet packet) {
		for (Player player : players) {
			player.getConnection().send(packet);
		}
	}

}
