package game6.core.interfaces;

import de.nerogar.util.Vector3f;

public class BoundingAABB extends Bounding {

	public Vector3f a, b;

	public BoundingAABB(Vector3f a, Vector3f b) {
		this.a = a;
		this.b = b;
	}

	public BoundingAABB(float ax, float ay, float az, float bx, float by, float bz) {
		this(new Vector3f(ax, ay, az), new Vector3f(bx, by, bz));
	}

	public boolean intersects(BoundingAABB bounding) {
		for (int i = 0; i < a.getComponentCount(); i++) {
			if (bounding.b.get(i) <= a.get(i) || bounding.a.get(i) >= b.get(i)) {
				return false;
			}
		}
		return true;
	}

	public boolean intersects(BoundingAABB bounding, Vector3f ownOffset, Vector3f boundingOffset) {
		for (int i = 0; i < a.getComponentCount(); i++) {
			if (bounding.b.get(i) + boundingOffset.get(i) <= a.get(i) + ownOffset.get(i) || bounding.a.get(i) + boundingOffset.get(i) >= b.get(i) + ownOffset.get(i)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean intersects(Bounding bounding) {
		if (bounding instanceof BoundingAABB) {
			return intersects((BoundingAABB) bounding);
		}
		return false;
	}

	@Override
	public boolean intersects(Bounding bounding, Vector3f ownOffset, Vector3f boundingOffset) {
		if (bounding instanceof BoundingAABB) {
			return intersects((BoundingAABB) bounding, ownOffset, boundingOffset);
		}
		return false;
	}

}
