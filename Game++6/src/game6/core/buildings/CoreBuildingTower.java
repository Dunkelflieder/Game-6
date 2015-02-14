package game6.core.buildings;

import game6.core.events.Event;

import java.util.List;

public abstract class CoreBuildingTower extends CoreBuilding {

	public CoreBuildingTower(long id) {
		super(id, 1, 1, 10);
	}

	@Override
	public void update(List<Event> events) {
	}

	public String getName() {
		return "Turm";
	}
	
}
