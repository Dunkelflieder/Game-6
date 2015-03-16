package game6.core.networking.packets.entities;

import game6.core.interfaces.ResourceContainer;
import game6.core.networking.packets.buildings.PacketBuildingUpdateInventory;

public class PacketEntityUpdateInventory extends PacketBuildingUpdateInventory {

	public PacketEntityUpdateInventory() {
	}

	public PacketEntityUpdateInventory(long id, ResourceContainer resources) {
		super(id, resources);
	}
}
