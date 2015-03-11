package game6.client.buildings;

import game6.client.ObjectRenderer;
import game6.core.buildings.CoreBuildingFactory;
import de.nerogar.render.*;

public class BuildingFactory extends CoreBuildingFactory {

	private RenderProperties3f renderProperties;
	private ObjectRenderer renderer;

	private BuildingGui gui;

	public BuildingFactory(long id) {
		super(id);
		gui = new BuildingGuiEnergy(this);
	}

	@Override
	public void init() {
		renderer = new ObjectRenderer(
				TextureLoader.loadTexture("res/buildings/factory/color.png"),
				TextureLoader.loadTexture("res/buildings/factory/light.png"),
				TextureLoader.loadTexture("res/buildings/factory/faction.png"),
				WavefrontLoader.loadObject("res/buildings/factory/mesh.obj")
				);

		renderProperties = new RenderProperties3f();
	}

	@Override
	public void render(Shader shader) {
		renderProperties.setXYZ(getPosX(), 0, getPosY());
		renderer.render(shader, renderProperties.getModelMatrix());
	}

	@Override
	public void update() {
	}

	@Override
	public BuildingGui getGui() {
		return gui;
	}

}
