package game6.core.networking.packets.entities;

import de.nerogar.network.packets.Packet;

public abstract class PacketEntity extends Packet {

	public long id;

	public PacketEntity() {
	}
	
	public PacketEntity(long id) {
		this.id = id;
	}

}
