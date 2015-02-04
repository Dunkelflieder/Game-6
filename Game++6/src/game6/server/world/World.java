package game6.server.world;

import game6.client.world.buildings.BaseBuilding;
import game6.core.MapGenerator;

public class World {

	private Map map;

	public World() {
		this.map = new Map(MapGenerator.getMap(40, 40));
	}

	public boolean canAddBuilding(int posX, int posY, BaseBuilding building) {
		return map.canAddBuilding(posX, posY, building);
	}

	public void addBuilding(int posX, int posY, BaseBuilding building) {
		map.addBuilding(posX, posY, building);
	}

	public void update() {
	}

	public Map getMap() {
		return map;
	}

}
