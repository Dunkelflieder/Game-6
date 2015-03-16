package game6.core.buildings;

public abstract class CoreBuildingReactor extends DefaultCoreBuilding {

	public CoreBuildingReactor(long id) {
		// Reactor is 2x2 and cannot hold energy itself.
		super(id, 2, 2, 0, 10, 100);
	}

	public String getName() {
		return "Reaktor";
	}

}
