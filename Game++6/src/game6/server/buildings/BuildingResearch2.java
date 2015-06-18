package game6.server.buildings;

import game6.core.buildings.CoreBuildingResearch2;
import game6.server.world.ServerWorld;

public class BuildingResearch2 extends CoreBuildingResearch2 implements ServerBuilding {

	private DefaultServerBuildingBehaviour defaultBehaviour = new DefaultServerBuildingBehaviour();

	public BuildingResearch2() {
		super(getNextID());
	}

	@Override
	public void init() {
	}

	@Override
	public ServerWorld getWorld() {
		return defaultBehaviour.getWorld();
	}

	@Override
	public void setWorld(ServerWorld world) {
		defaultBehaviour.setWorld(world);
	}

}
