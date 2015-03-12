package game6.client.buildings;

import game6.client.ObjectRenderer;
import game6.core.buildings.CoreBuildingRock;
import de.nerogar.render.*;

public class BuildingRock extends CoreBuildingRock {

	private RenderProperties3f renderProperties;
	private ObjectRenderer renderer;

	private BuildingGui gui;

	public BuildingRock(long id) {
		super(id);
		gui = new BuildingGuiDefault(this);
	}

	@Override
	public void init() {
		renderer = new ObjectRenderer(
				Texture2DLoader.loadTexture("res/buildings/rock/color.png"),
				Texture2DLoader.loadTexture("res/buildings/rock/light.png"),
				Texture2DLoader.loadTexture("res/buildings/rock/faction.png"),
				WavefrontLoader.loadObject("res/buildings/rock/mesh.obj")
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
