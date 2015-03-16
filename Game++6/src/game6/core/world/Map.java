package game6.core.world;

import game6.core.ai.pathfinding.Pathfinder;
import game6.core.ai.pathfinding.Pathfinder.Position;
import game6.core.buildings.CoreConstructionsite;
import game6.core.buildings.CoreBuilding;

import java.util.*;

import de.nerogar.util.*;

public class Map<T extends CoreBuilding> {

	private Tile[][] tiles;
	private CoreBuilding[][] buildingMap;
	private Pathfinder pathfinder;

	public Map(Tile[][] tiles) {
		this.tiles = tiles;
		this.buildingMap = new CoreBuilding[getSizeX()][getSizeY()];
		this.pathfinder = new Pathfinder(this);
	}

	public boolean canAddBuilding(int posX, int posY, T building) {
		for (int x = posX; x < posX + building.getSizeX(); x++) {
			for (int y = posY; y < posY + building.getSizeY(); y++) {
				if (buildingMap[x][y] != null) {
					return false;
				}
			}
		}
		return true;
	}

	public void addBuilding(T building) {
		for (int x = building.getPosX(); x < building.getPosX() + building.getSizeX(); x++) {
			for (int y = building.getPosY(); y < building.getPosY() + building.getSizeY(); y++) {
				buildingMap[x][y] = building;
			}
		}
		pathfinder.update(this, building.getPosX(), building.getPosY(), building.getPosX() + building.getSizeX(), building.getPosY() + building.getSizeY());
	}

	public void finishConstructionsite(CoreConstructionsite<T> constructionsite) {
		for (int x = constructionsite.getPosX(); x < constructionsite.getPosX() + constructionsite.getSizeX(); x++) {
			for (int y = constructionsite.getPosY(); y < constructionsite.getPosY() + constructionsite.getSizeY(); y++) {
				buildingMap[x][y] = constructionsite.getBuilding();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public T getBuildingAt(int x, int y) {
		if (x < 0 || y < 0 || x >= getSizeX() || y >= getSizeY()) {
			return null;
		}
		return (T) buildingMap[x][y];
	}

	public List<T> getBuildingsWithin(int posX, int posY, int radius) {
		// TODO give back a list sorted by distance
		Set<T> closeBuildings = new HashSet<>();
		for (int x = posX - radius; x <= posX + radius; x++) {
			for (int y = posY - radius; y <= posY + radius; y++) {
				T b = getBuildingAt(x, y);
				if (b != null && !closeBuildings.contains(b)) {
					closeBuildings.add(b);
				}
			}
		}

		List<T> buildingList = (new ArrayList<T>());
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

	@SuppressWarnings("unchecked")
	public T[][] getBuildingMap() {
		return (T[][]) buildingMap;
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

	public List<Position> getPath(int fromX, int fromY, int toX, int toY) {
		return pathfinder.getPath(fromX, fromY, toX, toY);
	}

	public List<Position> getPath(float fromX, float fromY, float toX, float toY) {
		return getPath((int) Math.floor(fromX), (int) Math.floor(fromY), (int) Math.floor(toX), (int) Math.floor(toY));
	}

	public Pathfinder getPathfinder() {
		return pathfinder;
	}

}
