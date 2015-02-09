package game6.core.buildings;

import game6.core.events.Event;

import java.util.List;

public abstract class CoreBuildingTower extends CoreBuilding {

	public CoreBuildingTower(int id) {
		super(id, 1, 1);
	}

	@Override
	public void update(List<Event> events) {
	}

	public String getName() {
		return "Turm";
	}
	
}
