package game6.core.networking.packets;

import game6.core.entities.EntityType;
import game6.core.faction.Faction;

import java.nio.ByteBuffer;

import de.nerogar.network.packets.Packet;
import de.nerogar.util.Vector3f;

public class PacketSpawnEntity extends Packet {

	public EntityType entity;
	public Faction faction;
	public long id;
	public Vector3f position;

	public PacketSpawnEntity() {
	}

	public PacketSpawnEntity(EntityType entity, Faction faction, long id, Vector3f position) {
		this.entity = entity;
		this.faction = faction;
		this.id = id;
		this.position = position;
	}

	@Override
	public void fromByteArray(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		faction = Faction.byID(buffer.getInt());
		id = buffer.getLong();
		position = new Vector3f(buffer.getFloat(), buffer.getFloat(), buffer.getFloat());
		entity = EntityType.byTypeID(buffer.getInt());
	}

	@Override
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(28);
		if (faction == null) {
			buffer.putInt(-1);
		} else {
			buffer.putInt(faction.getID());
		}
		buffer.putLong(id);
		buffer.putFloat(position.getX());
		buffer.putFloat(position.getY());
		buffer.putFloat(position.getZ());
		buffer.putInt(entity.getTypeID());
		return buffer.array();
	}

}
