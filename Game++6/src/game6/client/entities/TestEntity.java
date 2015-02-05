package game6.client.entities;

import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.physics.BoundingAABB;
import de.nerogar.render.*;
import de.nerogar.render.Texture2D.InterpolationType;
import de.nerogar.util.Vector3f;

public class TestEntity extends BaseEntity {

	private WavefrontMesh mesh;
	RenderProperties renderProperties;
	Texture2D tex = TextureLoader.loadTexture("res/buildings/houseBlue/color.png", InterpolationType.NEAREST);

	// Texture2D tex2 = TextureLoader.loadTexture("res/grass.png", InterpolationType.NEAREST);

	public TestEntity() {
		super(new BoundingAABB(new Vector3f(-1, 0, -1), new Vector3f(1)), new Vector3f());
		mesh = WavefrontLoader.loadObject("res/buildings/houseBlue/mesh.obj");
		renderProperties = new RenderProperties(getPosition(), new Vector3f(), new Vector3f(1));
	}

	@Override
	public void update(float timeDelta) {
		renderProperties.rotation.add(1, 10 * timeDelta);
	}

	@Override
	public void render() {
		tex.bind();
		mesh.render(renderProperties);
		// tex2.bind();
	}

}
