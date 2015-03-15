package game6.core.networking.packets.entities;

import game6.core.entities.CoreEntity;

import java.nio.ByteBuffer;

import de.nerogar.util.Vector3f;

public class PacketEntityMove extends PacketEntity {

	public Vector3f position;

	public PacketEntityMove() {
	}

	public PacketEntityMove(CoreEntity entity, Vector3f position) {
		super(entity.getID());
		this.position = position;
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
