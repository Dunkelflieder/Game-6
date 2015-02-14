package game6.client.buildings;

import game6.core.buildings.CoreBuildingReactor;
import de.nerogar.render.*;
import de.nerogar.util.Vector3f;

public class BuildingReactor extends CoreBuildingReactor {

	private Renderable mesh;
	private Texture2D textureLight;
	private Texture2D textureColor;
	private Texture2D textureFaction;

	public BuildingReactor(int id) {
		super(id);
	}

	@Override
	public void init() {
		mesh = WavefrontLoader.loadObject("res/buildings/reactor/mesh.obj");
		textureLight = TextureLoader.loadTexture("res/buildings/reactor/light.png");
		textureColor = TextureLoader.loadTexture("res/buildings/reactor/color.png");
		textureFaction = TextureLoader.loadTexture("res/buildings/reactor/faction.png");
	}

	@Override
	public void render() {
		textureLight.bind(0);
		textureColor.bind(1);
		textureFaction.bind(2);
		mesh.render(new RenderProperties(new Vector3f(getPosX(), 0, getPosY()), null, null));
	}

}
