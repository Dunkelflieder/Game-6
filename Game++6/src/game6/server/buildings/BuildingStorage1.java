package game6.server.buildings;

import game6.core.buildings.CoreBuildingStorage1;
import game6.core.networking.packets.PacketUpdateStorage;
import game6.core.util.Resource;
import game6.server.world.World;

public class BuildingStorage1 extends CoreBuildingStorage1 implements ServerBuilding {

	private DefaultServerBuildingBehaviour defaultBehaviour = new DefaultServerBuildingBehaviour();

	private int tick;

	public BuildingStorage1() {
		super(getNextID());
		getResources().setChangeCallback(this::resourcesChanged);
	}

	private void resourcesChanged() {
		getFaction().broadcast(new PacketUpdateStorage(getID(), getResources()));
	}

	@Override
	public void init() {
	}

	@Override
	public void update() {
		tick++;

		// TODO testing. remove later.
		if (tick % 10 == 0) {
			getResources().addResource(Resource.WOOD, 5);
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
