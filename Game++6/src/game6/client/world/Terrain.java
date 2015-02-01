package game6.client.world;

import game6.client.world.buildings.BaseBuilding;

import java.util.HashSet;

import de.nerogar.engine.entity.BaseEntity;
import de.nerogar.physics.BoundingAABB;
import de.nerogar.render.RenderProperties;
import de.nerogar.util.Vector3f;

public class Terrain extends BaseEntity {

	private Tile[][] tiles;
	private BaseBuilding[][] buildings;
	private TerrainMesh terrainMesh;
	private RenderProperties renderProperties;

	public Terrain(Tile[][] tiles) {
		super(new BoundingAABB(new Vector3f(0, -10, 0), new Vector3f(tiles.length, 0, tiles.length == 0 ? 0 : tiles[0].length)), new Vector3f());
		this.tiles = tiles;
		this.buildings = new BaseBuilding[getSizeX()][getSizeY()];
		this.terrainMesh = new TerrainMesh(tiles, buildings);
		renderProperties = new RenderProperties();
	}

	public void addBuilding(int posX, int posY, BaseBuilding building) {
		for (int x = posX; x < posX + building.getSizeX(); x++) {
			for (int y = posY; y < posY + building.getSizeY(); y++) {
				buildings[x][y] = building;
			}
		}
		terrainMesh.reload();
	}

	public int getSizeX() {
		return tiles.length;
	}

	public int getSizeY() {
		return tiles.length == 0 ? 0 : tiles[0].length;
	}

	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}

	public void setTile(int x, int y, Tile tile) {
		tiles[x][y] = tile;
	}

	@Override
	public void update(float timeDelta) {
	}

	@Override
	public void render() {
		Vector3f renderPosition = new Vector3f();
		RenderProperties buildingRenderProperties = new RenderProperties(renderPosition, new Vector3f(), null);

		HashSet<BaseBuilding> renderedBuildings = new HashSet<>();
		for (int x = 0; x < buildings.length; x++) {
			for (int y = 0; y < buildings[x].length; y++) {
				BaseBuilding building = buildings[x][y];
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
