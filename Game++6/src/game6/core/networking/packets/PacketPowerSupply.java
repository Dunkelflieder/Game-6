package game6.core.networking.packets;

import game6.core.buildings.CoreBuilding;
import game6.core.events.EventPowerSupply;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;

public class PacketPowerSupply extends Packet {

	public long[] waypoints;
	private int amount;

	public PacketPowerSupply() {

	}

	public PacketPowerSupply(EventPowerSupply event) {
		waypoints = new long[event.getPath().size() + 1];
		waypoints[0] = event.getSource().getID();
		int i = 1;
		for (CoreBuilding building : event.getPath().getWaypoints()) {
			waypoints[i] = building.getID();
			i++;
		}
		this.amount = event.getAmount();
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
