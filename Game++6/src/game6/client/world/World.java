package game6.client.world;
import game6.client.world.buildings.BaseBuilding;
import de.nerogar.engine.BaseWorld;
import de.nerogar.util.Vector3f;

public class World extends BaseWorld {

	private Terrain terrain;
	
	public World() {
		super(new Vector3f());
		isStatic = true;
		terrain = TerrainGenerator.getTerrain(50, 50);
		spawnEntity(terrain, new Vector3f());
	}
	
	public boolean canAddBuilding(int posX, int posY, BaseBuilding building){
		return terrain.canAddBuilding(posX, posY, building);
	}
	
	public void addBuilding(int posX, int posY, BaseBuilding building) {
		terrain.addBuilding(posX, posY, building);
	}

	@Override
	public void load() {
		
	}

	@Override
	public void save() {
		
	}

}
