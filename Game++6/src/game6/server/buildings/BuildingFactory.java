package game6.server.buildings;

import game6.core.buildings.CoreBuildingFactory;
import game6.core.networking.packets.PacketBuildingUpdate;
import game6.server.world.World;

public class BuildingFactory extends CoreBuildingFactory implements ServerBuilding {

	private DefaultServerBehaviour defaultBehaviour = new DefaultServerBehaviour();

	public BuildingFactory() {
		super(getNextID());
	}

	@Override
	public void init() {
	}

	@Override
	public void update() {
		if (subtractEnergy(3) != 3) {
			getFaction().broadcast(new PacketBuildingUpdate(this));
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
