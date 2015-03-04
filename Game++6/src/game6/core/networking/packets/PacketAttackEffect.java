package game6.core.networking.packets;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;
import de.nerogar.util.Vector3f;

public class PacketAttackEffect extends Packet {

	public Vector3f sourcePos;
	public Vector3f targetPos;

	public PacketAttackEffect() {
	}

	public PacketAttackEffect(Vector3f sourcePos, Vector3f targetPos) {
		this.sourcePos = sourcePos;
		this.targetPos = targetPos;
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		sourcePos = new Vector3f();
		targetPos = new Vector3f();

		sourcePos.setX(buffer.getFloat());
		sourcePos.setY(buffer.getFloat());
		sourcePos.setZ(buffer.getFloat());

		targetPos.setX(buffer.getFloat());
		targetPos.setY(buffer.getFloat());
		targetPos.setZ(buffer.getFloat());
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(24);
		buffer.putFloat(sourcePos.getX());
		buffer.putFloat(sourcePos.getY());
		buffer.putFloat(sourcePos.getZ());

		buffer.putFloat(targetPos.getX());
		buffer.putFloat(targetPos.getY());
		buffer.putFloat(targetPos.getZ());
		return buffer.array();
	}

}
