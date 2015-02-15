package game6.core.entities;

import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.physics.BoundingAABB;
import de.nerogar.util.Vector3f;

public abstract class CoreEntity extends BaseEntity<Vector3f> {

	public CoreEntity(long id, BoundingAABB<Vector3f> bounding, Vector3f position) {
		super(id, bounding, position);
	}

}
