package game6.core.entities;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.util.Vector3f;

public interface MovementAir extends Movement {

	default List<Vector3f> getPathTo(Vector3f target) {
		List<Vector3f> path = new ArrayList<>();
		path.add(target);
		return path;
	}
	
	default boolean isPathBlocked() {
		return false;
	}
	
}
