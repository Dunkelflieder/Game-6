package game6.client.buildings;

import game6.client.ObjectRenderer;
import game6.client.buildings.guis.BuildingGuiDefault;
import game6.client.world.World;
import game6.core.buildings.CoreBuildingTower;
import de.nerogar.render.*;

public class BuildingTower extends CoreBuildingTower implements IClientBuilding {

	private ClientBehaviourDefault defaultBehaviour = new ClientBehaviourDefault();

	private RenderProperties3f renderProperties;
	private ObjectRenderer renderer;

	private BuildingGuiDefault gui;

	public BuildingTower(long id) {
		super(id);
		gui = new BuildingGuiDefault(this);
	}

	@Override
	public void init() {
		renderer = new ObjectRenderer(
				Texture2DLoader.loadTexture("res/buildings/tower/color.png"),
				Texture2DLoader.loadTexture("res/buildings/tower/light.png"),
				Texture2DLoader.loadTexture("res/buildings/tower/faction.png"),
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
	public void update() {
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
