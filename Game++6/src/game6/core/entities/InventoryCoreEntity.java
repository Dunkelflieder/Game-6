package game6.core.entities;

import game6.core.interfaces.*;
import game6.core.util.Resource;
import de.nerogar.util.Vector3f;

public abstract class InventoryCoreEntity extends DefaultCoreEntity implements ResourceContainer {

	private ResourceContainer resourceContainer;

	public InventoryCoreEntity(long id, Vector3f position, BoundingAABB bounding, float speed, int maxHealth, int inventoryCapacity) {
		super(id, position, bounding, speed, maxHealth);
		resourceContainer = new DefaultResourceContainer(inventoryCapacity);
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
