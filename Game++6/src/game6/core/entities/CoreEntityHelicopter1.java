package game6.core.entities;

import game6.core.engine.BoundingAABB;
import de.nerogar.util.Vector3f;

public abstract class CoreEntityHelicopter1 extends DefaultCoreEntity implements MovementAir {

	public CoreEntityHelicopter1(long id, Vector3f position) {
		super(id, position, new BoundingAABB(-0.3f, 0f, -0.3f, 0.3f, 0.3f, 0.3f));
	}

	@Override
	public String getName() {
		return "Helicopter";
	}

	@Override
	public float getSpeed() {
		return 5;
	}

}
