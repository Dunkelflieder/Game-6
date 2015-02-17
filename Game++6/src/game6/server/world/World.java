package game6.server.world;

import game6.core.buildings.BuildingType;
import game6.core.buildings.CoreBuilding;
import game6.core.entities.CoreEntity;
import game6.core.entities.EntityType;
import game6.core.faction.Faction;
import game6.core.networking.PacketList;
import game6.core.networking.packets.*;
import game6.core.world.CoreWorld;
import game6.core.world.Map;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.engine.UpdateEvent;
import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.network.Connection;
import de.nerogar.network.packets.Packet;
import de.nerogar.util.Vector3f;

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
			for (Packet packet : player.getConnection().get(PacketList.BUILDINGS)) {
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
			for (Packet packet : player.getConnection().get(PacketList.ENTITIES)) {
				if (packet instanceof PacketSpawnEntity) {
					PacketSpawnEntity pse = (PacketSpawnEntity) packet;
					CoreEntity entity = pse.entity.getServerEntity();
					entity.setFaction(player.getFaction());
					if (canAddEntity(pse.position, entity)) {
						pse.position.setY(entity.isFlying() ? 2 : 0);
						spawnEntity(entity, pse.position);
						broadcast(new PacketSpawnEntity(pse.entity, player.getFaction(), entity.getID(), entity.getPosition()));
						entity.move(new Vector3f(1, entity.getPosition().getY(), 1));
					}
				}
			}
		}

		return super.update(timeDelta);
	}

	public boolean canAddEntity(Vector3f position, CoreEntity entity) {
		return position.getX() >= 0 && position.getY() >= 0 && position.getX() < getMap().getSizeX() && position.getY() < getMap().getSizeY();
	}

	public void addPlayer(Connection connection) {
		connection.send(new PacketMap(getMap()));
		Faction faction = Faction.getRandom();
		connection.send(new PacketPlayerInfo(faction));
		for (CoreBuilding building : getBuildings()) {
			connection.send(new PacketPlaceBuilding(BuildingType.fromServerClass(building.getClass()), building.getFaction(), building.getID(), building.getPosX(), building.getPosY()));
		}
		for (BaseEntity<Vector3f> entity : getEntityList().getEntities()) {
			CoreEntity coreEntity = (CoreEntity) entity;
			connection.send(new PacketSpawnEntity(EntityType.fromServerClass(coreEntity.getClass()), coreEntity.getFaction(), coreEntity.getID(), coreEntity.getPosition()));
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
