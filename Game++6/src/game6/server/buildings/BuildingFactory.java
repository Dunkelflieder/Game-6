package game6.server.buildings;

import game6.core.buildings.CoreBuildingFactory;

public class BuildingFactory extends CoreBuildingFactory {

	public BuildingFactory() {
		super(getNextID());
	}

	@Override
	public void init() {
	}

	@Override
	public void render() {
	}

}
