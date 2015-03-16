package game6.client.buildings;

import game6.client.ObjectRenderer;
import game6.client.buildings.guis.BuildingGuiEnergy;
import game6.client.world.World;
import game6.core.buildings.CoreBuildingResearch;
import de.nerogar.render.*;

public class BuildingResearch extends CoreBuildingResearch implements ClientBuilding {

	private DefaultClientBuildingBehaviour defaultBehaviour = new DefaultClientBuildingBehaviour();

	private RenderProperties3f renderProperties;
	private ObjectRenderer renderer;

	private BuildingGuiEnergy gui;

	public BuildingResearch(long id) {
		super(id);
		gui = new BuildingGuiEnergy(this);
	}

	@Override
	public void init() {
		renderer = new ObjectRenderer(
				Texture2DLoader.loadTexture("res/buildings/research/color.png"),
				Texture2DLoader.loadTexture("res/buildings/research/light.png"),
				Texture2DLoader.loadTexture("res/buildings/research/faction.png"),
				WavefrontLoader.loadObject("res/buildings/research/mesh.obj")
				);

		renderProperties = new RenderProperties3f();
	}

	@Override
	public void render(Shader shader) {
		renderProperties.setXYZ(getPosX(), 0, getPosY());
		renderer.render(shader, renderProperties.getModelMatrix());
	}

	@Override
	public BuildingGuiEnergy getGui() {
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
