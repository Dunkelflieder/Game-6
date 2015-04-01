package game6.core.networking.packets.entities;

import game6.core.buildings.CoreBuilding;
import game6.core.entities.CoreEntity;

public class PacketEntityDeliverJob extends PacketEntityAttackBuilding {

	public PacketEntityDeliverJob() {
	}

	public PacketEntityDeliverJob(CoreEntity entity, CoreBuilding target) {
		super(entity, target);
	}

}
