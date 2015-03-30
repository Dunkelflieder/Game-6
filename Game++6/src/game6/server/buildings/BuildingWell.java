package game6.server.buildings;

import game6.core.buildings.CoreBuildingWell;
import game6.server.world.World;

public class BuildingWell extends CoreBuildingWell implements ServerBuilding {

	private DefaultServerBuildingBehaviour defaultBehaviour = new DefaultServerBuildingBehaviour();

	public BuildingWell() {
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
