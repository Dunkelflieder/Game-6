package game6.core.entities;

import de.nerogar.physics.BoundingAABB;
import de.nerogar.util.Vector3f;
import de.nerogar.util.Vectorf;

public abstract class CoreEntityFighting extends CoreEntity {

	protected Vector3f rotation;

	public CoreEntityFighting(long id, BoundingAABB bounding, Vectorf<?> position) {
		super(id, bounding, position);
	}

}
