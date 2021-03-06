package game6.core.networking;

import game6.core.networking.packets.*;
import game6.core.networking.packets.buildings.*;
import game6.core.networking.packets.entities.*;
import de.nerogar.network.Packets;

public class PacketList {

	public static final int INIT = 100;
	public static final int INFO = 200;
	public static final int WORLD = 400;

	public static final int ENTITIES = 1000;
	public static final int BUILDINGS = 1100;
	
	private static boolean init = false; 

	public static void init() {
		if (init) return;
		init = true;
		Packets.addPacket(INIT, PacketPlayerInfo.class);
		Packets.addPacket(INIT, PacketMap.class);

		Packets.addPacket(INFO, PacketEnabledBuildingsList.class);

		Packets.addPacket(WORLD, PacketPowerSupply.class);
		Packets.addPacket(WORLD, PacketSpawnEntity.class);
		Packets.addPacket(WORLD, PacketStartConstruction.class);
		Packets.addPacket(WORLD, PacketSpawnBuilding.class);
		Packets.addPacket(WORLD, PacketAttackAnimation.class);
		// Packets.addPacket(ENTITIES, PacketCombatTargetSet.class);
		// Packets.addPacket(ENTITIES, PacketAttackEffect.class);

		// the new entity specific packets
		Packets.addPacket(ENTITIES, PacketEntityUpdatePosition.class);
		Packets.addPacket(ENTITIES, PacketEntityUpdateRotation.class);
		Packets.addPacket(ENTITIES, PacketEntityUpdatePath.class);
		Packets.addPacket(ENTITIES, PacketEntityUpdateInventory.class);
		Packets.addPacket(ENTITIES, PacketEntityUpdateHealth.class);
		Packets.addPacket(ENTITIES, PacketEntityMove.class);
		Packets.addPacket(ENTITIES, PacketEntityRemove.class);
		Packets.addPacket(ENTITIES, PacketEntityAttackEntity.class);
		Packets.addPacket(ENTITIES, PacketEntityAttackBuilding.class);
		Packets.addPacket(ENTITIES, PacketEntityDeliverJob.class);

		// the new building specific packets
		Packets.addPacket(BUILDINGS, PacketBuildingUpdateInventory.class);
		Packets.addPacket(BUILDINGS, PacketBuildingFinishConstruction.class);
		Packets.addPacket(BUILDINGS, PacketBuildingUpdate.class);
		Packets.addPacket(BUILDINGS, PacketBuildingRemove.class);
		Packets.addPacket(BUILDINGS, PacketBuildingUpdateHealth.class);
	}

}
