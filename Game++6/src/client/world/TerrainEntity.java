package client.world;

import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.impact.BoundingAABB;
import de.nerogar.spark.RenderProperties;
import de.nerogar.util.Vector3f;

public class TerrainEntity extends BaseEntity {

	private Terrain terrain;
	private RenderProperties renderProperties;
	
	public TerrainEntity(Terrain terrain) {
		super(new BoundingAABB(new Vector3f(-terrain.getSizeX() / 2, 0, -terrain.getSizeY() / 2), new Vector3f(terrain.getSizeX() / 2, 0, terrain.getSizeY() / 2)), new Vector3f());
		this.terrain = terrain;
		renderProperties = new RenderProperties();
	}

	@Override
	public void update(float timeDelta) {
	}

	@Override
	public void render() {
		terrain.render(renderProperties);
	}

}
