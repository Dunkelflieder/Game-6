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
		this(entity, entity.getNextGoal());
	}

	public PacketEntityGoalChanged(CoreEntity entity, Vector3f goal) {
		this.id = entity.getID();
		this.goal = goal;
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		id = buffer.getLong();
		float x = buffer.getFloat();
		float y = buffer.getFloat();
		float z = buffer.getFloat();
		if (x == Float.MIN_VALUE && y == Float.MIN_VALUE && z == Float.MIN_VALUE) {
			goal = null;
		} else {
			goal = new Vector3f(x, y, z);
		}
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(20);
		buffer.putLong(id);
		if (goal == null) {
			buffer.putFloat(Float.MIN_VALUE);
			buffer.putFloat(Float.MIN_VALUE);
			buffer.putFloat(Float.MIN_VALUE);
		} else {
			buffer.putFloat(goal.getX());
			buffer.putFloat(goal.getY());
			buffer.putFloat(goal.getZ());
		}
		return buffer.array();
	}

}
