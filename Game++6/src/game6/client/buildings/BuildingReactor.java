package game6.client.buildings;

import game6.core.buildings.CoreBuildingReactor;
import de.nerogar.render.TextureLoader;
import de.nerogar.render.WavefrontLoader;

public class BuildingReactor extends BaseBuilding {

	public BuildingReactor(int id) {
		super(new CoreBuildingReactor(id), WavefrontLoader.loadObject("res/buildings/reactor/mesh.obj"), TextureLoader.loadTexture("res/buildings/reactor/color.png"));
	}

}
