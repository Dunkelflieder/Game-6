package game6.core.world;

import game6.server.buildings.BuildingRock;
import game6.server.world.World;

public class WorldGenerator {

	public static World getWorld(long seed, int sizeX, int sizeY) {

		Tile[][] tiles = new Tile[sizeX][sizeY];
		Map map = new Map(tiles);
		World world = new World(map);

		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {

				double r1 = SimplexNoise.noise(x / 100f, y / 100f, 100);
				double r2 = SimplexNoise.noise(x / 100f, y / 100f, 200);

				tiles[x][y] = Tile.MARS;

				if (r1 > 0.8) {
					world.addBuilding(x, y, new BuildingRock());
				}
				if (r2 > 0.8) {
					tiles[x][y] = Tile.ICE;
				}

			}
		}

		return world;

	}

}
