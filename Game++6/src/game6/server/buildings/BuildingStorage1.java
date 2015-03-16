package game6.server.buildings;

import game6.core.buildings.CoreBuildingStorage1;
import game6.core.util.Resource;
import game6.server.world.World;

public class BuildingStorage1 extends CoreBuildingStorage1 implements ServerBuilding, ServerBuildingInventory {

	private DefaultServerBuildingBehaviour defaultBehaviour = new DefaultServerBuildingBehaviour();

	private int tick;

	public BuildingStorage1() {
		super(getNextID());
	}

	@Override
	public void init() {
	}

	@Override
	public void update(float timeDelta) {
		super.update(timeDelta);
		tick++;

		// TODO testing. remove later.
		if (tick % 10 == 0) {
			addResource(Resource.WOOD, 5);
		}
		if (tick % 10 == 5) {
			addResource(Resource.METAL, 5);
		}
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
