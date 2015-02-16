package game6.core.networking.packets;

import game6.core.events.EventPowerSupply;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;

public class PacketPowerSupply extends Packet {

	public long source, destination;
	private int amount;

	public PacketPowerSupply() {

	}

	public PacketPowerSupply(EventPowerSupply event) {
		this.source = event.getSourceID();
		this.destination = event.getDestID();
		this.amount = event.getAmount();
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		source = buffer.getLong();
		destination = buffer.getLong();
		amount = buffer.getInt();
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(20);
		buffer.putLong(source);
		buffer.putLong(destination);
		buffer.putInt(amount);
		return buffer.array();
	}

}
