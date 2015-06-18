package game6.server.buildings;

import game6.core.buildings.CoreBuildingFactory;
import game6.core.networking.packets.buildings.PacketBuildingUpdate;
import game6.server.world.ServerWorld;

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
		super.update(timeDelta);
		if (subtractEnergy(3) != 3) {
			getFaction().broadcast(new PacketBuildingUpdate(this));
		}
	}

	@Override
	public ServerWorld getWorld() {
		return defaultBehaviour.getWorld();
	}

	@Override
	public void setWorld(ServerWorld world) {
		defaultBehaviour.setWorld(world);
	}

}
