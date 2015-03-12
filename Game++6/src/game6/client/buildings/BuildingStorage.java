package game6.client.buildings;

import game6.client.ObjectRenderer;
import game6.client.buildings.guis.BuildingGuiStorage;
import game6.core.buildings.CoreBuildingStorage;
import de.nerogar.render.*;

public class BuildingStorage extends CoreBuildingStorage {

	private RenderProperties3f renderProperties;
	private ObjectRenderer renderer;

	private BuildingGuiStorage gui;

	public BuildingStorage(long id) {
		super(id);
		gui = new BuildingGuiStorage(this);
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
		renderProperties.setScale(2, 2, 2);
		renderProperties.setXYZ(getPosX(), 0, getPosY());
		renderer.render(shader, renderProperties.getModelMatrix());
	}

	@Override
	public void update() {
	}

	@Override
	public BuildingGuiStorage getGui() {
		return gui;
	}

}
