package game6.core.entities;

import game6.core.interfaces.BoundingAABB;
import de.nerogar.util.Vector3f;

public abstract class CoreEntityTransport1 extends InventoryCoreEntity implements MovementGround {

	public CoreEntityTransport1(long id, Vector3f position) {
		super(id, position, new BoundingAABB(-0.3f, 0f, -0.3f, 0.3f, 0.3f, 0.3f), 5, 100, 100);
	}

	@Override
	public String getName() {
		return "Toyota";
	}

}
