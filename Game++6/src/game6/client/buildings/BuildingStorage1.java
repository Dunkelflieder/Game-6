package game6.client.buildings;

import game6.client.ObjectRenderer;
import game6.client.buildings.guis.BuildingGuiStorage;
import game6.client.world.World;
import game6.core.buildings.CoreBuildingStorage1;
import game6.core.networking.packets.buildings.PacketBuilding;
import game6.core.networking.packets.buildings.PacketBuildingUpdateInventory;
import de.nerogar.render.*;

public class BuildingStorage1 extends CoreBuildingStorage1 implements ClientBuilding {

	private DefaultClientBuildingBehaviour defaultBehaviour = new DefaultClientBuildingBehaviour();

	private RenderProperties3f renderProperties;
	private ObjectRenderer renderer;

	private BuildingGuiStorage gui;

	public BuildingStorage1(long id) {
		super(id);
		gui = new BuildingGuiStorage(this);
	}

	@Override
	public void init() {
		renderer = new ObjectRenderer(
				Texture2DLoader.loadTexture("res/buildings/storage1/color.png"),
				Texture2DLoader.loadTexture("res/buildings/storage1/light.png"),
				Texture2DLoader.loadTexture("res/buildings/storage1/faction.png"),
				WavefrontLoader.loadObject("res/buildings/storage1/mesh.obj")
				);

		renderProperties = new RenderProperties3f();
	}

	@Override
	public void render(Shader shader) {
		renderProperties.setXYZ(getPosX(), 0, getPosY());
		renderer.render(shader, renderProperties.getModelMatrix());
	}

	@Override
	public BuildingGuiStorage getGui() {
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

	@Override
	public void process(PacketBuilding packet) {
		ClientBuilding.super.process(packet);
		if (packet instanceof PacketBuildingUpdateInventory) {
			getResources().setResources(((PacketBuildingUpdateInventory) packet).resources);
			getResources().setCapacity(((PacketBuildingUpdateInventory) packet).resources.getTotalCapacity());
		}
	}

}
