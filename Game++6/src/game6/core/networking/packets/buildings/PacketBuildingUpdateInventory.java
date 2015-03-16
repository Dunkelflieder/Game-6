package game6.core.networking.packets.buildings;

import game6.core.interfaces.DefaultResourceContainer;
import game6.core.interfaces.ResourceContainer;
import game6.core.networking.packets.PacketUniqueID;
import game6.core.util.Resource;

import java.nio.ByteBuffer;

public class PacketBuildingUpdateInventory extends PacketUniqueID {

	public ResourceContainer resources;

	public PacketBuildingUpdateInventory() {
	}

	public PacketBuildingUpdateInventory(long id, ResourceContainer resources) {
		super(id);
		this.resources = resources;
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		id = buffer.getLong();
		resources = new DefaultResourceContainer();
		resources.setCapacity(buffer.getInt());
		for (Resource resource : Resource.values()) {
			resources.setResource(resource, buffer.getInt());
		}
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(8 + 4 + 4 * Resource.values().length);
		buffer.putLong(id);
		buffer.putInt(resources.getCapacity());
		for (Resource resource : Resource.values()) {
			buffer.putInt(resources.getResource(resource));
		}
		return buffer.array();
	}

}
