package game6.core.networking.packets;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;

public class PacketRemoveEntity extends Packet {

	public long id;

	public PacketRemoveEntity() {
	}

	public PacketRemoveEntity(long id) {
		this.id = id;
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
