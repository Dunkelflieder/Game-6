package game6.core.networking.packets;

import game6.core.buildings.BuildingType;
import game6.core.faction.Faction;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;

public class PacketPlaceBuilding extends Packet {

	public BuildingType building;
	public Faction faction;
	public long id;
	public int posX, posY;

	public PacketPlaceBuilding() {
	}

	public PacketPlaceBuilding(BuildingType building, Faction faction, long id, int posX, int posY) {
		this.building = building;
		this.faction = faction;
		this.id = id;
		this.posX = posX;
		this.posY = posY;
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
		buffer.flip();
		return buffer.array();
	}

}
