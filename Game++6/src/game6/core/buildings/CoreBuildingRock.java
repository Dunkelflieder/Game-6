package game6.core.buildings;

public abstract class CoreBuildingRock extends DefaultCoreBuilding {

	public CoreBuildingRock(long id) {
		super(id, 1, 1, 0, 0, 100);
	}

	public String getName() {
		return "Stein";
	}

}
