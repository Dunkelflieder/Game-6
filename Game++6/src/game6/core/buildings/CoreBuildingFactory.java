package game6.core.buildings;

public abstract class CoreBuildingFactory extends DefaultCoreBuilding {

	public CoreBuildingFactory(long id) {
		super(id, 3, 2, 300, 0);
	}

	public String getName() {
		return "Fabrik";
	}

}
