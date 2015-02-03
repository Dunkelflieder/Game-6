package game6.client.world;
import game6.client.world.buildings.BaseBuilding;
import game6.core.MapGenerator;
import de.nerogar.engine.BaseWorld;
import de.nerogar.util.Vector3f;

public class World extends BaseWorld {

	private Map map;
	
	public World() {
		super(new Vector3f());
		isStatic = true;
		// TODO retrieve map from server
		map = new Map(MapGenerator.getMap(50, 50));
		spawnEntity(map, new Vector3f());
	}
	
	public boolean canAddBuilding(int posX, int posY, BaseBuilding building){
		return map.canAddBuilding(posX, posY, building);
	}
	
	public void addBuilding(int posX, int posY, BaseBuilding building) {
		map.addBuilding(posX, posY, building);
	}

	@Override
	public void load() {
		
	}

	@Override
	public void save() {
		
	}

}
