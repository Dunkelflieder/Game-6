package game6.core.world;

import game6.core.buildings.CoreBuilding;

import java.util.ArrayList;
import java.util.List;

import de.nerogar.util.*;

public class CoreMap {

	private Tile[][] tiles;
	private CoreBuilding[][] buildingMap;
	private List<CoreBuilding> buildings;

	public CoreMap(Tile[][] tiles) {
		this.tiles = tiles;
		this.buildingMap = new CoreBuilding[getSizeX()][getSizeY()];
		this.buildings = new ArrayList<>();
	}

	public boolean canAddBuilding(int posX, int posY, CoreBuilding building) {
		for (int x = posX; x < posX + building.getSizeX(); x++) {
			for (int y = posY; y < posY + building.getSizeY(); y++) {
				if (buildingMap[x][y] != null) return false;
			}
		}
		return true;
	}

	public void addBuilding(int posX, int posY, CoreBuilding building) {
		building.setPosX(posX);
		building.setPosY(posY);
		building.setMap(this);
		this.buildings.add(building);
		this.buildings.sort((a, b) -> (int) (a.getID() - b.getID()));
		for (int x = posX; x < posX + building.getSizeX(); x++) {
			for (int y = posY; y < posY + building.getSizeY(); y++) {
				buildingMap[x][y] = building;
			}
		}
	}

	public CoreBuilding getBuildingAt(int x, int y) {
		if (x < 0 || y < 0 || x >= getSizeX() || y >= getSizeY()) { return null; }
		return buildingMap[x][y];
	}

	public List<CoreBuilding> getBuildingsWithin(int posX, int posY, int radius) {
		List<CoreBuilding> closeBuildings = new ArrayList<>();
		for (int x = posX - radius; x < posX + radius; x++) {
			for (int y = posY - radius; y < posY + radius; y++) {
				if (x < 0 || y < 0 || x >= getSizeX() || y >= getSizeY()) {
					continue;
				}
				CoreBuilding b = getBuildingAt(x, y);
				if (b != null && !closeBuildings.contains(b)) {
					closeBuildings.add(b);
				}
			}
		}
		return closeBuildings;
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

	public List<CoreBuilding> getBuildings() {
		return buildings;
	}

	public CoreBuilding getBuilding(long id) {

		int l = 0;
		int r = buildings.size() - 1;
		int p;
		while (l <= r) {
			p = (l + r) / 2;

			if (buildings.get(p).getID() == id) return buildings.get(p);
			if (buildings.get(p).getID() < id) {
				l = p + 1;
			} else {
				r = p - 1;
			}
		}

		return null;

	}
	
	public Vector2f getIntersection(Ray<Vector3f> ray) {
		Vector3f start = ray.getStart();
		Vector3f dir = ray.getDirection();
		if (dir.getY() >= 0) {
			return null;
		}
		dir.multiply(- start.getY() / dir.getY());
		Vector3f intersect = start.added(dir);
		return new Vector2f(intersect.getX(), intersect.getZ());
	}

}
