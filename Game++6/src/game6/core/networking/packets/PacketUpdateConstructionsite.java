package game6.core.networking.packets;

import game6.core.util.Resource;
import game6.core.util.ResourceContainer;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;

public class PacketUpdateConstructionsite extends Packet {

	public long buildingID;
	public ResourceContainer resources;

	public PacketUpdateConstructionsite() {
	}

	public PacketUpdateConstructionsite(long buildingID, ResourceContainer resources) {
		this.buildingID = buildingID;
		this.resources = resources;
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		buildingID = buffer.getLong();
		resources = new ResourceContainer();
		resources.setCapacity(buffer.getInt());
		for (Resource resource : Resource.values()) {
			resources.addResource(resource, buffer.getInt());
		}
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(8 + 4 + 4 * Resource.values().length);
		buffer.putLong(buildingID);
		buffer.putInt(resources.getTotalCapacity());
		for (Resource resource : Resource.values()) {
			buffer.putInt(resources.getAmount(resource));
		}
		return buffer.array();
	}

}
