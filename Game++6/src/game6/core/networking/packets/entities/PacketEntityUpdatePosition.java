package game6.core.networking.packets.entities;

import game6.core.entities.CoreEntity;
import game6.core.networking.packets.PacketUniqueID;

import java.nio.ByteBuffer;

import de.nerogar.util.Vector3f;

public class PacketEntityUpdatePosition extends PacketUniqueID {

	public Vector3f position;

	public PacketEntityUpdatePosition() {
	}

	public PacketEntityUpdatePosition(CoreEntity entity) {
		super(entity.getID());
		this.position = entity.getPosition();
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		id = buffer.getLong();
		position = new Vector3f(buffer.getFloat(), buffer.getFloat(), buffer.getFloat());
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(20);
		buffer.putLong(id);
		buffer.putFloat(position.getX());
		buffer.putFloat(position.getY());
		buffer.putFloat(position.getZ());
		return buffer.array();
	}

}
