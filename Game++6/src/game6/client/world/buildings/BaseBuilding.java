package game6.client.world.buildings;

import game6.core.CoreBuilding;
import de.nerogar.render.RenderProperties;
import de.nerogar.render.Renderable;
import de.nerogar.render.Texture2D;

public abstract class BaseBuilding extends CoreBuilding {

	private Renderable mesh;
	private Texture2D texture;

	public BaseBuilding(Renderable mesh, Texture2D texture, int sizeX, int sizeY) {
		super(sizeX, sizeY);
		this.mesh = mesh;
		this.texture = texture;
	}

	public void render(RenderProperties renderProperties) {
		texture.bind();
		mesh.render(renderProperties);
	}

}
