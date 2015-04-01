package game6.core.networking.packets;

import game6.core.buildings.BuildingType;

import java.nio.ByteBuffer;
import java.util.LinkedHashSet;

import de.nerogar.network.packets.Packet;

public class PacketEnabledBuildingsList extends Packet {

	public LinkedHashSet<BuildingType> buildings;

	public PacketEnabledBuildingsList() {
	}

	public PacketEnabledBuildingsList(LinkedHashSet<BuildingType> buildings) {
		this.buildings = buildings;
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		buildings = new LinkedHashSet<BuildingType>();
		int length = buffer.getInt();
		while (length > 0) {
			buildings.add(BuildingType.byTypeID(buffer.getInt()));
			length--;
		}
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(4 + 4 * buildings.size());
		buffer.putInt(buildings.size());
		for (BuildingType building : buildings) {
			buffer.putInt(building.getTypeID());
		}
		return buffer.array();
	}

}
