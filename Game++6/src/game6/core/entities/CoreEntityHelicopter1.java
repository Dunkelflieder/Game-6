package game6.core.entities;

import game6.core.interfaces.*;
import game6.core.util.Resource;
import de.nerogar.util.Vector3f;

public abstract class CoreEntityHelicopter1 extends DefaultCoreEntity implements MovementAir, ResourceContainer {

	private ResourceContainer resourceContainer;

	public CoreEntityHelicopter1(long id, Vector3f position) {
		super(id, position, new BoundingAABB(-0.3f, 0f, -0.3f, 0.3f, 0.3f, 0.3f), 100);
		resourceContainer = new DefaultResourceContainer(100);
	}

	@Override
	public String getName() {
		return "Helicopter";
	}

	@Override
	public float getSpeed() {
		return 5;
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
