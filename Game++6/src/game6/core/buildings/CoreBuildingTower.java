package game6.core.buildings;

public abstract class CoreBuildingTower extends CoreBuilding {

	public CoreBuildingTower(long id) {
		super(id, 1, 1, 0, 5);
	}

	public String getName() {
		return "Turm";
	}

}
