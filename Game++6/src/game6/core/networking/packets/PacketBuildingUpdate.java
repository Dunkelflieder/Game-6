package game6.core.networking.packets;

import game6.core.buildings.CoreBuilding;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;

public class PacketBuildingUpdate extends Packet {

	public long id;
	public int energy;

	public PacketBuildingUpdate() {
	}

	public PacketBuildingUpdate(CoreBuilding building) {
		this.id = building.getID();
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
