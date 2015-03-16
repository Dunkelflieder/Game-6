package game6.client.entities;

import game6.client.ObjectRenderer;
import game6.client.entities.guis.EntityGuiInventory;
import game6.client.world.World;
import game6.core.entities.CoreEntityHelicopter1;
import de.nerogar.render.*;
import de.nerogar.util.Vector3f;

public class EntityHelicopter1 extends CoreEntityHelicopter1 implements ClientEntity, ClientEntityInventory {

	private DefaultClientEntityBehaviour defaultBehaviour = new DefaultClientEntityBehaviour();

	private RenderProperties3f renderPropertiesMain;
	private RenderProperties3f renderPropertiesRotorL;
	private RenderProperties3f renderPropertiesRotorH;

	private ObjectRenderer rendererMain;
	private ObjectRenderer rendererRotor;

	private EntityGuiInventory gui;

	public EntityHelicopter1(long id) {
		super(id, new Vector3f());

		rendererMain = new ObjectRenderer(
				Texture2DLoader.loadTexture("res/entities/helicopter1/colorMain.png"),
				Texture2DLoader.loadTexture("res/entities/helicopter1/lightMain.png"),
				Texture2DLoader.loadTexture("res/entities/helicopter1/factionMain.png"),
				WavefrontLoader.loadObject("res/entities/helicopter1/mesh.obj")
				);

		rendererRotor = new ObjectRenderer(
				Texture2DLoader.loadTexture("res/entities/helicopter1/colorRotor.png"),
				Texture2DLoader.loadTexture("res/entities/helicopter1/lightRotor.png"),
				Texture2DLoader.loadTexture("res/entities/helicopter1/factionRotor.png"),
				WavefrontLoader.loadObject("res/entities/helicopter1/meshRotorL.obj"),
				WavefrontLoader.loadObject("res/entities/helicopter1/meshRotorH.obj")
				);

		renderPropertiesMain = new RenderProperties3f();
		renderPropertiesRotorL = new RenderProperties3f();
		renderPropertiesRotorH = new RenderProperties3f();

		gui = new EntityGuiInventory(this);
	}

	@Override
	public void update(float timeDelta) {
		super.update(timeDelta);

		renderPropertiesRotorL.setYaw((renderPropertiesRotorL.getYaw() + 8f * timeDelta) % 360);
		renderPropertiesRotorH.setYaw((renderPropertiesRotorH.getYaw() - 8f * timeDelta) % 360);
	}

	@Override
	public void render(Shader shader) {
		renderPropertiesMain.setXYZ(getPosition());
		renderPropertiesRotorL.setXYZ(getPosition());
		renderPropertiesRotorH.setXYZ(getPosition());
		renderPropertiesMain.setYaw(getRotation());

		renderPropertiesMain.setScale(2, 2, 2);
		renderPropertiesRotorL.setScale(2, 2, 2);
		renderPropertiesRotorH.setScale(2, 2, 2);

		rendererMain.render(shader, renderPropertiesMain.getModelMatrix());
		rendererRotor.render(shader, renderPropertiesRotorL.getModelMatrix(), renderPropertiesRotorH.getModelMatrix());
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
