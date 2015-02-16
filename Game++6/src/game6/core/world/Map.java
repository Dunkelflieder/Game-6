package game6.core.world;

import game6.core.buildings.CoreBuilding;

import java.util.*;

import de.nerogar.util.*;

public class Map {

	private Tile[][] tiles;
	private CoreBuilding[][] buildingMap;

	public Map(Tile[][] tiles) {
		this.tiles = tiles;
		this.buildingMap = new CoreBuilding[getSizeX()][getSizeY()];
	}

	public boolean canAddBuilding(int posX, int posY, CoreBuilding building) {
		for (int x = posX; x < posX + building.getSizeX(); x++) {
			for (int y = posY; y < posY + building.getSizeY(); y++) {
				if (buildingMap[x][y] != null) {
					return false;
				}
			}
		}
		return true;
	}

	public void addBuilding(CoreBuilding building) {
		for (int x = building.getPosX(); x < building.getPosX() + building.getSizeX(); x++) {
			for (int y = building.getPosY(); y < building.getPosY() + building.getSizeY(); y++) {
				buildingMap[x][y] = building;
			}
		}
	}

	public CoreBuilding getBuildingAt(int x, int y) {
		if (x < 0 || y < 0 || x >= getSizeX() || y >= getSizeY()) {
			return null;
		}
		return buildingMap[x][y];
	}

	public List<CoreBuilding> getBuildingsWithin(int posX, int posY, int radius) {
		// TODO give back a list sorted by distance
		Set<CoreBuilding> closeBuildings = new HashSet<>();
		for (int x = posX - radius; x < posX + radius; x++) {
			for (int y = posY - radius; y < posY + radius; y++) {
				CoreBuilding b = getBuildingAt(x, y);
				if (b != null && !closeBuildings.contains(b)) {
					closeBuildings.add(b);
				}
			}
		}
		
		List<CoreBuilding> buildingList = (new ArrayList<CoreBuilding>());
		buildingList.addAll(closeBuildings);
		return buildingList;
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

	public Tile[][] getTiles() {
		return tiles;
	}

	public CoreBuilding[][] getBuildingMap() {
		return buildingMap;
	}

	public Vector2f getIntersection(Ray<Vector3f> ray) {
		Vector3f start = ray.getStart();
		Vector3f dir = ray.getDirection();
		if (dir.getY() >= 0) {
			return null;
		}
		dir.multiply(-start.getY() / dir.getY());
		Vector3f intersect = start.added(dir);
		return new Vector2f(intersect.getX(), intersect.getZ());
	}

}
