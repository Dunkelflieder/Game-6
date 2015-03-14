package game6.core.engine;

import de.nerogar.util.Vector3f;

public abstract class Bounding {

	public abstract boolean intersects(Bounding bounding);
	public abstract boolean intersects(Bounding bounding, Vector3f ownOffset, Vector3f boundingOffset);
	
}
