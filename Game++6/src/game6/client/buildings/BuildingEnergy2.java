package game6.client.buildings;

import game6.client.ObjectRenderer;
import game6.client.buildings.guis.BuildingGuiEnergy;
import game6.client.world.World;
import game6.core.buildings.CoreBuildingEnergy2;
import de.nerogar.render.*;

public class BuildingEnergy2 extends CoreBuildingEnergy2 implements ClientBuilding {

	private DefaultClientBuildingBehaviour defaultBehaviour = new DefaultClientBuildingBehaviour();

	private RenderProperties3f renderProperties;
	private ObjectRenderer renderer;

	private BuildingGuiEnergy gui;

	public BuildingEnergy2(long id) {
		super(id);
		gui = new BuildingGuiEnergy(this);
	}

	@Override
	public void init() {
		renderer = new ObjectRenderer(
				Texture2DLoader.loadTexture("res/buildings/energy2/color.png"),
				Texture2DLoader.loadTexture("res/buildings/energy2/light.png"),
				Texture2DLoader.loadTexture("res/buildings/energy2/faction.png"),
				WavefrontLoader.loadObject("res/buildings/energy2/mesh.obj")
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
