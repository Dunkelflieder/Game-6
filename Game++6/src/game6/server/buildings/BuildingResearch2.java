package game6.server.buildings;

import game6.core.buildings.CoreBuildingResearch2;
import game6.server.world.World;

public class BuildingResearch2 extends CoreBuildingResearch2 implements ServerBuilding {

	private DefaultServerBuildingBehaviour defaultBehaviour = new DefaultServerBuildingBehaviour();

	public BuildingResearch2() {
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
