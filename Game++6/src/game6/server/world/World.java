package game6.server.world;

import game6.core.buildings.BuildingType;
import game6.core.buildings.CoreBuilding;
import game6.core.faction.Faction;
import game6.core.networking.PacketChannel;
import game6.core.networking.packets.*;
import game6.core.world.CoreWorld;
import game6.core.world.Map;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.engine.UpdateEvent;
import de.nerogar.network.Connection;
import de.nerogar.network.packets.Packet;

public class World extends CoreWorld {

	private List<Player> players;

	public World(Map map) {
		super(map);
		this.players = new ArrayList<>();
	}

	@Override
	public List<UpdateEvent> update(float timeDelta) {
		// check for building placement request.
		// TODO this is sample code btw.
		for (Player player : players) {
			for (Packet packet : player.getConnection().get(PacketChannel.BUILDINGS)) {
				if (packet instanceof PacketPlaceBuilding) {
					PacketPlaceBuilding ppb = (PacketPlaceBuilding) packet;
					CoreBuilding building = ppb.building.getServerBuilding();
					building.setFaction(player.getFaction());
					if (getMap().canAddBuilding(ppb.posX, ppb.posY, building)) {
						addBuilding(ppb.posX, ppb.posY, building);
						broadcast(new PacketPlaceBuilding(ppb.building, player.getFaction(), building.getID(), building.getPosX(), building.getPosY()));
					}
				}
			}
		}

		return super.update(timeDelta);
	}

	public void addPlayer(Connection connection) {
		connection.send(new PacketMap(getMap()));
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
