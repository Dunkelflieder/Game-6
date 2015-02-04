package game6.client.world;

import game6.client.world.buildings.BaseBuilding;
import game6.core.CoreMap;
import game6.core.Tile;

import java.util.HashSet;

import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.physics.BoundingAABB;
import de.nerogar.render.RenderProperties;
import de.nerogar.util.Vector3f;

public class Map extends BaseEntity {

	private CoreMap core;
	private MapMesh terrainMesh;
	private RenderProperties renderProperties;

	public Map(CoreMap core) {
		super(new BoundingAABB(new Vector3f(0, -10, 0), new Vector3f(core.getSizeX(), 0, core.getSizeY())), new Vector3f());
		this.core = core;
		this.terrainMesh = new MapMesh(core);
		renderProperties = new RenderProperties();
	}

	public boolean canAddBuilding(int posX, int posY, BaseBuilding building) {
		return core.canAddBuilding(posX, posY, building);
	}

	public void addBuilding(int posX, int posY, BaseBuilding building) {
		core.addBuilding(posX, posY, building);
		terrainMesh.reload();
	}

	public int getSizeX() {
		return core.getSizeX();
	}

	public int getSizeY() {
		return core.getSizeY();
	}

	public Tile getTile(int x, int y) {
		return core.getTile(x, y);
	}

	public void setTile(int x, int y, Tile tile) {
		core.setTile(x, y, tile);
	}

	@Override
	public void update(float timeDelta) {
	}

	@Override
	public void render() {
		Vector3f renderPosition = new Vector3f();
		RenderProperties buildingRenderProperties = new RenderProperties(renderPosition, new Vector3f(), null);

		HashSet<BaseBuilding> renderedBuildings = new HashSet<>();
		for (int x = 0; x < core.getBuildings().length; x++) {
			for (int y = 0; y < core.getBuildings()[x].length; y++) {
				BaseBuilding building = (BaseBuilding) core.getBuildings()[x][y];
				if (building != null) {
					if (!renderedBuildings.contains(building)) {
						renderPosition.setX(x);
						renderPosition.setZ(y);
						building.render(buildingRenderProperties);
						renderedBuildings.add(building);
					}
				}
			}
		}

		terrainMesh.render(renderProperties);
	}

}
