package game6.server.buildings;

import game6.core.buildings.CoreBuildingTower;

public class BuildingTower extends BaseBuilding {

	public BuildingTower(int id) {
		super(new CoreBuildingTower(id));
	}

}
