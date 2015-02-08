package game6.core.entities;

import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.physics.BoundingAABB;
import de.nerogar.util.Vector3f;
import de.nerogar.util.Vectorf;

public abstract class CoreEntityFighting extends BaseEntity{

	protected Vector3f rotation;

	public CoreEntityFighting(BoundingAABB bounding, Vectorf<?> position) {
		super(bounding, position);
	}

}
