package game6.client.buildings;

import game6.core.interfaces.ResourceContainer;
import game6.core.networking.packets.PacketUniqueID;
import game6.core.networking.packets.buildings.PacketBuildingUpdateInventory;

public interface ClientBuildingInventory extends ClientBuilding, ResourceContainer {

	@Override
	default public void process(PacketUniqueID packet) {
		ClientBuilding.super.process(packet);
		if (packet instanceof PacketBuildingUpdateInventory) {
			setResources(((PacketBuildingUpdateInventory) packet).resources);
			setCapacity(((PacketBuildingUpdateInventory) packet).resources.getCapacity());
		}
	}

}
