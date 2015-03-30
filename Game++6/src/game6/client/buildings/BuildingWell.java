package game6.client.buildings;

import game6.client.ObjectRenderer;
import game6.client.buildings.guis.BuildingGuiDefault;
import game6.client.world.World;
import game6.core.buildings.CoreBuildingWell;
import de.nerogar.render.*;

public class BuildingWell extends CoreBuildingWell implements ClientBuilding {

	private DefaultClientBuildingBehaviour defaultBehaviour = new DefaultClientBuildingBehaviour();

	private RenderProperties3f renderProperties;
	private ObjectRenderer renderer;

	private BuildingGuiDefault gui;

	public BuildingWell(long id) {
		super(id);
		gui = new BuildingGuiDefault(this);
	}

	@Override
	public void init() {
		renderer = new ObjectRenderer(
				Texture2DLoader.loadTexture("res/buildings/well/color.png"),
				Texture2DLoader.loadTexture("res/buildings/well/light.png"),
				Texture2DLoader.loadTexture("res/buildings/well/faction.png"),
				WavefrontLoader.loadObject("res/buildings/well/mesh.obj")
				);

		renderProperties = new RenderProperties3f();
	}

	@Override
	public void render(Shader shader) {
		renderProperties.setXYZ(getPosX(), 0, getPosY());
		renderer.render(shader, renderProperties.getModelMatrix());
	}

	@Override
	public BuildingGuiDefault getGui() {
		return gui;
	}

	@Override
	public World getWorld() {
		return defaultBehaviour.getWorld();
	}

	@Override
	public void setWorld(World world) {
		defaultBehaviour.setWorld(world);
	}

}
