package game6.core.entities;

import de.nerogar.physics.BoundingAABB;
import de.nerogar.util.Vector3f;
import de.nerogar.util.Vectorf;

public class CoreEntityHelicopter extends CoreEntity {

	public CoreEntityHelicopter(Vectorf<?> position) {
		super(new BoundingAABB(new Vector3f(-0.3f, 0f, -0.3f), new Vector3f(0.3f, 0.3f, 0.3f)), position);
	}

	@Override
	public void update(float timeDelta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

}
