package game6.core.buildings;

import game6.core.util.ResourceContainer;

public abstract class CoreBuildingStorage1 extends DefaultCoreBuilding {

	private ResourceContainer resources;

	public CoreBuildingStorage1(long id) {
		super(id, 2, 3, 50, 0);
		resources = new ResourceContainer();
		resources.setCapacity(1000);
	}

	public ResourceContainer getResources() {
		return resources;
	}

	public String getName() {
		return "Lager";
	}

}
