package game6.core.buildings;

import java.util.List;

import de.nerogar.engine.UpdateEvent;

public abstract class CoreBuildingTower extends CoreBuilding {

	public CoreBuildingTower(long id) {
		super(id, 1, 1, 10);
	}

	@Override
	public void update(List<UpdateEvent> events) {
	}

	public String getName() {
		return "Turm";
	}
	
}
