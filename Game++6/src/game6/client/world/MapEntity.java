package game6.client.world;

import game6.core.buildings.CoreBuilding;
import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.physics.BoundingAABB;
import de.nerogar.render.RenderProperties;
import de.nerogar.util.Vector3f;

public class MapEntity extends BaseEntity {

	private Map map;
	private MapMesh mesh;
	private RenderProperties renderProperties;
	
	public MapEntity(Map map) {
		super(new BoundingAABB(new Vector3f(-99999), new Vector3f(99999, 0, 99999)), new Vector3f(0));
		this.map = map;
		mesh = new MapMesh(map);
		renderProperties = new RenderProperties();
	}
	
	public void reloadMesh() {
		mesh.reload();
	}
	
	@Override
	public void update(float timeDelta) {
	}

	@Override
	public void render() {
		mesh.render(renderProperties);
		for (CoreBuilding building : map.getBuildings()) {
			building.render();
		}
	}

}
