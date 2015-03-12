package game6.client.buildings;

import game6.client.ObjectRenderer;
import game6.client.buildings.guis.BuildingGuiDefault;
import game6.core.buildings.CoreBuildingReactor;
import de.nerogar.render.*;

public class BuildingReactor extends CoreBuildingReactor {

	private RenderProperties3f renderProperties;
	private ObjectRenderer renderer;

	private BuildingGuiDefault gui;

	public BuildingReactor(long id) {
		super(id);
		gui = new BuildingGuiDefault(this);
	}

	@Override
	public void init() {
		renderer = new ObjectRenderer(
				Texture2DLoader.loadTexture("res/buildings/reactor/color.png"),
				Texture2DLoader.loadTexture("res/buildings/reactor/light.png"),
				Texture2DLoader.loadTexture("res/buildings/reactor/faction.png"),
				WavefrontLoader.loadObject("res/buildings/reactor/mesh.obj")
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
	public BuildingGuiDefault getGui() {
		return gui;
	}

}
