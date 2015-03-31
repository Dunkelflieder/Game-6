package game6.core.networking.packets.entities;

import game6.core.entities.CoreEntity;
import game6.core.networking.packets.PacketUniqueID;

import java.nio.ByteBuffer;

public class PacketEntityAttackEntity extends PacketUniqueID {

	public long targetID;

	public PacketEntityAttackEntity() {
	}

	public PacketEntityAttackEntity(CoreEntity entity, CoreEntity target) {
		super(entity.getID());
		this.targetID = target.getID();
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		id = buffer.getLong();
		targetID = buffer.getLong();
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(16);
		buffer.putLong(id);
		buffer.putLong(targetID);
		return buffer.array();
	}

}
