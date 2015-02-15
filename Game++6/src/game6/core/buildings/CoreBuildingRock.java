package game6.core.buildings;

import java.util.List;

import de.nerogar.engine.UpdateEvent;

public abstract class CoreBuildingRock extends CoreBuilding {

	public CoreBuildingRock(long id) {
		super(id, 1, 1, 0);
	}

	@Override
	public void update(List<UpdateEvent> events) {
		
	}

	public String getName() {
		return "Stein";
	}
	
}
