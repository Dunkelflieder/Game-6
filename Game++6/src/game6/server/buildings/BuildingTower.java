package game6.server.buildings;

import game6.client.buildings.guis.BuildingGui;
import game6.core.buildings.CoreBuilding;
import game6.core.buildings.CoreBuildingTower;
import de.nerogar.render.Shader;

public class BuildingTower extends CoreBuildingTower {

	public BuildingTower() {
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
	}

	@Override
	public BuildingGui<CoreBuilding> getGui() {
		return null;
	}

}
