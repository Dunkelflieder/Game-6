package game6.core.buildings;

import game6.core.networking.packets.PacketUpdateStorage;
import game6.core.util.ResourceContainer;

public abstract class CoreBuildingStorage extends CoreBuilding {

	private ResourceContainer resources;

	public CoreBuildingStorage(long id) {
		super(id, 2, 2, 50, 0);
		resources = new ResourceContainer();
		resources.setCallback(this::resourcesChanged);
	}

	private void resourcesChanged() {
		faction.broadcast(new PacketUpdateStorage(getID(), resources));
	}

	public ResourceContainer getResources() {
		return resources;
	}

	public String getName() {
		return "Lager";
	}

}
