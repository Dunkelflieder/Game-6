package game6.client.buildings;

import game6.core.buildings.CoreBuildingResearch;
import de.nerogar.render.TextureLoader;
import de.nerogar.render.WavefrontLoader;

public class BuildingResearch extends BaseBuilding {

	public BuildingResearch() {
		super(new CoreBuildingResearch(), WavefrontLoader.loadObject("res/buildings/research/mesh.obj"), TextureLoader.loadTexture("res/buildings/research/color.png"));
	}

}
