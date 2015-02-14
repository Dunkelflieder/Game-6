package game6.client.buildings;

import game6.core.buildings.CoreBuildingResearch;
import de.nerogar.render.*;
import de.nerogar.util.Vector3f;

public class BuildingResearch extends CoreBuildingResearch {

	private Renderable mesh;
	private Texture2D textureLight;
	private Texture2D textureColor;
	private Texture2D textureFaction;

	public BuildingResearch(long id) {
		super(id);
	}

	@Override
	public void init() {
		mesh = WavefrontLoader.loadObject("res/buildings/research/mesh.obj");
		textureLight = TextureLoader.loadTexture("res/buildings/research/light.png");
		textureColor = TextureLoader.loadTexture("res/buildings/research/color.png");
		textureFaction = TextureLoader.loadTexture("res/buildings/research/faction.png");
	}

	@Override
	public void render() {
		textureLight.bind(0);
		textureColor.bind(1);
		textureFaction.bind(2);
		mesh.render(new RenderProperties(new Vector3f(getPosX(), 0, getPosY()), null, null));
	}

}
