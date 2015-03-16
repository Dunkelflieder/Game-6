package game6.server.buildings;

import game6.core.buildings.CoreBuildingTower;
import game6.server.world.World;

public class BuildingTower extends CoreBuildingTower implements ServerBuilding {

	private DefaultServerBuildingBehaviour defaultBehaviour = new DefaultServerBuildingBehaviour();

	public BuildingTower() {
		super(getNextID());
	}

	@Override
	public void init() {
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
