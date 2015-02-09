package game6.server.world;

import game6.core.buildings.CoreBuilding;
import game6.core.events.Event;
import game6.core.world.CoreMap;

import java.util.List;

public class Map extends CoreMap {

	public Map(CoreMap core) {
		super(core.getTiles(), core.getHeights());
	}

	public void update(List<Event> events) {
		for (CoreBuilding building: getBuildings()) {
			building.update(events);
		}
	}

}
