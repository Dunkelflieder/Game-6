package game6.core.buildings;

import game6.core.events.Event;

import java.util.List;

public abstract class CoreBuildingFactory extends CoreBuilding {

	public CoreBuildingFactory(long id) {
		super(id, 3, 2, 300);
	}

	@Override
	public void update(List<Event> events) {
		
	}

	public String getName() {
		return "Fabrik";
	}
	
}
