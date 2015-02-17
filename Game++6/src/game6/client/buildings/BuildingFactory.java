package game6.client.buildings;

import game6.core.buildings.CoreBuildingFactory;

import java.util.List;

import de.nerogar.engine.UpdateEvent;
import de.nerogar.render.*;

public class BuildingFactory extends CoreBuildingFactory {

	private Renderable mesh;
	private Texture2D textureLight;
	private Texture2D textureColor;
	private Texture2D textureFaction;

	private RenderProperties3f renderProperties;

	public BuildingFactory(long id) {
		super(id);
	}

	@Override
	public void init() {
		mesh = WavefrontLoader.loadObject("res/buildings/factory/mesh.obj");
		textureLight = TextureLoader.loadTexture("res/buildings/factory/light.png");
		textureColor = TextureLoader.loadTexture("res/buildings/factory/color.png");
		textureFaction = TextureLoader.loadTexture("res/buildings/factory/faction.png");

		renderProperties = new RenderProperties3f();
	}

	@Override
	public void render(Shader shader) {
		renderProperties.setXYZ(getPosX(), 0, getPosY());
		if (shader != null) shader.setUniformMat4f("modelMatrix", renderProperties.getModelMatrix().asBuffer());

		textureLight.bind(0);
		textureColor.bind(1);
		textureFaction.bind(2);
		mesh.render(null);
	}

	@Override
	public void update(List<UpdateEvent> events) {
	}

}
