package game6.core.buildings;

public abstract class CoreBuildingFarm1 extends DefaultCoreBuilding {

	public CoreBuildingFarm1(long id) {
		super(id, 4, 4, 50, 0, 100);
	}

	public String getName() {
		return "Farm";
	}

}
