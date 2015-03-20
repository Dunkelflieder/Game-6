package game6.core.entities;

import game6.core.interfaces.BoundingAABB;
import de.nerogar.util.Vector3f;

public abstract class CoreEntityTank1 extends DefaultCoreEntity implements MovementGround {

	public CoreEntityTank1(long id, Vector3f position) {
		super(id, position, new BoundingAABB(-0.3f, 0f, -0.3f, 0.3f, 0.3f, 0.3f), 2, 500);

		// TODO enable fighting again
		// getFightingObject().setReach(15);
	}

	@Override
	public String getName() {
		return "Tank";
	}

}
