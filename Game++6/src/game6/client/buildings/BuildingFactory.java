package game6.client.buildings;

import game6.core.buildings.CoreBuildingFactory;
import de.nerogar.render.TextureLoader;
import de.nerogar.render.WavefrontLoader;

public class BuildingFactory extends BaseBuilding {

	public BuildingFactory(int id) {
		super(new CoreBuildingFactory(id), WavefrontLoader.loadObject("res/buildings/factory/mesh.obj"), TextureLoader.loadTexture("res/buildings/factory/color.png"));
	}

}
