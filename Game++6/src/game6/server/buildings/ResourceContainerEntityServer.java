package game6.server.buildings;

import game6.core.interfaces.ResourceContainer;
import game6.core.networking.packets.entities.PacketEntityUpdateInventory;
import game6.server.entities.ServerEntity;

public interface ResourceContainerEntityServer extends ResourceContainer, ServerEntity {

	@Override
	default public void resourceContentChanged() {
		getFaction().broadcast(new PacketEntityUpdateInventory(getID(), this));
	}

}
