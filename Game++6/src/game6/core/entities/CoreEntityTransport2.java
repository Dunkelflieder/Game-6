package game6.core.entities;

import game6.core.interfaces.BoundingAABB;
import game6.core.interfaces.ResourceContainer;
import de.nerogar.util.Vector3f;

public abstract class CoreEntityTransport2 extends InventoryCoreEntity implements MovementGround, ResourceContainer {

	public CoreEntityTransport2(long id, Vector3f position) {
		super(id, position, new BoundingAABB(-0.8f, 0f, -0.8f, 0.5f, 0.8f, 0.8f), 3, 100, 200);
	}

	@Override
	public String getName() {
		return "Truck";
	}

}
