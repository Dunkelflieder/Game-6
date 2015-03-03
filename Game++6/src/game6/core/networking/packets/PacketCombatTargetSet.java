package game6.core.networking.packets;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;

public class PacketCombatTargetSet extends Packet {

	public static final byte BUILDING = 0;
	public static final byte ENTITIY = 1;

	public long sourceID;
	public long targetID;

	public byte sourceType;
	public byte targetType;

	public PacketCombatTargetSet() {
	}

	public PacketCombatTargetSet(byte sourceType, long sourceID, byte targetType, long targetID) {
		this.sourceType = sourceType;
		this.sourceID = sourceID;
		this.targetType = targetType;
		this.targetID = targetID;
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		sourceType = buffer.get();
		sourceID = buffer.getLong();
		targetType = buffer.get();
		targetID = buffer.getLong();
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(18);
		buffer.put(sourceType);
		buffer.putLong(sourceID);
		buffer.put(targetType);
		buffer.putLong(targetID);

		return buffer.array();
	}

}
