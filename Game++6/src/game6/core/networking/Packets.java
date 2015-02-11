package game6.core.networking;

import game6.core.networking.packets.*;
import de.felk.NodeFile.NodeFile;

public enum Packets {
	// The first packet, ConnectionInfo, must always have ID 1 and it's own PacketChannel!
	CONNECTION_INFO(1, PacketChannel.CONNECTION_INFO, PacketConnectionInfo.class),
	PLAYER_INFO(2, PacketChannel.INIT, PacketPlayerInfo.class),
	MAP(3, PacketChannel.INIT, PacketMap.class),
	PLACE_BUILDING(4, PacketChannel.BUILDINGS, PacketPlaceBuilding.class),
	POWER_SUPPLY(5, PacketChannel.BUILDINGS, PacketPowerSupply.class),
	BUILDING_UPDATE(6, PacketChannel.BUILDINGS, PacketBuildingUpdate.class);

	private Class<? extends Packet> clazz;
	private int id;
	private PacketChannel channel;

	public static final int NETWORKING_VERSION = 1000;
	
	Packets(int id, PacketChannel channel, Class<? extends Packet> clazz) {
		this.id = id;
		this.channel = channel;
		this.clazz = clazz;
	}

	public Packet load(NodeFile node) {
		Packet p = null;
		try {
			p = clazz.newInstance();
			p.loadNode(node);
		} catch (InstantiationException | IllegalAccessException e) {
			System.err.println("Error calling FileNode constructor by packet class. Make sure every Packet has a default (empty) constructor!");
			e.printStackTrace();
		}
		return p;
	}

	public static Packets byId(int id) {
		for (Packets p : values()) {
			if (p.id == id) {
				return p;
			}
		}
		return null;
	}

	public static Packets byClass(Class<? extends Packet> clazz) {
		for (Packets packet : values()) {
			if (packet.clazz.equals(clazz)) {
				return packet;
			}
		}
		return null;
	}

	public int getID() {
		return id;
	}

	public PacketChannel getChannel() {
		return channel;
	}

}
