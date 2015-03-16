package game6.core.buildings;

public abstract class CoreBuildingResearch extends DefaultCoreBuilding {

	public CoreBuildingResearch(long id) {
		super(id, 2, 2, 50, 0, 100);
	}

	public String getName() {
		return "Labor";
	}

}
