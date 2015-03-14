package game6.server.entities;

import game6.core.ai.pathfinding.Pathfinder;
import game6.server.world.World;

public class DefaultServerEntityBehaviour implements ServerEntityBehaviour {

	private static long NEXT_ID = 0;
	
	private World world;
	
	public static long getNextID() {
		NEXT_ID++;
		return NEXT_ID;
	}

	public Pathfinder getPathfinder() {
		return world.getMap().getPathfinder();
	}
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
}
