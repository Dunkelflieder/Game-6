package game6.core.networking.packets;

import game6.core.entities.CoreEntity;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;
import de.nerogar.util.Vector3f;

public class PacketEntityMoved extends Packet {

	public long id;
	public Vector3f pos;

	public PacketEntityMoved() {
	}

	public PacketEntityMoved(CoreEntity entity) {
		this.id = entity.getID();
		this.pos = entity.getPosition();
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		id = buffer.getLong();
		pos = new Vector3f(buffer.getFloat(), buffer.getFloat(), buffer.getFloat());
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(20);
		buffer.putLong(id);
		buffer.putFloat(pos.getX());
		buffer.putFloat(pos.getY());
		buffer.putFloat(pos.getZ());
		return buffer.array();
	}

}
