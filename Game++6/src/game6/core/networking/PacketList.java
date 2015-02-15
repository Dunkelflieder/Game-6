package game6.core.networking;

import game6.core.networking.packets.*;
import de.nerogar.network.Packets;

public class PacketList {

	public static void init() {
		Packets.addPacket(PacketChannel.BUILDINGS, PacketBuildingUpdate.class);
		Packets.addPacket(PacketChannel.INIT, PacketMap.class);
		Packets.addPacket(PacketChannel.BUILDINGS, PacketPlaceBuilding.class);
		Packets.addPacket(PacketChannel.INIT, PacketPlayerInfo.class);
		Packets.addPacket(PacketChannel.BUILDINGS, PacketPowerSupply.class);
	}

}
