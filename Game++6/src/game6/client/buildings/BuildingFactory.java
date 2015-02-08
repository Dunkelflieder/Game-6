package game6.client.buildings;

import game6.core.buildings.CoreBuildingFactory;
import de.nerogar.render.RenderProperties;
import de.nerogar.render.Renderable;
import de.nerogar.render.Texture2D;
import de.nerogar.render.TextureLoader;
import de.nerogar.render.WavefrontLoader;
import de.nerogar.util.Vector3f;

public class BuildingFactory extends CoreBuildingFactory {

	private Renderable mesh;
	private Texture2D texture;
	private Texture2D textureFaction;
	
	public BuildingFactory(int id) {
		super(id);
	}
	
	@Override
	public void init() {
		mesh = WavefrontLoader.loadObject("res/buildings/factory/mesh.obj");
		texture = TextureLoader.loadTexture("res/buildings/factory/color.png");
		textureFaction = TextureLoader.loadTexture("res/buildings/factory/faction.png");
	}

	@Override
	public void render() {
		Vector3f pos = new Vector3f(getPosX(), 0, getPosY());
		texture.bind(0);
		textureFaction.bind(1);
		mesh.render(new RenderProperties(pos, null, null));
	}

}
