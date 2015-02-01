package client.world.buildings;

import de.nerogar.render.TextureLoader;
import de.nerogar.render.WavefrontLoader;

public class BuildingReactor extends BaseBuilding {

	public BuildingReactor() {
		super(WavefrontLoader.loadObject("res/entities/reactor/mesh.obj"), TextureLoader.loadTexture("res/entities/reactor/color.png"), 2, 2);
	}

}
