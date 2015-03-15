package game6.core.networking.packets.buildings;

import game6.core.util.Resource;
import game6.core.util.ResourceContainer;

import java.nio.ByteBuffer;

public class PacketBuildingUpdateInventory extends PacketBuilding {

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
		resources = new ResourceContainer();
		resources.setCapacity(buffer.getInt());
		for (Resource resource : Resource.values()) {
			resources.addResource(resource, buffer.getInt());
		}
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(8 + 4 + 4 * Resource.values().length);
		buffer.putLong(id);
		buffer.putInt(resources.getTotalCapacity());
		for (Resource resource : Resource.values()) {
			buffer.putInt(resources.getAmount(resource));
		}
		return buffer.array();
	}

}
