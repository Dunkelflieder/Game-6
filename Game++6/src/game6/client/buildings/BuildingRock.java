package game6.client.buildings;

import game6.core.buildings.CoreBuildingRock;

import java.util.List;

import de.nerogar.engine.UpdateEvent;
import de.nerogar.render.*;

public class BuildingRock extends CoreBuildingRock {

	private Renderable mesh;
	private Texture2D textureLight;
	private Texture2D textureColor;
	private Texture2D textureFaction;

	private RenderProperties3f renderProperties;

	public BuildingRock(long id) {
		super(id);
	}

	@Override
	public void init() {
		mesh = WavefrontLoader.loadObject("res/buildings/rock/mesh.obj");
		textureLight = TextureLoader.loadTexture("res/buildings/rock/light.png");
		textureColor = TextureLoader.loadTexture("res/buildings/rock/color.png");
		textureFaction = TextureLoader.loadTexture("res/buildings/rock/faction.png");
		
		renderProperties = new RenderProperties3f();
	}

	@Override
	public void render(Shader shader) {
		renderProperties.setXYZ(getPosX(), 0, getPosY());
		if (shader != null)shader.setUniformMat4f("modelMatrix", renderProperties.getModelMatrix().asBuffer());
		
		textureColor.bind(0);
		textureLight.bind(1);
		textureFaction.bind(2);
		mesh.render(null);
	}

	@Override
	public void update(List<UpdateEvent> events) {
	}

}
