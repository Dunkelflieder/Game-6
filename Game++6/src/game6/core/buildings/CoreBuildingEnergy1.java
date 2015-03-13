package game6.core.buildings;

public abstract class CoreBuildingEnergy1 extends DefaultCoreBuilding {

	public CoreBuildingEnergy1(long id) {
		super(id, 2, 2, 50, 0);
	}

	public String getName() {
		return "Solar Station";
	}

}
