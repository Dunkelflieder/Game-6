package game6.core.buildings;

public abstract class CoreBuildingFactory extends CoreBuilding {

	public CoreBuildingFactory(long id) {
		super(id, 3, 2, 300);
	}

	public String getName() {
		return "Fabrik";
	}
	
}
