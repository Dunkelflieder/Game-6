package game6.core.networking.packets.buildings;

import game6.core.buildings.CoreBuilding;
import game6.core.networking.packets.PacketUniqueID;

import java.nio.ByteBuffer;

public class PacketBuildingUpdate extends PacketUniqueID {

	public int energy;

	public PacketBuildingUpdate() {
	}

	public PacketBuildingUpdate(CoreBuilding building) {
		super(building.getID());
		this.energy = building.getEnergy();
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		id = buffer.getLong();
		energy = buffer.getInt();
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(12);
		buffer.putLong(id);
		buffer.putInt(energy);
		return buffer.array();
	}

}
