package game6.server.world;

import game6.core.buildings.BuildingType;
import game6.core.entities.*;
import game6.core.faction.Faction;
import game6.core.faction.Player;
import game6.core.networking.PacketList;
import game6.core.networking.packets.*;
import game6.core.world.CoreWorld;
import game6.core.world.Map;
import game6.server.buildings.Constructionsite;
import game6.server.buildings.ServerBuilding;
import game6.server.entities.ServerEntity;

import java.util.List;

import de.nerogar.network.packets.Packet;
import de.nerogar.util.Vector3f;

public class World extends CoreWorld<ServerBuilding, ServerEntity> {

	public World(Map<ServerBuilding> map) {
		super(map);
	}

	@Override
	public void update(float timeDelta) {
		super.update(timeDelta);

		// check for building placement request.
		// TODO this is sample code btw.
		for (Faction faction : Faction.values()) {
			for (Packet packet : faction.getPackets(PacketList.WORLD)) {
				if (packet instanceof PacketSpawnEntity) {
					PacketSpawnEntity pse = (PacketSpawnEntity) packet;
					ServerEntity entity = pse.entity.getServerEntity();
					entity.setFaction(faction);
					if (canAddEntity(pse.position, entity)) {
						entity.teleport(pse.position);
						entity.getPosition().setY(entity instanceof MovementAir ? 2 : 0);
						addEntity(entity);
						Faction.broadcastAll(new PacketSpawnEntity(pse.entity, faction, entity.getID(), entity.getPosition()));
						entity.move(new MoveTargetPosition(entity, new Vector3f(1, entity.getPosition().getY(), 1)));
					}
				} else if (packet instanceof PacketStartConstruction) {
					PacketStartConstruction psc = (PacketStartConstruction) packet;
					ServerBuilding building = psc.building.getServerBuilding();
					building.setFaction(faction);

					if (getMap().canAddBuilding(psc.posX, psc.posY, building)) {
						// wrap in construction
						Constructionsite constructionsite = new Constructionsite(building, psc.building.getBuildingCost());
						addBuilding(psc.posX, psc.posY, constructionsite);
						Faction.broadcastAll(new PacketStartConstruction(psc.building, faction, building.getPosX(), building.getPosY(), building.getID()));
					}
				}

				// TODO reimplement combat
				/* else if (packet instanceof PacketCombatTargetSet) {
					PacketCombatTargetSet pcts = (PacketCombatTargetSet) packet;
					ServerEntity sourceEntity = (ServerEntity) getEntity(pcts.sourceID);
					if (pcts.targetType == PacketCombatTargetSet.ENTITIY) {
						ServerEntity targetEntity = (ServerEntity) getEntity(pcts.targetID);
						// TODO reimplement fighting
						// sourceEntity.attack(targetEntity.getFightingObject());
					}

				}*/
			}

			List<Packet> packets = faction.getPackets(PacketList.ENTITIES);
			for (Packet packet : packets) {
				PacketUniqueID packetEntity = (PacketUniqueID) packet;
				ServerEntity entity = getEntity(packetEntity.id);
				if (entity.getFaction() == faction) {
					getEntity(packetEntity.id).process(packetEntity);
				} else {
					System.out.println("HACKING!");
				}
			}

			packets = faction.getPackets(PacketList.BUILDINGS);
			for (Packet packet : packets) {
				PacketUniqueID packetBuilding = (PacketUniqueID) packet;
				ServerBuilding building = getBuilding(packetBuilding.id);
				if (building.getFaction() == faction) {
					building.process(packetBuilding);
				} else {
					System.out.println("HACKING!");
				}
			}
		}
	}

	@Override
	public void addBuilding(int posX, int posY, ServerBuilding building) {
		building.setWorld(this);
		super.addBuilding(posX, posY, building);
	}

	public boolean canAddEntity(Vector3f position, ServerEntity entity) {
		return position.getX() >= 0 && position.getY() >= 0 && position.getX() < getMap().getSizeX() && position.getY() < getMap().getSizeY();
	}

	/**
	 * Send a freshly connected player all needed data (the map, buildings, entities etc.)
	 * @param player Player-Object
	 */
	public void initNewPlayer(Player player) {
		player.getConnection().send(new PacketMap(getMap()));

		for (ServerBuilding building : getBuildings()) {
			if (building instanceof Constructionsite) {
				Constructionsite constructionsite = (Constructionsite) building;
				BuildingType type = BuildingType.fromServerClass(constructionsite.getBuilding().getClass());
				player.getConnection().send(new PacketStartConstruction(type, constructionsite.getFaction(), constructionsite.getPosX(), constructionsite.getPosY(), constructionsite.getID()));
			} else {
				BuildingType type = BuildingType.fromServerClass(building.getClass());
				player.getConnection().send(new PacketSpawnBuilding(type, building.getFaction(), building.getPosX(), building.getPosY(), building.getID()));
			}
		}

		for (ServerEntity entity : getEntities()) {
			ServerEntity serverEntity = (ServerEntity) entity;
			player.getConnection().send(new PacketSpawnEntity(EntityType.fromServerClass(serverEntity.getClass()), serverEntity.getFaction(), serverEntity.getID(), serverEntity.getPosition()));
		}
	}

	@Override
	public void addEntity(ServerEntity entity) {
		entity.setWorld(this);
		super.addEntity(entity);
	}

}
