package game6.server.buildings;

import game6.core.buildings.CoreBuilding;
import game6.core.faction.Faction;
import game6.core.networking.packets.PacketUniqueID;
import game6.core.networking.packets.buildings.PacketBuildingRemove;
import game6.core.world.Map;

public interface ServerBuilding extends CoreBuilding, ServerBuildingBehaviour {

	@Override
	default public void process(PacketUniqueID packet) {
	}

	@Override
	default public void kill() {
		Faction.broadcastAll(new PacketBuildingRemove(getID(), true));
		remove();
	}

	@Override
	default public Map<? extends CoreBuilding> getMap() {
		return getWorld().getMap();
	}
}
