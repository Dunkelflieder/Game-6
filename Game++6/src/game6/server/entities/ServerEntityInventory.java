package game6.server.entities;

import game6.core.interfaces.ResourceContainer;
import game6.core.networking.packets.entities.PacketEntityUpdateInventory;

public interface ServerEntityInventory extends ResourceContainer, ServerEntity {

	@Override
	default public void resourceContentChanged() {
		getFaction().broadcast(new PacketEntityUpdateInventory(getID(), this));
	}

}
