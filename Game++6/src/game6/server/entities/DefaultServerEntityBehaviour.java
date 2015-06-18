package game6.server.entities;

import game6.core.ai.pathfinding.Pathfinder;
import game6.server.world.ServerWorld;

public class DefaultServerEntityBehaviour implements ServerEntityBehaviour {

	private static long NEXT_ID = 0;
	
	private ServerWorld world;
	
	public static long getNextID() {
		NEXT_ID++;
		return NEXT_ID;
	}

	public Pathfinder getPathfinder() {
		return world.getMap().getPathfinder();
	}
	
	public ServerWorld getWorld() {
		return world;
	}

	public void setWorld(ServerWorld world) {
		this.world = world;
	}
	
}
