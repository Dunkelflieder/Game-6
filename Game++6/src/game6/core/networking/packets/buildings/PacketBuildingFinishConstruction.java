package game6.core.networking.packets.buildings;

import game6.core.networking.packets.PacketUniqueID;

import java.nio.ByteBuffer;

public class PacketBuildingFinishConstruction extends PacketUniqueID {

	public PacketBuildingFinishConstruction() {
	}

	public PacketBuildingFinishConstruction(long id) {
		super(id);
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		id = buffer.getLong();
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putLong(id);
		return buffer.array();
	}

}
