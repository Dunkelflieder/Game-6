package game6.server.entities;

import game6.core.buildings.CoreBuilding;
import game6.core.combat.ICombat;
import game6.core.entities.CoreEntity;
import game6.core.faction.Faction;
import game6.core.networking.packets.PacketUniqueID;
import game6.core.networking.packets.buildings.PacketBuildingUpdateHealth;
import game6.core.networking.packets.entities.*;
import game6.server.buildings.ServerBuilding;

public interface ServerEntityCombat extends ICombat, ServerEntity {

	@Override
	default public void notifyDamage() {
		if (getCombatTarget() instanceof CoreEntity) {
			Faction.broadcastAll(new PacketEntityUpdateHealth(getCombatTarget()));
		} else if (getCombatTarget() instanceof CoreBuilding) {
			Faction.broadcastAll(new PacketBuildingUpdateHealth(getCombatTarget()));
		}
	}

	@Override
	default public void process(PacketUniqueID packet) {
		ServerEntity.super.process(packet);
		if (packet instanceof PacketEntityAttackEntity) {
			ServerEntity entity = getWorld().getEntity(((PacketEntityAttackEntity) packet).targetID);
			if (entity.getFaction() != getFaction()) {
				attack(entity);
			}
		} else if (packet instanceof PacketEntityAttackBuilding) {
			ServerBuilding building = getWorld().getBuilding(((PacketEntityAttackBuilding) packet).targetID);
			if (building.getFaction() != getFaction()) {
				attack(building);
			}
		}
	}

}
