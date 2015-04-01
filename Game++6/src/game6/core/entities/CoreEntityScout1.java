package game6.core.entities;

import game6.core.interfaces.BoundingAABB;
import de.nerogar.util.Vector3f;

public abstract class CoreEntityScout1 extends InventoryCoreEntity implements MovementGround {

	public CoreEntityScout1(long id, Vector3f position) {
		super(id, position, new BoundingAABB(-0.5f, 0f, -0.5f, 0.5f, 0.3f, 0.5f), 5, 100, 50);
	}

	@Override
	public String getName() {
		return "HMMWV Scout";
	}

}
