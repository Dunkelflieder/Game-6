package game6.client.world;

import game6.core.buildings.CoreBuilding;
import game6.core.world.CoreMap;
import game6.core.world.Tile;

public class Map extends CoreMap {

	private MapEntity entity;

	public Map(Tile[][] tiles) {
		super(tiles);
		this.entity = new MapEntity(this);
	}
	
	@Override
	public void addBuilding(int posX, int posY, CoreBuilding building) {
		super.addBuilding(posX, posY, building);
		entity.reloadMesh();
	}

	public MapEntity getEntity() {
		return entity;
	}
	
}
