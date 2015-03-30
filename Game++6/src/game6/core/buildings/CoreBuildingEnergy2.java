package game6.core.buildings;

public abstract class CoreBuildingEnergy2 extends DefaultCoreBuilding {

	public CoreBuildingEnergy2(long id) {
		super(id, 3, 3, 0, 10, 100);
	}

	public String getName() {
		return "Reactor";
	}

}
