package game6.client.buildings;

import game6.core.buildings.CoreBuildingReactor;
import de.nerogar.render.RenderProperties;
import de.nerogar.render.Renderable;
import de.nerogar.render.Texture2D;
import de.nerogar.render.TextureLoader;
import de.nerogar.render.WavefrontLoader;
import de.nerogar.util.Vector3f;

public class BuildingReactor extends CoreBuildingReactor {

	private Renderable mesh;
	private Texture2D texture;
	
	public BuildingReactor(int id) {
		super(id);
	}
	
	@Override
	public void init() {
		mesh = WavefrontLoader.loadObject("res/buildings/reactor/mesh.obj");
		texture = TextureLoader.loadTexture("res/buildings/reactor/color.png");
	}

	@Override
	public void render() {
		Vector3f pos = new Vector3f(getPosX(), 0, getPosY());
		texture.bind();
		mesh.render(new RenderProperties(pos, null, null));
	}

}
