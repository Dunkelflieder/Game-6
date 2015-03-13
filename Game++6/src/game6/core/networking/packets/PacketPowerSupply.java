package game6.core.networking.packets;

import game6.core.ai.goalfinding.Path;
import game6.core.buildings.CoreBuilding;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;

public class PacketPowerSupply extends Packet {

	public long[] waypoints;
	public int amount;

	public PacketPowerSupply() {

	}

	public PacketPowerSupply(CoreBuilding source, Path path, int amount) {
		waypoints = new long[path.size() + 1];
		waypoints[0] = source.getID();
		int i = 1;
		for (CoreBuilding building : path.getWaypoints()) {
			waypoints[i] = building.getID();
			i++;
		}
		this.amount = amount;
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		int len = buffer.getInt();
		waypoints = new long[len];
		for (int i = 0; i < waypoints.length; i++) {
			waypoints[i] = buffer.getLong();
		}
		amount = buffer.getInt();
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(8 + 8 * waypoints.length);
		buffer.putInt(waypoints.length);
		for (long id : waypoints) {
			buffer.putLong(id);
		}
		buffer.putInt(amount);
		return buffer.array();
	}

}
