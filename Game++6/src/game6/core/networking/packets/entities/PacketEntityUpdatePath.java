package game6.core.networking.packets.entities;

import game6.core.entities.CoreEntity;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import de.nerogar.util.Vector3f;

public class PacketEntityUpdatePath extends PacketEntity {

	public List<Vector3f> path;

	public PacketEntityUpdatePath() {
	}

	public PacketEntityUpdatePath(CoreEntity entity) {
		super(entity.getID());
		this.path = entity.getPath();
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		id = buffer.getLong();

		path = new ArrayList<>();
		int length = buffer.getInt();
		while (length > 0) {
			path.add(new Vector3f(buffer.getFloat(), buffer.getFloat(), buffer.getFloat()));
			length--;
		}

	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(8 + 4 + 12 * path.size());
		buffer.putLong(id);
		buffer.putInt(path.size());
		for (Vector3f node : path) {
			buffer.putFloat(node.getX());
			buffer.putFloat(node.getY());
			buffer.putFloat(node.getZ());
		}
		return buffer.array();
	}

}
