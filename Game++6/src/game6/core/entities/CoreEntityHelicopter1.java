package game6.core.entities;

import java.util.List;

import de.nerogar.engine.UpdateEvent;
import de.nerogar.physics.BoundingAABB;
import de.nerogar.util.Vector3f;

public class CoreEntityHelicopter1 extends CoreEntity {

	public CoreEntityHelicopter1(long id, Vector3f position) {
		super(id, new BoundingAABB<Vector3f>(new Vector3f(-0.3f, 0f, -0.3f), new Vector3f(0.3f, 0.3f, 0.3f)), position);
	}

	@Override
	public void update(float timeDelta, List<UpdateEvent> events) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub

	}

}
