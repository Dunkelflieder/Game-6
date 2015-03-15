package game6.core.networking.packets;

import game6.core.buildings.BuildingType;
import game6.core.faction.Faction;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;

public class PacketStartConstruction extends Packet {

	public BuildingType building;
	public Faction faction;
	public long id;
	public int posX, posY;

	public PacketStartConstruction() {
	}

	public PacketStartConstruction(BuildingType building, Faction faction, int posX, int posY, long id) {
		this.building = building;
		this.faction = faction;
		this.posX = posX;
		this.posY = posY;
		this.id = id;
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		faction = Faction.byID(buffer.getInt());
		id = buffer.getLong();
		posX = buffer.getInt();
		posY = buffer.getInt();
		building = BuildingType.byTypeID(buffer.getInt());
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(24);
		if (faction == null) {
			buffer.putInt(-1);
		} else {
			buffer.putInt(faction.getID());
		}
		buffer.putLong(id);
		buffer.putInt(posX);
		buffer.putInt(posY);
		buffer.putInt(building.getTypeID());
		return buffer.array();
	}

}
