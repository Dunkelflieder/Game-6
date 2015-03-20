package game6.client.entities;

import game6.client.ObjectRenderer;
import game6.client.entities.guis.EntityGuiInventory;
import game6.client.world.World;
import game6.core.entities.CoreEntityTransport2;
import de.nerogar.render.*;
import de.nerogar.util.Vector3f;

public class EntityTransport2 extends CoreEntityTransport2 implements ClientEntity, ClientEntityInventory {

	private DefaultClientEntityBehaviour defaultBehaviour = new DefaultClientEntityBehaviour();

	private RenderProperties3f renderProperties;

	private ObjectRenderer renderer;

	private EntityGuiInventory gui;

	public EntityTransport2(long id) {
		super(id, new Vector3f());

		renderer = new ObjectRenderer(
				Texture2DLoader.loadTexture("res/entities/transport2/color.png"),
				Texture2DLoader.loadTexture("res/entities/transport2/light.png"),
				Texture2DLoader.loadTexture("res/entities/transport2/faction.png"),
				WavefrontLoader.loadObject("res/entities/transport2/mesh.obj")
				);

		renderProperties = new RenderProperties3f();

		gui = new EntityGuiInventory(this);
	}

	@Override
	public void render(Shader shader) {
		renderProperties.setXYZ(getPosition());
		renderProperties.setYaw(getVisibleRotation());

		renderProperties.setScale(1, 1, 1);

		renderer.render(shader, renderProperties.getModelMatrix());
	}

	@Override
	public EntityGuiInventory getGui() {
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
	public float getVisibleRotation() {
		return defaultBehaviour.getVisibleRotation();
	}

	@Override
	public void setVisibleRotation(float rotation) {
		defaultBehaviour.setVisibleRotation(rotation);
	}

}
