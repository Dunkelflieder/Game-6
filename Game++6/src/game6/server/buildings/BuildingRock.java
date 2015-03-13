package game6.server.buildings;

import game6.core.buildings.CoreBuildingRock;
import game6.server.world.World;

public class BuildingRock extends CoreBuildingRock implements IServerBuilding {

	private ServerBehaviourDefault defaultBehaviour = new ServerBehaviourDefault();

	public BuildingRock() {
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
