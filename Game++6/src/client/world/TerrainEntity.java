package client.world;

import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.physics.BoundingAABB;
import de.nerogar.render.RenderProperties;
import de.nerogar.util.Vector3f;

public class TerrainEntity extends BaseEntity {

	private Terrain terrain;
	private RenderProperties renderProperties;
	
	public TerrainEntity(Terrain terrain) {
		super(new BoundingAABB(new Vector3f(0, -10, 0), new Vector3f(terrain.getSizeX(), 0, terrain.getSizeY())), new Vector3f());
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
