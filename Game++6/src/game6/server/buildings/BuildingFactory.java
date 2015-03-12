package game6.server.buildings;

import game6.client.buildings.guis.BuildingGui;
import game6.core.buildings.CoreBuilding;
import game6.core.buildings.CoreBuildingFactory;
import game6.core.networking.packets.PacketBuildingUpdate;
import de.nerogar.render.Shader;

public class BuildingFactory extends CoreBuildingFactory {

	public BuildingFactory() {
		super(getNextID());
	}

	@Override
	public void init() {
	}

	@Override
	public void render(Shader shader) {
	}

	@Override
	public void update() {
		if (subtractEnergy(3) != 3) {
			faction.broadcast(new PacketBuildingUpdate(this));
		}
	}

	@Override
	public BuildingGui<CoreBuilding> getGui() {
		return null;
	}

}
