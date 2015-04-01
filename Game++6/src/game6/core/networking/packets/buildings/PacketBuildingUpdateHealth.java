package game6.core.networking.packets.buildings;

import game6.core.interfaces.IHealth;
import game6.core.networking.packets.PacketUniqueID;

import java.nio.ByteBuffer;

public class PacketBuildingUpdateHealth extends PacketUniqueID {

	public int health;

	public PacketBuildingUpdateHealth() {
	}

	public PacketBuildingUpdateHealth(IHealth building) {
		super(building.getID());
		this.health = building.getHealth();
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
