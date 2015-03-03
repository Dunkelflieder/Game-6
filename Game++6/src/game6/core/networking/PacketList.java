package game6.core.networking;

import game6.core.networking.packets.*;
import de.nerogar.network.Packets;

public class PacketList {

	public static final int INIT = 100;
	public static final int BUILDINGS = 200;
	public static final int ENTITIES = 300;
	
	public static void init() {
		Packets.addPacket(BUILDINGS, PacketBuildingUpdate.class);
		Packets.addPacket(INIT, PacketMap.class);
		Packets.addPacket(BUILDINGS, PacketPlaceBuilding.class);
		Packets.addPacket(INIT, PacketPlayerInfo.class);
		Packets.addPacket(BUILDINGS, PacketPowerSupply.class);
		Packets.addPacket(ENTITIES, PacketSpawnEntity.class);
		Packets.addPacket(ENTITIES, PacketEntityMoved.class);
		Packets.addPacket(ENTITIES, PacketEntityGoalChanged.class);
		Packets.addPacket(ENTITIES, PacketCombatTargetSet.class);		
	}

}
