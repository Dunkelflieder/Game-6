package client.world;

import client.world.buildings.BuildingReactor;

public class TerrainGenerator {

	public static Terrain getTerrain(int sizeX, int sizeY) {

		Tile[][] tiles = new Tile[sizeX][sizeY];

		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				tiles[x][y] = Tile.CHROME;
			}
		}

		Terrain terrain = new Terrain(tiles);
		for (int x = 0; x < tiles.length-1; x++) {
			for (int y = 0; y < tiles[x].length-1; y++) {
				if (Math.random() < 0.01) {
					terrain.addBuilding(x, y, new BuildingReactor());
				}
			}
		}
		
		return terrain;

	}

}
