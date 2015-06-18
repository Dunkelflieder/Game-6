package game6.server.entities;

import game6.core.entities.CoreEntityScout1;
import game6.server.world.ServerWorld;
import de.nerogar.util.Vector3f;

public class EntityScout1 extends CoreEntityScout1 implements ServerEntity, ServerEntityInventory {

	private DefaultServerEntityBehaviour defaultBehaviour = new DefaultServerEntityBehaviour();

	public EntityScout1() {
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
