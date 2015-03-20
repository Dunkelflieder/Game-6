package game6.core.buildings;

import game6.core.interfaces.DefaultResourceContainer;
import game6.core.interfaces.ResourceContainer;
import game6.core.util.Resource;

public abstract class CoreBuildingStorage1 extends DefaultCoreBuilding implements ResourceContainer {

	private ResourceContainer resourceContainer;

	public CoreBuildingStorage1(long id) {
		super(id, 2, 2, 50, 0, 100);
		resourceContainer = new DefaultResourceContainer();
		resourceContainer.setCapacity(1000);
	}

	public String getName() {
		return "Lager";
	}

	@Override
	public void setCapacity(int capacity) {
		resourceContainer.setCapacity(capacity);
	}

	@Override
	public int getCapacity() {
		return resourceContainer.getCapacity();
	}

	@Override
	public int getResource(Resource resource) {
		return resourceContainer.getResource(resource);
	}

	@Override
	public void setResource(Resource resource, int amount) {
		resourceContainer.setResource(resource, amount);
	}

}
