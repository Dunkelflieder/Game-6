package game6.server.buildings;

import game6.core.buildings.CoreBuildingLiving1;
import game6.server.world.World;

public class BuildingLiving1 extends CoreBuildingLiving1 implements IServerBuilding {

	private ServerBehaviourDefault defaultBehaviour = new ServerBehaviourDefault();

	public BuildingLiving1() {
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
