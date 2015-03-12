package game6.server.buildings;

import game6.client.buildings.guis.BuildingGui;
import game6.core.buildings.*;
import de.nerogar.render.Shader;

public class BuildingLiving1 extends CoreBuildingLiving1 {

	public BuildingLiving1() {
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
