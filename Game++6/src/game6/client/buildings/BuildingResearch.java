package game6.client.buildings;

import game6.core.buildings.CoreBuildingResearch;
import de.nerogar.render.RenderProperties;
import de.nerogar.render.Renderable;
import de.nerogar.render.Texture2D;
import de.nerogar.render.TextureLoader;
import de.nerogar.render.WavefrontLoader;
import de.nerogar.util.Vector3f;

public class BuildingResearch extends CoreBuildingResearch {

	private Renderable mesh;
	private Texture2D texture;
	private Texture2D textureFaction;

	public BuildingResearch(int id) {
		super(id);
	}

	@Override
	public void init() {
		mesh = WavefrontLoader.loadObject("res/buildings/research/mesh.obj");
		texture = TextureLoader.loadTexture("res/buildings/research/color.png");
		textureFaction = TextureLoader.loadTexture("res/buildings/research/faction.png");
	}

	@Override
	public void render() {
		texture.bind(0);
		textureFaction.bind(1);
		mesh.render(new RenderProperties(new Vector3f(getPosX(), 0, getPosY()), null, null));
	}

}
