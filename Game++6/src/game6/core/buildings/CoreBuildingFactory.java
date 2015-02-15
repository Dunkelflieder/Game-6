package game6.core.buildings;

import java.util.List;

import de.nerogar.engine.UpdateEvent;

public abstract class CoreBuildingFactory extends CoreBuilding {

	public CoreBuildingFactory(long id) {
		super(id, 3, 2, 300);
	}

	@Override
	public void update(List<UpdateEvent> events) {
		
	}

	public String getName() {
		return "Fabrik";
	}
	
}
