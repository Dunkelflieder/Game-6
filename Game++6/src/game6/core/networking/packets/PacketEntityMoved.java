package game6.core.networking.packets;

import game6.core.entities.CoreEntity;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;
import de.nerogar.util.Vector3f;

public class PacketEntityMoved extends Packet {

	public long id;
	public Vector3f position;
	public float rotation;

	public PacketEntityMoved() {
	}

	public PacketEntityMoved(CoreEntity entity) {
		this.id = entity.getID();
		this.position = entity.getPosition();
		this.rotation = entity.getRotation();
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		id = buffer.getLong();
		position = new Vector3f(buffer.getFloat(), buffer.getFloat(), buffer.getFloat());
		rotation = buffer.getFloat();
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(24);
		buffer.putLong(id);
		buffer.putFloat(position.getX());
		buffer.putFloat(position.getY());
		buffer.putFloat(position.getZ());
		buffer.putFloat(rotation);
		return buffer.array();
	}

}
