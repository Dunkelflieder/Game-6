package game6.core.world;

import game6.core.buildings.CoreBuilding;

public class CoreMap {

	private Tile[][] tiles;
	private CoreBuilding[][] buildings;

	public CoreMap(Tile[][] tiles) {
		this.tiles = tiles;
		this.buildings = new CoreBuilding[getSizeX()][getSizeY()];
	}

	public boolean canAddBuilding(int posX, int posY, CoreBuilding building) {
		for (int x = posX; x < posX + building.getSizeX(); x++) {
			for (int y = posY; y < posY + building.getSizeY(); y++) {
				if (buildings[x][y] != null)
					return false;
			}
		}
		return true;
	}

	public void addBuilding(int posX, int posY, CoreBuilding building) {
		for (int x = posX; x < posX + building.getSizeX(); x++) {
			for (int y = posY; y < posY + building.getSizeY(); y++) {
				buildings[x][y] = building;
			}
		}
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

	public CoreBuilding[][] getBuildings() {
		return buildings;
	}

}
