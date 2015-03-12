package game6.core.networking.packets;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;

public class PacketFinishConstruction extends Packet {

	public long buildingID;

	public PacketFinishConstruction() {
	}

	public PacketFinishConstruction(long buildingID) {
		this.buildingID = buildingID;
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		buildingID = buffer.getLong();
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putLong(buildingID);
		return buffer.array();
	}

}
