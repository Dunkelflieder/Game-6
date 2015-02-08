package game6.server.world;

import game6.core.buildings.CoreBuilding;
import game6.core.events.Event;
import game6.core.world.CoreMap;
import game6.core.world.Tile;

import java.util.List;

public class Map extends CoreMap {

	public Map(Tile[][] tiles) {
		super(tiles);
	}

	public void update(List<Event> events) {
		for (CoreBuilding building: getBuildings()) {
			building.update(events);
		}
	}

}
