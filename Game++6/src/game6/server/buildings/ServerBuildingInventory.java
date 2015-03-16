package game6.server.buildings;

import game6.core.interfaces.ResourceContainer;
import game6.core.networking.packets.buildings.PacketBuildingUpdateInventory;

public interface ServerBuildingInventory extends ResourceContainer, ServerBuilding {

	@Override
	default public void resourceContentChanged() {
		getFaction().broadcast(new PacketBuildingUpdateInventory(getID(), this));
	}

}
