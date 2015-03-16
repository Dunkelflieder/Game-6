package game6.server.buildings;

import game6.core.buildings.CoreBuildingFactory;
import game6.core.networking.packets.buildings.PacketBuildingUpdate;
import game6.server.world.World;

public class BuildingFactory extends CoreBuildingFactory implements ServerBuilding {

	private DefaultServerBuildingBehaviour defaultBehaviour = new DefaultServerBuildingBehaviour();

	public BuildingFactory() {
		super(getNextID());
	}

	@Override
	public void init() {
	}

	@Override
	public void update(float timeDelta) {
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
