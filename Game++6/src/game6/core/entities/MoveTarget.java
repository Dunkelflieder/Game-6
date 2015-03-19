package game6.core.entities;

import de.nerogar.util.Vector3f;

public interface MoveTarget {

	public boolean isReached();
	
	public Vector3f getMovePosition();
	
}
