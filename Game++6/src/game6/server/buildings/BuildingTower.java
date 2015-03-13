package game6.server.buildings;

import game6.core.buildings.CoreBuildingTower;
import game6.server.world.World;

public class BuildingTower extends CoreBuildingTower implements IServerBuilding {

	private ServerBehaviourDefault defaultBehaviour = new ServerBehaviourDefault();

	public BuildingTower() {
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
