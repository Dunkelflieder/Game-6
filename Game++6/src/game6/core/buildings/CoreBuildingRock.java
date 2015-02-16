package game6.core.buildings;

public abstract class CoreBuildingRock extends CoreBuilding {

	public CoreBuildingRock(long id) {
		super(id, 1, 1, 0);
	}

	public String getName() {
		return "Stein";
	}
	
}
