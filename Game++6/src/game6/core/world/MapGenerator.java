package game6.core.world;

import game6.server.buildings.BuildingRock;

public class MapGenerator {

	public static CoreMap getMap(long seed, int sizeX, int sizeY) {

		Tile[][] tiles = new Tile[sizeX][sizeY];
		CoreMap map = new CoreMap(tiles);

		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {

				double r1 = SimplexNoise.noise(x / 100f, y / 100f, 100);
				double r2 = SimplexNoise.noise(x / 100f, y / 100f, 200);

				Tile tile = Tile.MARS;
				if (r1 > 0.8) {
					map.addBuilding(x, y, new BuildingRock());
				}
				if (r2 > 0.8) {
					tile = Tile.ICE;
				}

				tiles[x][y] = tile;

			}
		}

		return map;

	}

}
