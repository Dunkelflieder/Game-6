package game6.client.entities;

import game6.client.ObjectRenderer;
import game6.client.entities.guis.EntityGuiDefault;
import game6.client.world.World;
import game6.core.entities.CoreEntityTank1;
import de.nerogar.render.*;
import de.nerogar.util.Vector3f;

public class EntityTank1 extends CoreEntityTank1 implements ClientEntity {

	private DefaultClientEntityBehaviour defaultBehaviour = new DefaultClientEntityBehaviour();

	private RenderProperties3f renderProperties;
	private ObjectRenderer renderer;

	private EntityGuiDefault gui;

	public EntityTank1(long id) {
		super(id, new Vector3f());

		renderer = new ObjectRenderer(Texture2DLoader.loadTexture("res/entities/tank1/color.png"),
				Texture2DLoader.loadTexture("res/entities/tank1/light.png"),
				Texture2DLoader.loadTexture("res/entities/tank1/faction.png"),
				WavefrontLoader.loadObject("res/entities/tank1/mesh.obj"));

		renderProperties = new RenderProperties3f();

		gui = new EntityGuiDefault(this);
	}

	@Override
	public void render(Shader shader) {
		renderProperties.setXYZ(getPosition());
		renderProperties.setYaw(getVisibleRotation());
		renderProperties.setScale(2, 2, 2);

		renderer.render(shader, renderProperties.getModelMatrix());
	}

	@Override
	public EntityGuiDefault getGui() {
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
