package game6.core.buildings;

import game6.core.events.Event;

import java.util.List;

public abstract class CoreBuildingResearch extends CoreBuilding {

	public CoreBuildingResearch(int id) {
		super(id, 2, 2, 50);
	}

	@Override
	public void update(List<Event> events) {
	}

	public String getName() {
		return "Labor";
	}
	
}
