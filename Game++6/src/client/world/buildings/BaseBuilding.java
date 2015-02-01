package client.world.buildings;

import de.nerogar.render.RenderProperties;
import de.nerogar.render.Renderable;
import de.nerogar.render.Texture2D;

public abstract class BaseBuilding {

	private Renderable mesh;
	private Texture2D texture;
	private int sizeX, sizeY;

	public BaseBuilding(Renderable mesh, Texture2D texture, int sizeX, int sizeY) {
		this.mesh = mesh;
		this.texture = texture;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	public void render(RenderProperties renderProperties) {
		texture.bind();
		mesh.render(renderProperties);
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

}
