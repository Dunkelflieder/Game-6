package game6.client.entities;

import game6.core.entities.CoreEntityTank1;
import de.nerogar.render.*;
import de.nerogar.util.Vector3f;

public class EntityTank1 extends CoreEntityTank1 {

	private RenderProperties renderProperties;
	private WavefrontMesh mesh;
	
	private Texture2D tex;
	private Texture2D texLight;
	private Texture2D texFaction;
	
	public EntityTank1(long id) {
		super(id, new Vector3f());

		mesh = WavefrontLoader.loadObject("res/entities/tank1/mesh.obj");

		tex = TextureLoader.loadTexture("res/entities/tank1/color.png");
		texLight = TextureLoader.loadTexture("res/entities/tank1/light.png");
		texFaction = TextureLoader.loadTexture("res/entities/tank1/faction.png");
		
		renderProperties = new RenderProperties(getPosition(), new Vector3f(), null);
	}

	@Override
	public void render() {
		tex.bind(0);
		texLight.bind(1);
		texFaction.bind(2);
		mesh.render(renderProperties);
	}

}
