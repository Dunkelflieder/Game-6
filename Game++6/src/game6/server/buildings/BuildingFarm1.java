package game6.server.buildings;

import game6.core.buildings.CoreBuildingFarm1;
import game6.server.world.World;

public class BuildingFarm1 extends CoreBuildingFarm1 implements ServerBuilding {

	private DefaultServerBuildingBehaviour defaultBehaviour = new DefaultServerBuildingBehaviour();

	public BuildingFarm1() {
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
