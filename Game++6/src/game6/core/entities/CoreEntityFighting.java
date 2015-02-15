package game6.core.entities;

import de.nerogar.physics.BoundingAABB;
import de.nerogar.util.Vector3f;

public abstract class CoreEntityFighting extends CoreEntity {

	protected Vector3f rotation;

	public CoreEntityFighting(long id, BoundingAABB<Vector3f> bounding, Vector3f position) {
		super(id, bounding, position);
	}

}
