package game6.core.buildings;

public abstract class CoreBuildingWell extends DefaultCoreBuilding {

	public CoreBuildingWell(long id) {
		super(id, 1, 1, 0, 5, 100);
	}

	public String getName() {
		return "Brunnen";
	}

}
