package game6.core.world;

import game6.core.entities.CoreEntity;
import de.nerogar.util.Ray;
import de.nerogar.util.Vector3f;

public class IntersectionHelper {

	public static RayIntersection getIntersecting(IDList<? extends CoreEntity> entities, Ray<Vector3f> ray) {

		// TODO change to partitioned space
		for (CoreEntity entity : entities) {
			RayIntersection intersection = getIntersectionPoint(ray, entity);
			if (intersection != null) {
				return intersection;
			}
		}

		return null;
	}

	private static RayIntersection getIntersectionPoint(Ray<Vector3f> ray, CoreEntity entity) {
		Vector3f intersectionPoint = new Vector3f();

		Vector3f inverse = new Vector3f(1f / ray.getDirection().getX(), 1f / ray.getDirection().getY(), 1f / ray.getDirection().getZ());
		for (int axis = 0; axis < ray.getDirection().getComponentCount(); axis++) {

			if (ray.getDirection().get(axis) != 0f) {
				float intersectionPlane;
				if (ray.getDirection().get(axis) < 0) {
					intersectionPlane = entity.getPosition().get(axis) + entity.getBounding().b.get(axis);
				} else {
					intersectionPlane = entity.getPosition().get(axis) + entity.getBounding().a.get(axis);
				}

				intersectionPoint.set(ray.getDirection());
				float distance = (intersectionPlane - ray.getStart().get(axis)) * inverse.get(axis);
				intersectionPoint.multiply(distance).add(ray.getStart());

				boolean isValidIntersection = true;

				for (int i = 0; i < ray.getDirection().getComponentCount(); i++) {
					if (i != axis) {
						if (intersectionPoint.get(i) >= entity.getPosition().get(i) + entity.getBounding().b.get(i) || intersectionPoint.get(i) <= entity.getPosition().get(i) + entity.getBounding().a.get(i)) {
							isValidIntersection = false;
							break;
						}
					}
				}

				if (isValidIntersection) {
					return new RayIntersection(intersectionPoint, distance, entity);
				}
			}
		}

		return null;
	}

}
