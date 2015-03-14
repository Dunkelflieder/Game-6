package game6.server.entities;

import game6.core.ai.pathfinding.Pathfinder;
import game6.core.entities.CoreEntityHelicopter1;
import game6.server.world.World;
import de.nerogar.util.Vector3f;

public class EntityHelicopter1 extends CoreEntityHelicopter1 implements ServerEntity {

	private DefaultServerEntityBehaviour defaultBehaviour = new DefaultServerEntityBehaviour();
	
	public EntityHelicopter1() {
		super(DefaultServerEntityBehaviour.getNextID(), new Vector3f());
	}

	@Override
	public Pathfinder getPathfinder() {
		return defaultBehaviour.getPathfinder();
	}

	@Override
	public World getWorld() {
		return defaultBehaviour.getWorld();
	}

	@Override
	public void setWorld(World world) {
		defaultBehaviour.setWorld(world);
	}

}
