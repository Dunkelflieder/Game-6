package game6.client.buildings;

import game6.core.buildings.CoreBuilding;
import game6.core.networking.packets.buildings.PacketBuilding;
import game6.core.networking.packets.buildings.PacketBuildingUpdate;

public interface ClientBuilding extends CoreBuilding, ClientBuildingBehaviour {

	default public void process(PacketBuilding packet) {
		if (packet instanceof PacketBuildingUpdate) {
			setEnergy(((PacketBuildingUpdate) packet).energy);
		}
	}

}
