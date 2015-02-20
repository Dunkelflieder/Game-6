package game6.client.buildings;

import game6.core.buildings.CoreBuildingTower;

import java.util.List;

import de.nerogar.engine.UpdateEvent;
import de.nerogar.render.*;

public class BuildingTower extends CoreBuildingTower {

	private Renderable mesh;
	private Texture2D textureLight;
	private Texture2D textureColor;
	private Texture2D textureFaction;

	private RenderProperties3f renderProperties;

	public BuildingTower(long id) {
		super(id);
	}

	@Override
	public void init() {
		mesh = WavefrontLoader.loadObject("res/buildings/tower/mesh.obj");
		textureLight = TextureLoader.loadTexture("res/buildings/tower/light.png");
		textureColor = TextureLoader.loadTexture("res/buildings/tower/color.png");
		textureFaction = TextureLoader.loadTexture("res/buildings/tower/faction.png");
		
		renderProperties = new RenderProperties3f();
	}

	@Override
	public void render(Shader shader) {
		renderProperties.setXYZ(getPosX(), 0, getPosY());
		if (shader != null) shader.setUniformMat4f("modelMatrix", renderProperties.getModelMatrix().asBuffer());

		textureColor.bind(0);
		textureLight.bind(1);
		textureFaction.bind(2);
		mesh.render(null);
	}

	@Override
	public void update(List<UpdateEvent> events) {
	}

}
