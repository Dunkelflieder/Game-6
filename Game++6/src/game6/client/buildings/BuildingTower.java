package game6.client.buildings;

import game6.core.buildings.CoreBuildingTower;
import de.nerogar.render.TextureLoader;
import de.nerogar.render.WavefrontLoader;

public class BuildingTower extends BaseBuilding {

	public BuildingTower(int id) {
		super(new CoreBuildingTower(id), WavefrontLoader.loadObject("res/buildings/tower/mesh.obj"), TextureLoader.loadTexture("res/buildings/tower/color.png"));
	}

}
