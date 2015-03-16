package game6.client.buildings;

import game6.client.ObjectRenderer;
import game6.client.buildings.guis.BuildingGuiConstructionsite;
import game6.client.world.World;
import game6.core.buildings.CoreConstructionsite;
import game6.core.networking.packets.PacketUniqueID;
import game6.core.networking.packets.buildings.*;
import game6.core.util.ResourceContainer;
import de.nerogar.render.*;

/**
 * Don't add this to the building type list. It's a special building representing a construction site.
 * It also does not have the necessary long-only constructor.
 * @author Felk
 */
public class Constructionsite extends CoreConstructionsite<ClientBuilding> implements ClientBuilding {

	private DefaultClientBuildingBehaviour defaultBehaviour = new DefaultClientBuildingBehaviour();
	
	private RenderProperties3f renderProperties;
	private ObjectRenderer renderer;

	private BuildingGuiConstructionsite gui;

	public Constructionsite(ClientBuilding building, ResourceContainer constructionCost) {
		super(building, constructionCost);
		renderProperties.setScale(building.getSizeX() * 0.5f, 0.1f, building.getSizeY() * 0.5f);
		gui = new BuildingGuiConstructionsite(this);
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
		getBuilding().render(shader);
	}

	@Override
	public BuildingGuiConstructionsite getGui() {
		return gui;
	}
	
	@Override
	public World getWorld() {
		return defaultBehaviour.getWorld();
	}

	@Override
	public void setWorld(World world) {
		defaultBehaviour.setWorld(world);
		getBuilding().setWorld(world);
	}
	
	@Override
	public void process(PacketUniqueID packet) {
		ClientBuilding.super.process(packet);
		if (packet instanceof PacketBuildingUpdateInventory) {
			getCostRemaining().setResources(((PacketBuildingUpdateInventory) packet).resources);
			getCostRemaining().setCapacity(((PacketBuildingUpdateInventory) packet).resources.getTotalCapacity());
		} else if (packet instanceof PacketBuildingFinishConstruction) {
			getWorld().finishConstructionsite(this);
		}
	}
	
}
