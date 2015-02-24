package game6.client.buildings;

import game6.client.ObjectRenderer;
import game6.core.buildings.CoreBuildingTower;

import java.util.List;

import de.nerogar.engine.UpdateEvent;
import de.nerogar.render.*;

public class BuildingTower extends CoreBuildingTower {

	private RenderProperties3f renderProperties;
	private ObjectRenderer renderer;

	public BuildingTower(long id) {
		super(id);
	}

	@Override
	public void init() {
		renderer = new ObjectRenderer(
				TextureLoader.loadTexture("res/buildings/tower/color.png"),
				TextureLoader.loadTexture("res/buildings/tower/light.png"),
				TextureLoader.loadTexture("res/buildings/tower/faction.png"),
				WavefrontLoader.loadObject("res/buildings/tower/mesh.obj")
				);

		renderProperties = new RenderProperties3f();
	}

	@Override
	public void render(Shader shader) {
		renderProperties.setXYZ(getPosX(), 0, getPosY());
		renderer.render(shader, renderProperties.getModelMatrix());
	}

	@Override
	public void update(List<UpdateEvent> events) {
	}

}
