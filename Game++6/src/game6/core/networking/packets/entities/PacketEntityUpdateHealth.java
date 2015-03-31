package game6.core.networking.packets.entities;

import game6.core.interfaces.IHealth;
import game6.core.networking.packets.PacketUniqueID;

import java.nio.ByteBuffer;

public class PacketEntityUpdateHealth extends PacketUniqueID {

	public int health;

	public PacketEntityUpdateHealth() {
	}

	public PacketEntityUpdateHealth(IHealth entity) {
		super(entity.getID());
		this.health = entity.getHealth();
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		id = buffer.getLong();
		health = buffer.getInt();
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(8 + 4);
		buffer.putLong(id);
		buffer.putInt(health);
		return buffer.array();
	}

}
