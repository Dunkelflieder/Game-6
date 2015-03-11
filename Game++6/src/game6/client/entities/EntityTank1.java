package game6.client.entities;

import game6.client.ObjectRenderer;
import game6.core.entities.CoreEntityTank1;
import de.nerogar.render.*;
import de.nerogar.util.Vector3f;

public class EntityTank1 extends CoreEntityTank1 {

	private RenderProperties3f renderProperties;
	private ObjectRenderer renderer;

	public EntityTank1(long id) {
		super(id, new Vector3f());

		renderer = new ObjectRenderer(TextureLoader.loadTexture("res/entities/tank1/color.png"),
				TextureLoader.loadTexture("res/entities/tank1/light.png"),
				TextureLoader.loadTexture("res/entities/tank1/faction.png"),
				WavefrontLoader.loadObject("res/entities/tank1/mesh.obj"));

		renderProperties = new RenderProperties3f();
	}

	@Override
	public void render(Shader shader) {
		renderProperties.setXYZ(getPosition());
		renderProperties.setYaw(getVisibleRotation());
		renderProperties.setScale(2, 2, 2);

		renderer.render(shader, renderProperties.getModelMatrix());
	}
}
