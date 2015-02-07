package game6.client.buildings;

import game6.core.buildings.CoreBuilding;
import de.nerogar.render.RenderProperties;
import de.nerogar.render.Renderable;
import de.nerogar.render.Texture2D;
import de.nerogar.util.Vector3f;

public abstract class BaseBuilding {

	private CoreBuilding core;
	private Renderable mesh;
	private Texture2D texture;

	public BaseBuilding(CoreBuilding core, Renderable mesh, Texture2D texture) {
		this.core = core;
		this.mesh = mesh;
		this.texture = texture;
	}

	public void render() {
		// TODO do this kind of stuff on a static working-instance
		Vector3f pos = new Vector3f(getPosX(), 0, getPosY());
		RenderProperties properties = new RenderProperties(pos, null, null);
		texture.bind();
		mesh.render(properties);
	}
	
	public int getID() {
		return core.getID();
	}

	public CoreBuilding getCore() {
		return core;
	}

	public int getPosX() {
		return core.getPosX();
	}

	public void setPosX(int posX) {
		core.setPosX(posX);
	}

	public int getPosY() {
		return core.getPosY();
	}

	public void setPosY(int posY) {
		core.setPosY(posY);
	}
	
	public int getSizeX() {
		return core.getSizeX();
	}
	
	public int getSizeY() {
		return core.getSizeY();
	}

}
