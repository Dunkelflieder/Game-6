package game6.server.world;

import game6.core.events.Event;
import game6.core.world.CoreMap;
import game6.server.buildings.BaseBuilding;

import java.util.ArrayList;
import java.util.List;

public class Map {

	private CoreMap core;
	private List<BaseBuilding> buildings;

	public Map(CoreMap core) {
		this.core = core;
		this.buildings = new ArrayList<>();
	}

	public boolean canAddBuilding(int posX, int posY, BaseBuilding building) {
		return core.canAddBuilding(posX, posY, building.getCore());
	}

	public void addBuilding(int posX, int posY, BaseBuilding building) {
		building.setPosX(posX);
		building.setPosY(posY);
		buildings.add(building.getID(), building);
		core.addBuilding(posX, posY, building.getCore());
		
		// send to clients ?
		
	}

	public CoreMap getCore() {
		return core;
	}
	
	public void update(List<Event> events) {
		
		for (BaseBuilding building: buildings) {
			building.update(events);
		}
		
	}

	public List<BaseBuilding> getBuildings() {
		return buildings;
	}

}
