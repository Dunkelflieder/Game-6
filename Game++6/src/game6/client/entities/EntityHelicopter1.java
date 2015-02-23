package game6.client.entities;

import game6.core.entities.CoreEntityHelicopter1;

import java.util.List;

import de.nerogar.engine.UpdateEvent;
import de.nerogar.render.*;
import de.nerogar.util.Vector3f;

public class EntityHelicopter1 extends CoreEntityHelicopter1 {

	private RenderProperties3f renderPropertiesMain;
	private RenderProperties3f renderPropertiesRotorL;
	private RenderProperties3f renderPropertiesRotorH;

	private WavefrontMesh meshMain;
	private WavefrontMesh meshRotorL;
	private WavefrontMesh meshRotorH;

	private Texture2D texMain;
	private Texture2D texRotor;

	private Texture2D texLightMain;
	private Texture2D texLightRotor;

	private Texture2D texFactionMain;
	private Texture2D texFactionRotor;

	public EntityHelicopter1(long id) {
		super(id, new Vector3f());

		meshMain = WavefrontLoader.loadObject("res/entities/helicopter1/mesh.obj");
		meshRotorL = WavefrontLoader.loadObject("res/entities/helicopter1/meshRotorL.obj");
		meshRotorH = WavefrontLoader.loadObject("res/entities/helicopter1/meshRotorH.obj");

		texMain = TextureLoader.loadTexture("res/entities/helicopter1/colorMain.png");
		texRotor = TextureLoader.loadTexture("res/entities/helicopter1/colorRotor.png");

		texLightMain = TextureLoader.loadTexture("res/entities/helicopter1/lightMain.png");
		texLightRotor = TextureLoader.loadTexture("res/entities/helicopter1/lightRotor.png");

		texFactionMain = TextureLoader.loadTexture("res/entities/helicopter1/factionMain.png");
		texFactionRotor = TextureLoader.loadTexture("res/entities/helicopter1/factionRotor.png");

		renderPropertiesMain = new RenderProperties3f();
		renderPropertiesRotorL = new RenderProperties3f();
		renderPropertiesRotorH = new RenderProperties3f();
	}

	@Override
	public void update(float timeDelta, List<UpdateEvent> events) {
		super.update(timeDelta, events);

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

		shader.setUniformMat4f("modelMatrix", renderPropertiesMain.getModelMatrix().asBuffer());
		texMain.bind(0);
		texLightMain.bind(1);
		texFactionMain.bind(2);
		meshMain.render(null);

		texRotor.bind(0);
		texLightRotor.bind(1);
		texFactionRotor.bind(2);

		shader.setUniformMat4f("modelMatrix", renderPropertiesRotorL.getModelMatrix().asBuffer());
		meshRotorH.render(null);

		shader.setUniformMat4f("modelMatrix", renderPropertiesRotorH.getModelMatrix().asBuffer());
		meshRotorL.render(null);
	}

}
