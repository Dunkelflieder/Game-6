package game6.server.buildings;

import game6.core.buildings.CoreBuildingEnergy1;
import game6.server.world.ServerWorld;

public class BuildingEnergy1 extends CoreBuildingEnergy1 implements ServerBuilding {

	private DefaultServerBuildingBehaviour defaultBehaviour = new DefaultServerBuildingBehaviour();

	public BuildingEnergy1() {
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
