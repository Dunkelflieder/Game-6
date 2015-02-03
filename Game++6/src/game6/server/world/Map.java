package game6.server.world;

import game6.core.CoreBuilding;
import game6.core.CoreMap;

public class Map {

	private CoreMap core;

	public Map(CoreMap core) {
		this.core = core;
	}

	public boolean canAddBuilding(int posX, int posY, CoreBuilding building) {
		return core.canAddBuilding(posX, posY, building);
	}

	public void addBuilding(int posX, int posY, CoreBuilding building) {
		core.addBuilding(posX, posY, building);
		// send to clients or something
	}

	public CoreMap getCore() {
		return core;
	}

}
