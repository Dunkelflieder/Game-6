package game6.server.entities;

import game6.core.combat.ICombat;
import game6.core.faction.Faction;
import game6.core.networking.packets.PacketUniqueID;
import game6.core.networking.packets.entities.*;
import game6.server.buildings.ServerBuilding;

public interface ServerEntityCombat extends ICombat, ServerEntity {

	@Override
	default public void notifyDamage() {
		Faction.broadcastAll(new PacketEntityUpdateHealth(getCombatTarget()));
	}

	@Override
	default public void process(PacketUniqueID packet) {
		ServerEntity.super.process(packet);
		if (packet instanceof PacketEntityAttackEntity) {
			ServerEntity entity = getWorld().getEntity(((PacketEntityAttackEntity) packet).targetID);
			attack(entity);
		} else if (packet instanceof PacketEntityAttackBuilding) {
			ServerBuilding building = getWorld().getBuilding(((PacketEntityAttackBuilding) packet).targetID);
			attack(building);
		}
	}

}
