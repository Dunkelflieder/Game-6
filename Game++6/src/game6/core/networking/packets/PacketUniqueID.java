package game6.core.networking.packets;

import de.nerogar.network.packets.Packet;

public abstract class PacketUniqueID extends Packet {

	public long id;

	public PacketUniqueID() {
	}
	
	public PacketUniqueID(long id) {
		this.id = id;
	}

}
