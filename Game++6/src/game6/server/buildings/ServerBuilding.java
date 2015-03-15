package game6.server.buildings;

import game6.core.buildings.CoreBuilding;
import game6.core.networking.packets.buildings.PacketBuilding;

public interface ServerBuilding extends CoreBuilding, ServerBuildingBehaviour {

	default public void process(PacketBuilding packet) {
	}
	
}
