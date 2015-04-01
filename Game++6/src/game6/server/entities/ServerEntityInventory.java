package game6.server.entities;

import game6.core.interfaces.ResourceContainer;
import game6.core.networking.packets.PacketUniqueID;
import game6.core.networking.packets.entities.PacketEntityDeliverJob;
import game6.core.networking.packets.entities.PacketEntityUpdateInventory;
import game6.server.buildings.ServerBuilding;
import game6.server.buildings.ServerBuildingInventory;
import game6.server.entities.jobs.JobDeliver;

public interface ServerEntityInventory extends ResourceContainer, ServerEntity {

	@Override
	default public void process(PacketUniqueID packet) {
		ServerEntity.super.process(packet);
		if (packet instanceof PacketEntityDeliverJob) {
			PacketEntityDeliverJob p = (PacketEntityDeliverJob) packet;
			ServerBuilding building = getWorld().getBuilding(p.targetID);
			if (building instanceof ServerBuildingInventory) {
				setJob(new JobDeliver(this, ((ServerBuildingInventory) building)));
			}
		}
	}

	@Override
	default public void resourceContentChanged() {
		getFaction().broadcast(new PacketEntityUpdateInventory(getID(), this));
	}

}
