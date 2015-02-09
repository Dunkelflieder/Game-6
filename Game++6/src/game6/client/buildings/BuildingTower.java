package game6.client.buildings;

import game6.core.buildings.CoreBuildingTower;
import de.nerogar.render.RenderProperties;
import de.nerogar.render.Renderable;
import de.nerogar.render.Texture2D;
import de.nerogar.render.TextureLoader;
import de.nerogar.render.WavefrontLoader;
import de.nerogar.util.Vector3f;

public class BuildingTower extends CoreBuildingTower {

	private Renderable mesh;
	private Texture2D texture;
	private Texture2D textureFaction;
	
	public BuildingTower(int id) {
		super(id);
	}
	
	@Override
	public void init() {
		mesh = WavefrontLoader.loadObject("res/buildings/tower/mesh.obj");
		texture = TextureLoader.loadTexture("res/buildings/tower/color.png");
		textureFaction = TextureLoader.loadTexture("res/buildings/tower/color.png");
	}

	@Override
	public void render(float height) {
		Vector3f pos = new Vector3f(getPosX(), height, getPosY());
		texture.bind(0);
		textureFaction.bind(1);
		mesh.render(new RenderProperties(pos, null, null));
	}

}
