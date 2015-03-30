package game6.core.buildings;

public abstract class CoreBuildingResearch2 extends DefaultCoreBuilding {

	public CoreBuildingResearch2(long id) {
		super(id, 4, 4, 50, 0, 100);
	}

	public String getName() {
		return "Research2";
	}

}
