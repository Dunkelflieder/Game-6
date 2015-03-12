package game6.server.world;

import game6.core.buildings.*;
import game6.core.entities.CoreEntity;
import game6.core.entities.EntityType;
import game6.core.faction.Faction;
import game6.core.faction.Player;
import game6.core.networking.PacketList;
import game6.core.networking.packets.*;
import game6.core.world.CoreWorld;
import game6.core.world.Map;
import game6.server.buildings.Constructionsite;
import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.network.packets.Packet;
import de.nerogar.util.Vector3f;

public class World extends CoreWorld {

	public World(Map map) {
		super(map);
	}

	@Override
	public void update(float timeDelta) {
		// check for building placement request.
		// TODO this is sample code btw.
		for (Faction faction : Faction.values()) {
			for (Packet packet : faction.get(PacketList.BUILDINGS)) {
				if (packet instanceof PacketPlaceBuilding) {
					// TODO this packet is not used for buildings anymore. Use PacketStartConstruction instead
					PacketPlaceBuilding ppb = (PacketPlaceBuilding) packet;
					CoreBuilding building = ppb.building.getServerBuilding();
					building.setFaction(faction);
					if (getMap().canAddBuilding(ppb.posX, ppb.posY, building)) {
						addBuilding(ppb.posX, ppb.posY, building);
						Faction.broadcastAll(new PacketPlaceBuilding(ppb.building, faction, building.getID(), building.getPosX(), building.getPosY()));
					}
				} else if (packet instanceof PacketStartConstruction) {
					PacketStartConstruction psc = (PacketStartConstruction) packet;
					CoreBuilding building = psc.building.getServerBuilding();
					building.setFaction(faction);

					if (getMap().canAddBuilding(psc.posX, psc.posY, building)) {
						// wrap in construction
						CoreConstructionsite constructionsite = new Constructionsite(building, psc.building.getBuildingCost());
						addBuilding(psc.posX, psc.posY, constructionsite);
						Faction.broadcastAll(new PacketStartConstruction(psc.building, faction, building.getID(), building.getPosX(), building.getPosY()));
					}
				}
			}
			for (Packet packet : faction.get(PacketList.ENTITIES)) {
				if (packet instanceof PacketSpawnEntity) {
					PacketSpawnEntity pse = (PacketSpawnEntity) packet;
					CoreEntity entity = pse.entity.getServerEntity();
					entity.setFaction(faction);
					if (canAddEntity(pse.position, entity)) {
						pse.position.setY(entity.isFlying() ? 2 : 0);
						spawnEntity(entity, pse.position);
						Faction.broadcastAll(new PacketSpawnEntity(pse.entity, faction, entity.getID(), entity.getPosition()));
						entity.move(new Vector3f(1, entity.getPosition().getY(), 1));
					}
				} else if (packet instanceof PacketEntityGoalChanged) {
					PacketEntityGoalChanged pegc = (PacketEntityGoalChanged) packet;
					CoreEntity entity = (CoreEntity) getEntityList().getEntity(pegc.id);
					// TODO check if movement is valid
					entity.move(pegc.goal);
				} else if (packet instanceof PacketCombatTargetSet) {
					PacketCombatTargetSet pcts = (PacketCombatTargetSet) packet;
					CoreEntity sourceEntity = (CoreEntity) getEntityList().getEntity(pcts.sourceID);
					if (pcts.targetType == PacketCombatTargetSet.ENTITIY) {
						CoreEntity targetEntity = (CoreEntity) getEntityList().getEntity(pcts.targetID);
						sourceEntity.attack(targetEntity.getFightingObject());
					}

				}
			}
		}

		super.update(timeDelta);
	}

	public boolean canAddEntity(Vector3f position, CoreEntity entity) {
		return position.getX() >= 0 && position.getY() >= 0 && position.getX() < getMap().getSizeX() && position.getY() < getMap().getSizeY();
	}

	/**
	 * Send a freshly connected player all needed data (the map, buildings, entities etc.)
	 * @param player Player-Object
	 */
	public void initNewPlayer(Player player) {
		player.getConnection().send(new PacketMap(getMap()));

		for (CoreBuilding building : getBuildings()) {
			player.getConnection().send(new PacketPlaceBuilding(BuildingType.fromServerClass(building.getClass()), building.getFaction(), building.getID(), building.getPosX(), building.getPosY()));
		}

		for (BaseEntity<Vector3f> entity : getEntityList().getEntities()) {
			CoreEntity coreEntity = (CoreEntity) entity;
			player.getConnection().send(new PacketSpawnEntity(EntityType.fromServerClass(coreEntity.getClass()), coreEntity.getFaction(), coreEntity.getID(), coreEntity.getPosition()));
		}
	}

}
