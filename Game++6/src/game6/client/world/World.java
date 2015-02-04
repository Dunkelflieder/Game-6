package game6.client.world;
import game6.client.world.buildings.BaseBuilding;
import de.nerogar.engine.BaseWorld;
import de.nerogar.util.Vector3f;

public class World extends BaseWorld {

	private Map map;
	
	public World() {
		super(new Vector3f());
		isStatic = true;
	}
	
	public boolean canAddBuilding(int posX, int posY, BaseBuilding building){
		return map.canAddBuilding(posX, posY, building);
	}
	
	public void addBuilding(int posX, int posY, BaseBuilding building) {
		map.addBuilding(posX, posY, building);
	}

	public void setMap(Map map) {
		this.map = map;
		spawnEntity(map, new Vector3f());
	}
	
	@Override
	public void load() {
		
	}

	@Override
	public void save() {
		
	}
	
	public boolean isReady() {
		return map != null;
	}

}
