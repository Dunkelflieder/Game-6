package game6.core.networking.packets.buildings;

import de.nerogar.network.packets.Packet;

public abstract class PacketBuilding extends Packet {

	public long id;

	public PacketBuilding() {
	}
	
	public PacketBuilding(long id) {
		this.id = id;
	}

}
