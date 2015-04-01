package game6.core.networking.packets.entities;

import game6.core.buildings.CoreBuilding;
import game6.core.entities.CoreEntity;
import game6.core.networking.packets.PacketUniqueID;

import java.nio.ByteBuffer;

public class PacketEntityAttackBuilding extends PacketUniqueID {

	public long targetID;

	public PacketEntityAttackBuilding() {
	}

	public PacketEntityAttackBuilding(CoreEntity entity, CoreBuilding target) {
		super(entity.getID());
		this.targetID = target.getID();
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		id = buffer.getLong();
		targetID = buffer.getLong();
		System.out.println("read targetID: " + targetID);
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(16);
		buffer.putLong(id);
		buffer.putLong(targetID);
		return buffer.array();
	}

}
