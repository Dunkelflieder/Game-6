package game6.server.buildings;

import game6.server.world.ServerWorld;

public class DefaultServerBuildingBehaviour implements ServerBuildingBehaviour {

	private ServerWorld world;

	@Override
	public ServerWorld getWorld() {
		return world;
	}

	@Override
	public void setWorld(ServerWorld world) {
		this.world = world;
	}

}
