package game6.core.buildings;

public abstract class CoreBuildingResearch extends CoreBuilding {

	public CoreBuildingResearch(long id) {
		super(id, 2, 2, 50, 0);
	}

	public String getName() {
		return "Labor";
	}

}
