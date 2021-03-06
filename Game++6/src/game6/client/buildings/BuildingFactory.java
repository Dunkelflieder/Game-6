package game6.client.buildings;

import game6.client.ObjectRenderer;
import game6.client.buildings.guis.BuildingGuiEnergy;
import game6.client.world.ClientWorld;
import game6.core.buildings.CoreBuildingFactory;
import de.nerogar.render.*;

public class BuildingFactory extends CoreBuildingFactory implements ClientBuilding {

	private DefaultClientBuildingBehaviour defaultBehaviour = new DefaultClientBuildingBehaviour();

	private RenderProperties3f renderProperties;
	private ObjectRenderer renderer;

	private BuildingGuiEnergy gui;

	public BuildingFactory(long id) {
		super(id);
		gui = new BuildingGuiEnergy(this);
	}

	@Override
	public void init() {
		renderer = new ObjectRenderer(
				Texture2DLoader.loadTexture("res/buildings/#factory/color.png"),
				Texture2DLoader.loadTexture("res/buildings/#factory/light.png"),
				Texture2DLoader.loadTexture("res/buildings/#factory/faction.png"),
				WavefrontLoader.loadObject("res/buildings/#factory/mesh.obj")
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
