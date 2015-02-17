package game6.server.buildings;

import java.util.List;

import de.nerogar.engine.UpdateEvent;
import de.nerogar.render.Shader;
import game6.core.buildings.CoreBuildingFactory;
import game6.core.events.EventBuildingUpdate;

public class BuildingFactory extends CoreBuildingFactory {

	public BuildingFactory() {
		super(getNextID());
	}

	@Override
	public void init() {
	}

	@Override
	public void render(Shader shader) {
	}

	@Override
	public void update(List<UpdateEvent> events) {
		if (subtractEnergy(3) != 3) {
			events.add(new EventBuildingUpdate(this));
		}
	}

}
