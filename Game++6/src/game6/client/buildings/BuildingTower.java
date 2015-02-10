package game6.client.buildings;

import game6.core.buildings.CoreBuildingTower;
import de.nerogar.render.*;
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
		textureFaction = TextureLoader.loadTexture("res/buildings/tower/faction.png");
	}

	@Override
	public void render() {
		texture.bind(0);
		textureFaction.bind(1);
		mesh.render(new RenderProperties(new Vector3f(getPosX(), 0, getPosY()), null, null));
	}

}
