package game6.core.buildings;

public abstract class CoreBuildingLiving1 extends DefaultCoreBuilding {

	public CoreBuildingLiving1(long id) {
		super(id, 1, 1, 50, 0);
	}

	public String getName() {
		return "Solar Station";
	}

}
