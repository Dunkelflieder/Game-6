package game6.client.buildings;

import game6.client.ObjectRenderer;
import game6.client.buildings.guis.BuildingGuiEnergy;
import game6.client.world.ClientWorld;
import game6.core.buildings.CoreBuildingEnergy1;
import de.nerogar.render.*;

public class BuildingEnergy1 extends CoreBuildingEnergy1 implements ClientBuilding {

	private DefaultClientBuildingBehaviour defaultBehaviour = new DefaultClientBuildingBehaviour();

	private RenderProperties3f renderProperties;
	private ObjectRenderer renderer;

	private BuildingGuiEnergy gui;

	public BuildingEnergy1(long id) {
		super(id);
		gui = new BuildingGuiEnergy(this);
	}

	@Override
	public void init() {
		renderer = new ObjectRenderer(
				Texture2DLoader.loadTexture("res/buildings/energy1/color.png"),
				Texture2DLoader.loadTexture("res/buildings/energy1/light.png"),
				Texture2DLoader.loadTexture("res/buildings/energy1/faction.png"),
				WavefrontLoader.loadObject("res/buildings/energy1/mesh.obj")
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
	public ClientWorld getWorld() {
		return defaultBehaviour.getWorld();
	}

	@Override
	public void setWorld(ClientWorld world) {
		defaultBehaviour.setWorld(world);
	}

}
