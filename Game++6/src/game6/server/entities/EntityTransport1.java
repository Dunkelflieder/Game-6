package game6.server.entities;

import game6.core.entities.CoreEntityTransport1;
import game6.server.world.ServerWorld;
import de.nerogar.util.Vector3f;

public class EntityTransport1 extends CoreEntityTransport1 implements ServerEntity, ServerEntityInventory {

	private DefaultServerEntityBehaviour defaultBehaviour = new DefaultServerEntityBehaviour();

	public EntityTransport1() {
		super(DefaultServerEntityBehaviour.getNextID(), new Vector3f());
	}

	@Override
	public ServerWorld getWorld() {
		return defaultBehaviour.getWorld();
	}

	@Override
	public void setWorld(ServerWorld world) {
		defaultBehaviour.setWorld(world);
	}

	@Override
	public void update(float timeDelta) {
		super.update(timeDelta);
	}

}
