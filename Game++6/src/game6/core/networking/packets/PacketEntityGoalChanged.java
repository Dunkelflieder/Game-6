package game6.core.networking.packets;

import game6.core.entities.CoreEntity;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;
import de.nerogar.util.Vector3f;

public class PacketEntityGoalChanged extends Packet {

	public long id;
	public Vector3f goal;

	public PacketEntityGoalChanged() {
	}

	public PacketEntityGoalChanged(CoreEntity entity) {
		this.id = entity.getID();
		this.goal = entity.getNextGoal();
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		id = buffer.getLong();
		goal = new Vector3f(buffer.getFloat(), buffer.getFloat(), buffer.getFloat());
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(20);
		buffer.putLong(id);
		buffer.putFloat(goal.getX());
		buffer.putFloat(goal.getY());
		buffer.putFloat(goal.getZ());
		return buffer.array();
	}

}
