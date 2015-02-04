package game6.server.buildings;

import game6.core.buildings.CoreBuilding;

public abstract class BaseBuilding {

	private CoreBuilding core;

	public BaseBuilding(CoreBuilding core) {
		this.core = core;
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

	public abstract void update();

}
