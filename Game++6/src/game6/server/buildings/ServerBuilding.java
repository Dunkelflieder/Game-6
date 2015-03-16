package game6.server.buildings;

import game6.core.buildings.CoreBuilding;
import game6.core.faction.Faction;
import game6.core.networking.packets.buildings.PacketBuilding;
import game6.core.networking.packets.buildings.PacketBuildingRemove;

public interface ServerBuilding extends CoreBuilding, ServerBuildingBehaviour {

	default public void process(PacketBuilding packet) {
	}

	@Override
	default public void kill() {
		Faction.broadcastAll(new PacketBuildingRemove(getID(), true));
		remove();
	}

}
