package game6.core.networking.packets;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;
import de.nerogar.util.Vector3f;

public class PacketAttackAnimation extends Packet {

	public Vector3f source, target;

	public PacketAttackAnimation() {
	}

	public PacketAttackAnimation(Vector3f source, Vector3f target) {
		this.source = source;
		this.target = target;
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		source = new Vector3f(buffer.getFloat(), buffer.getFloat(), buffer.getFloat());
		target = new Vector3f(buffer.getFloat(), buffer.getFloat(), buffer.getFloat());
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(24);
		buffer.putFloat(source.getX());
		buffer.putFloat(source.getY());
		buffer.putFloat(source.getZ());
		buffer.putFloat(target.getX());
		buffer.putFloat(target.getY());
		buffer.putFloat(target.getZ());
		return buffer.array();
	}

}
