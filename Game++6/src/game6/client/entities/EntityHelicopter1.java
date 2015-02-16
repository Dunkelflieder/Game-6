package game6.client.entities;

import game6.core.entities.CoreEntityHelicopter1;

import java.util.List;

import de.nerogar.engine.UpdateEvent;
import de.nerogar.render.*;
import de.nerogar.util.Vector3f;

public class EntityHelicopter1 extends CoreEntityHelicopter1 {

	private RenderProperties renderPropertiesMain;
	private RenderProperties renderPropertiesRotorL;
	private RenderProperties renderPropertiesRotorH;

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
		
		renderPropertiesMain = new RenderProperties(getPosition(), new Vector3f(), null);
		renderPropertiesRotorL = new RenderProperties(getPosition(), new Vector3f(), null);
		renderPropertiesRotorH = new RenderProperties(getPosition(), new Vector3f(), null);
	}

	@Override
	public void update(float timeDelta, List<UpdateEvent> events) {

		renderPropertiesRotorL.rotation.set(1, (renderPropertiesRotorL.rotation.get(1) + 360*timeDelta) % 360);
		renderPropertiesRotorH.rotation.set(1, (renderPropertiesRotorH.rotation.get(1) - 360*timeDelta) % 360);
		
	}

	@Override
	public void render() {
		texMain.bind(0);
		texLightMain.bind(1);
		texFactionMain.bind(2);
		meshMain.render(renderPropertiesMain);
		
		texRotor.bind(0);
		texLightRotor.bind(1);
		texFactionRotor.bind(2);
		meshRotorH.render(renderPropertiesRotorL);
		meshRotorL.render(renderPropertiesRotorH);
	}

}
