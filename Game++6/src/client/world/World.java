package client.world;
import de.nerogar.engine.BaseWorld;
import de.nerogar.util.Vector3f;

public class World extends BaseWorld {

	private Terrain terrain;
	
	public World() {
		super(new Vector3f());
		isStatic = true;		
		terrain = TerrainGenerator.getTerrain(50, 50);
		terrain.initTerrain();
		spawnEntity(new TerrainEntity(terrain), new Vector3f());
	}

	@Override
	public void load() {
		
	}

	@Override
	public void save() {
		
	}

}
