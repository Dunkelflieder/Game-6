package game6.server.buildings;

import game6.core.buildings.CoreBuildingResearch;
import game6.server.world.World;

public class BuildingResearch extends CoreBuildingResearch implements ServerBuilding {

	private DefaultServerBuildingBehaviour defaultBehaviour = new DefaultServerBuildingBehaviour();

	public BuildingResearch() {
		super(getNextID());
	}

	@Override
	public void init() {
	}

	@Override
	public void update() {
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
