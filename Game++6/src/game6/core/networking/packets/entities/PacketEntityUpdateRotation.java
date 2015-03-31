package game6.core.networking.packets.entities;

import game6.core.entities.CoreEntity;
import game6.core.networking.packets.PacketUniqueID;

import java.nio.ByteBuffer;

public class PacketEntityUpdateRotation extends PacketUniqueID {

	public float rotation;

	public PacketEntityUpdateRotation() {
	}

	public PacketEntityUpdateRotation(CoreEntity entity) {
		super(entity.getID());
		this.rotation = entity.getRotation();
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		id = buffer.getLong();
		rotation = buffer.getFloat();
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(12);
		buffer.putLong(id);
		buffer.putFloat(rotation);
		return buffer.array();
	}

}
