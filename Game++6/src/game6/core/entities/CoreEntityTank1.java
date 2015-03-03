package game6.core.entities;

import de.nerogar.physics.BoundingAABB;
import de.nerogar.util.Vector3f;

public abstract class CoreEntityTank1 extends CoreEntity {

	public CoreEntityTank1(long id, Vector3f position) {
		super(id, new BoundingAABB<Vector3f>(new Vector3f(-0.3f, 0f, -0.3f), new Vector3f(0.3f, 0.3f, 0.3f)), position, 2, false, 30);
	}

	public String getName() {
		return "Tank";
	}

}
