package game6.core.entities;

import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.physics.BoundingAABB;
import de.nerogar.util.Vectorf;

public abstract class CoreEntity extends BaseEntity {

	public CoreEntity(long id, BoundingAABB bounding, Vectorf<?> position) {
		super(id, bounding, position);
	}

}
