package game6.core.buildings;

import game6.core.events.Event;

import java.util.List;

public abstract class CoreBuildingRock extends CoreBuilding {

	public CoreBuildingRock(long id) {
		super(id, 1, 1, 0);
	}

	@Override
	public void update(List<Event> events) {
		
	}

	public String getName() {
		return "Stein";
	}
	
}
