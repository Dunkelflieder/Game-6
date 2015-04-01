package game6.server.entities;

import game6.core.entities.CoreEntityTransport2;
import game6.server.world.World;
import de.nerogar.util.Vector3f;

public class EntityTransport2 extends CoreEntityTransport2 implements ServerEntity, ServerEntityInventory {

	private DefaultServerEntityBehaviour defaultBehaviour = new DefaultServerEntityBehaviour();

	public EntityTransport2() {
		super(DefaultServerEntityBehaviour.getNextID(), new Vector3f());
	}

	@Override
	public World getWorld() {
		return defaultBehaviour.getWorld();
	}

	@Override
	public void setWorld(World world) {
		defaultBehaviour.setWorld(world);
	}

	@Override
	public void update(float timeDelta) {
		super.update(timeDelta);
	}

}
