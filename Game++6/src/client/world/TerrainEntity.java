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
		Vector3f renderPosition = new Vector3f();
		RenderProperties buildingRenderProperties = new RenderProperties(renderPosition, new Vector3f(), null);
		for (int x = 0; x < terrain.getSizeX(); x++) {
			for (int y = 0; y < terrain.getSizeY(); y++) {
				if (terrain.getTile(x, y) != Tile.CHROME) {
					renderPosition.setX(x);
					renderPosition.setZ(y);
					System.out.println(buildingRenderProperties.position.get(0) + " : " + buildingRenderProperties.position.get(2));

					terrain.getTile(x, y).getTexture().bind();
					terrain.getTile(x, y).getRenderable().render(buildingRenderProperties);
				}
			}
		}

		terrain.render(renderProperties);
	}
}
