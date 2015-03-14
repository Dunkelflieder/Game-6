package game6.core.world;

import game6.core.entities.CoreEntity;
import de.nerogar.util.Vector3f;

public class RayIntersection implements Comparable<RayIntersection> {
	public Vector3f intersectionPoint;
	public float distance;
	public CoreEntity intersectingBody;

	public RayIntersection(Vector3f intersectionPoint, float distance, CoreEntity intersectingBody) {
		this.intersectionPoint = intersectionPoint;
		this.distance = distance;
		this.intersectingBody = intersectingBody;
	}

	@Override
	public int compareTo(RayIntersection obj) {
		return distance - obj.distance < 0 ? -1 : 1;
	}

}
