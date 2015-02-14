package game6.server.world;

import game6.core.buildings.BuildingType;
import game6.core.buildings.CoreBuilding;
import game6.core.events.Event;
import game6.core.faction.Faction;
import game6.core.networking.Connection;
import game6.core.networking.PacketChannel;
import game6.core.networking.packets.*;
import game6.core.world.CoreMap;

import java.util.ArrayList;
import java.util.List;

public class Map extends CoreMap {

	private List<Player> players;

	public Map(CoreMap core) {
		super(core.getTiles());
		for (CoreBuilding building : core.getBuildings()) {
			addBuilding(building.getPosX(), building.getPosY(), building);
		}
		this.players = new ArrayList<>();
	}

	public void update() {

		// check for building placement request.
		// TODO this is sample code btw.
		for (Player player : players) {
			for (Packet packet : player.getConnection().get(PacketChannel.BUILDINGS)) {
				if (packet instanceof PacketPlaceBuilding) {
					PacketPlaceBuilding ppb = (PacketPlaceBuilding) packet;
					CoreBuilding building = ppb.building.getServerBuilding();
					building.setFaction(player.getFaction());
					if (canAddBuilding(ppb.posX, ppb.posY, building)) {
						addBuilding(ppb.posX, ppb.posY, building);
						broadcast(new PacketPlaceBuilding(ppb.building, player.getFaction(), building.getID(), building.getPosX(), building.getPosY()));
					}
				}
			}
		}

		List<Event> events = new ArrayList<>();

		for (CoreBuilding building : getBuildings()) {
			building.update(events);
		}

		List<Packet> packets = new ArrayList<>();
		for (Event e : events) {
			e.process(players);
		}

		for (Packet p : packets) {
			broadcast(p);
		}
	}

	public void addPlayer(Connection connection) {
		connection.send(new PacketMap(this));
		Faction faction = Faction.getRandom();
		connection.send(new PacketPlayerInfo(faction));
		for (CoreBuilding building : getBuildings()) {
			connection.send(new PacketPlaceBuilding(BuildingType.fromServerClass(building.getClass()), building.getFaction(), building.getID(), building.getPosX(), building.getPosY()));
		}
		players.add(new Player(connection, faction));
	}

	public List<Player> getPlayers() {
		return players;
	}

	private void broadcast(Packet packet) {
		for (Player player : players) {
			player.getConnection().send(packet);
		}
	}

}
