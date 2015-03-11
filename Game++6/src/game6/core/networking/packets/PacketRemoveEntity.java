package game6.core.networking.packets;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;

public class PacketRemoveEntity extends Packet {

	public long id;
	public boolean killed;

	public PacketRemoveEntity() {
	}

	public PacketRemoveEntity(long id, boolean killed) {
		this.id = id;
		this.killed = killed;
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		id = buffer.getLong();
		killed = buffer.get() == 0 ? false : true;
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(9);
		buffer.putLong(id);
		buffer.put(killed ? (byte) 1 : (byte) 0);
		return buffer.array();
	}

}
