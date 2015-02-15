package game6.core.buildings;

import java.util.List;

import de.nerogar.engine.UpdateEvent;

public abstract class CoreBuildingResearch extends CoreBuilding {

	public CoreBuildingResearch(long id) {
		super(id, 2, 2, 50);
	}

	@Override
	public void update(List<UpdateEvent> events) {
	}

	public String getName() {
		return "Labor";
	}
	
}
