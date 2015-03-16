package game6.core.networking.packets.buildings;

import java.nio.ByteBuffer;

public class PacketBuildingRemove extends PacketBuilding {

	public boolean killed;

	public PacketBuildingRemove() {
	}

	public PacketBuildingRemove(long id, boolean killed) {
		super(id);
		this.killed = killed;
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		id = buffer.getLong();
		killed = buffer.get() == 0 ? false : true;
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(9);
		buffer.putLong(id);
		buffer.put(killed ? (byte) 1 : (byte) 0);
		return buffer.array();
	}

}
