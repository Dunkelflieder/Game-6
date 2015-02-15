package game6.core.networking.packets;

import game6.core.faction.Faction;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;

public class PacketPlayerInfo extends Packet {

	public Faction faction;

	public PacketPlayerInfo() {
	}

	public PacketPlayerInfo(Faction faction) {
		this.faction = faction;
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		faction = Faction.byID(buffer.getInt());
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(faction.getID());
		buffer.flip();
		return buffer.array();
	}

}
