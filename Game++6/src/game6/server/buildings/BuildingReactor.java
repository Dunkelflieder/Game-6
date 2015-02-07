package game6.server.buildings;

import game6.core.buildings.CoreBuildingReactor;

public class BuildingReactor extends BaseBuilding {

	public BuildingReactor(int id) {
		super(new CoreBuildingReactor(id));
	}

}
