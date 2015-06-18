package game6.server.buildings;

import game6.server.world.ServerWorld;

public interface ServerBuildingBehaviour {

	public ServerWorld getWorld();
	
	public void setWorld(ServerWorld world);
	
}
